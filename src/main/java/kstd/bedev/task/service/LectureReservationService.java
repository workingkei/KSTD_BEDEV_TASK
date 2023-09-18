package kstd.bedev.task.service;

import kstd.bedev.task.domain.Lecture;
import kstd.bedev.task.domain.LectureRepository;
import kstd.bedev.task.domain.LectureReservation;
import kstd.bedev.task.domain.LectureReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LectureReservationService {

    private final LectureReservationRepository lectureReservationRepository;
    private final LectureRepository lectureRepository;

    public List<LectureReservation> getLectureReservationsByLectureId(Long lectureId) {
        return lectureReservationRepository.findAllByLectureId(lectureId);
    }

    // 낙관적 Locking 및 트랜잭션 격리 수준 설정 - 동시성 제어, 일관성 보장
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void reserveLecture(Long lectureId, String employeeId) throws Exception {
        while (true) {
            try {
                int maxAttendees = lectureRepository.findById(lectureId).get().getMaxAttendees();
                int reservationSize  = lectureReservationRepository.findAllByLectureId(lectureId).size();

                if (reservationSize >= maxAttendees) {
                    throw new Exception("강의 신청인원을 초과했습니다.");
                }

                // 저장
                lectureReservationRepository.save(LectureReservation.builder()
                        .lectureId(lectureId)
                        .employeeId(employeeId)
                        .build());
                break;
            } catch (OptimisticLockingFailureException ex) {
            }
        }

    }

    public void cancelReservation(Long id) {
        lectureReservationRepository.deleteById(id);
    }

    public List<String> getLectureReservationsEmployeeIds(Long lectureId) {
        return lectureReservationRepository.findAllByLectureId(lectureId).stream()
                .map(LectureReservation::getEmployeeId)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Lecture> getMyReservationLectuers(String employeeId) {
        List<Long> collect = lectureReservationRepository.findAllByEmployeeId(employeeId).stream()
                .map(LectureReservation::getLectureId).collect(Collectors.toList());
        List<Lecture> allById = lectureRepository.findAllById(collect);
        return allById;
    }
}

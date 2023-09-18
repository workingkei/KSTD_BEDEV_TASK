package kstd.bedev.task.service;

import kstd.bedev.task.api.dto.LectureRes;
import kstd.bedev.task.domain.Lecture;
import kstd.bedev.task.domain.LectureRepository;
import kstd.bedev.task.domain.LectureReservation;
import kstd.bedev.task.domain.LectureReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureReservationRepository lectureReservationRepository;
    public List<LectureRes> getAllLectures() {
        return lectureRepository.findAll().stream().map(LectureRes::new).toList();
    }

    public Lecture addLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public List<LectureRes> getAvailableLectures() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(1);
        LocalDateTime endDate = now.plusWeeks(1);

        List<Lecture> lecturesWithinDateRange = lectureRepository.findLecturesWithinDateRange(startDate, endDate);

        return lecturesWithinDateRange.stream().map(LectureRes::new).toList();
    }

    public List<LectureRes> getPopularLectures() {
        LocalDateTime endDate = LocalDateTime.now();
        List<LectureReservation> reservations = lectureReservationRepository.findAllByCreatedDateBetween(endDate.minusDays(3), endDate);

        Map<Long, Long> lectureIdToReservationCount = reservations.stream()
                .collect(Collectors.groupingBy(LectureReservation::getLectureId, Collectors.counting()));

        List<Lecture> lectures = lectureRepository.findAllById(lectureIdToReservationCount.keySet());
        lectures.sort((o1, o2) -> lectureIdToReservationCount.get(o2.getId()).compareTo(lectureIdToReservationCount.get(o1.getId())));
        return lectures.stream()
                .map(lecture -> new LectureRes(lecture, lectureIdToReservationCount.get(lecture.getId())))
                .toList();

    }
}

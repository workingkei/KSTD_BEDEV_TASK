package kstd.bedev.task.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public interface LectureReservationRepository extends JpaRepository<LectureReservation, Long> {
    List<LectureReservation> findAllByLectureId(Long lectureId);

    List<LectureReservation> findAllByEmployeeId(String employeeId);

    List<LectureReservation> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}

package kstd.bedev.task.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    // 현재 날짜를 기준으로 1주일 전부터 1일 후까지의 강연 정보를 조회하는 쿼리
    @Query("SELECT l FROM Lecture l WHERE l.startTime >= :startDate AND l.startTime <= :endDate")
    List<Lecture> findLecturesWithinDateRange(LocalDateTime startDate, LocalDateTime endDate);
}



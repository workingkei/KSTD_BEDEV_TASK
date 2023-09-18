package kstd.bedev.task.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String speaker;         // 강연자
    private String venue;           // 강연장
    private int maxAttendees;       // 최대 참가 가능 인원
    private LocalDateTime startTime;// 강연 시작 시간
    private String contents;         // 강연 내용

    @Transient
    private List<LectureReservation> reservations; // 강연 예약 목록

    @Builder
    public Lecture(String speaker, String venue, int maxAttendees, LocalDateTime startTime, String contents) {
        this.speaker = speaker;
        this.venue = venue;
        this.maxAttendees = maxAttendees;
        this.startTime = startTime;
        this.contents = contents;
    }
}

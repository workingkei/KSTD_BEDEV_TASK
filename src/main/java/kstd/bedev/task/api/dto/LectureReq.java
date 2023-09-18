package kstd.bedev.task.api.dto;

import kstd.bedev.task.domain.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
public class LectureReq {

    private String speaker;         // 강연자
    private String venue;           // 강연장
    private int maxAttendees;       // 최대 참가 가능 인원
    private LocalDateTime startTime;// 강연 시작 시간
    private String contents;         // 강연 내용

    @Builder
    public LectureReq(String speaker, String venue, int maxAttendees, LocalDateTime startTime, String contents) {
        this.speaker = speaker;
        this.venue = venue;
        this.maxAttendees = maxAttendees;
        this.startTime = startTime;
        this.contents = contents;
    }

    public Lecture toEntity() {
        return Lecture.builder()
                .speaker(speaker)
                .venue(venue)
                .maxAttendees(maxAttendees)
                .startTime(startTime)
                .contents(contents)
                .build();
    }
}

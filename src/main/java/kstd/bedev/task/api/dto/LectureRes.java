package kstd.bedev.task.api.dto;

import kstd.bedev.task.domain.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@ToString
@Getter
@NoArgsConstructor
public class LectureRes {
    private Long id;
    private String speaker;         // 강연자
    private String venue;           // 강연장
    private int maxAttendees;       // 최대 참가 가능 인원
    private LocalDateTime startTime;// 강연 시작 시간
    private String contents;         // 강연 내용

    @Setter
    private long attendees;          // 현재 참가 인원

    public String getStartTime() {
        return startTime.toString().substring(0, 19);
    }

    public LectureRes(Lecture entity) {
        this.id = entity.getId();
        this.speaker = entity.getSpeaker();
        this.venue = entity.getVenue();
        this.maxAttendees = entity.getMaxAttendees();
        this.startTime = entity.getStartTime();
        this.contents = entity.getContents();
    }

    public LectureRes(Lecture entity, Long attendees) {
        this.id = entity.getId();
        this.speaker = entity.getSpeaker();
        this.venue = entity.getVenue();
        this.maxAttendees = entity.getMaxAttendees();
        this.startTime = entity.getStartTime();
        this.contents = entity.getContents();
        this.attendees = attendees;
    }

}

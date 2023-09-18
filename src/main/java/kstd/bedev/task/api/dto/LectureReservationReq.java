package kstd.bedev.task.api.dto;

import kstd.bedev.task.domain.LectureReservation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class LectureReservationReq {

    private String employeeId; // 사번 (직원 ID)
    private Long lectureId; // 강연 ID

    @Builder
    public LectureReservationReq(String employeeId, Long lectureId) {
        this.employeeId = employeeId;
        this.lectureId = lectureId;
    }

    public LectureReservation toEntity() {
        return LectureReservation.builder()
                .employeeId(employeeId)
                .lectureId(lectureId)
                .build();
    }

}

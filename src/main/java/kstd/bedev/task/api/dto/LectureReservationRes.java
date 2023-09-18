package kstd.bedev.task.api.dto;

import kstd.bedev.task.domain.LectureReservation;
import lombok.Getter;

@Getter
public class LectureReservationRes {

    private Long id;
    private String employeeId; // 사번 (직원 ID)
    private Long lectureId; // 강연 ID

    public LectureReservationRes(LectureReservation entity) {
        this.id = entity.getId();
        this.employeeId = entity.getEmployeeId();
        this.lectureId = entity.getLectureId();
    }
}

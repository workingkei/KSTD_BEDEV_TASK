package kstd.bedev.task.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class LectureReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId; // 사번 (직원 ID)
    private Long lectureId; // 강연 ID

    @CreatedDate
    private LocalDateTime createdDate;

    @Version
    private Integer version; // 버전 열

    @Builder
    public LectureReservation(String employeeId, Long lectureId) {
        this.employeeId = employeeId;
        this.lectureId = lectureId;
    }
}

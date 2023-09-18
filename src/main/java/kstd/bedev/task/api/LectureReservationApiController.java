package kstd.bedev.task.api;

import kstd.bedev.task.api.dto.LectureRes;
import kstd.bedev.task.domain.Lecture;
import kstd.bedev.task.domain.LectureReservation;
import kstd.bedev.task.service.LectureReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lecture-reservations")
public class LectureReservationApiController {

    private final LectureReservationService reservationService;

    public LectureReservationApiController(LectureReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // BackOffice: 강연별 신청한 사번 목록
    @GetMapping("/{lectureId}/employeeIds")
    public ResponseEntity<List<String>> getLectureReservationsEmployeeIds(@PathVariable Long lectureId) {
        return ResponseEntity.ok(reservationService.getLectureReservationsEmployeeIds(lectureId));
    }

    // Front: 강연 신청
    @PostMapping("/reserve/{lectureId}/{employeeId}")
    public ResponseEntity<String> reserveLecture(@PathVariable Long lectureId, @PathVariable String employeeId) {
        Optional<LectureReservation> has = reservationService.getLectureReservationsByLectureId(lectureId).stream()
                .filter(reservation -> reservation.getEmployeeId().equals(employeeId))
                .findFirst();

        if (has.isPresent()) {
            return ResponseEntity.badRequest().body("Already reserved.");
        }

        try {
            reservationService.reserveLecture(lectureId, employeeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Successfully reserved.");
    }

    // Front: 신청내역 조회
    @GetMapping("/my-reservation-lectuers/{employeeId}")
    public ResponseEntity<List<LectureRes>> getMyReservationLectuers(@PathVariable String employeeId) {
        return ResponseEntity.ok(reservationService.getMyReservationLectuers(employeeId).stream()
                        .map(LectureRes::new)
                        .collect(Collectors.toList()));
    }

    // Front: 신청한 강연 취소
    @DeleteMapping("/cancel/{employeeId}/{lectureId}")
    public ResponseEntity<String> cancelReservation(@PathVariable String employeeId, @PathVariable Long lectureId) {
        Optional<LectureReservation> lectureReservation = reservationService.getLectureReservationsByLectureId(lectureId).stream()
                .filter(reservation -> reservation.getEmployeeId().equals(employeeId))
                .findFirst();
        if (!lectureReservation.isPresent()) {
            return ResponseEntity.badRequest().body("wrong reservation info.");
        }
        reservationService.cancelReservation(lectureReservation.get().getId());
        return ResponseEntity.ok("Successfully canceled.");
    }

}

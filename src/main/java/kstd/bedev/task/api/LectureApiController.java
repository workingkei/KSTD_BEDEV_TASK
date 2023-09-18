package kstd.bedev.task.api;

import kstd.bedev.task.api.dto.LectureReq;
import kstd.bedev.task.api.dto.LectureRes;
import kstd.bedev.task.domain.Lecture;
import kstd.bedev.task.domain.LectureReservation;
import kstd.bedev.task.service.LectureReservationService;
import kstd.bedev.task.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lectures")
public class LectureApiController {

    private final LectureService lectureService;

    // BackOffice: 강연 목록 (전체 강연 목록)
    @GetMapping("")
    public ResponseEntity<List<LectureRes>> getAllLectures() {
        return ResponseEntity.ok(lectureService.getAllLectures());
    }

    // BackOffice: 강연 등록
    @PostMapping("")
    public ResponseEntity<LectureRes> addLecture(@RequestBody LectureReq lectureReq) {
        return ResponseEntity.ok(new LectureRes(lectureService.addLecture(lectureReq.toEntity())));
    }

    // Front: 강연 목록 (신청 가능한 시점부터 강연 시작시간 1일 후까지 노출)
    @GetMapping("/available")
    public ResponseEntity<List<LectureRes>> getAvailableLectures() {
        return ResponseEntity.ok(lectureService.getAvailableLectures());
    }

    // Front: 실시간 인기 강연
    @GetMapping("/popular")
    public ResponseEntity<List<LectureRes>> getPopularLectures() {
        return ResponseEntity.ok(lectureService.getPopularLectures());
    }

}

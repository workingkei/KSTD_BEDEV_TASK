package kstd.bedev.task;

import jakarta.annotation.PostConstruct;
import kstd.bedev.task.api.dto.LectureReq;
import kstd.bedev.task.domain.Lecture;
import kstd.bedev.task.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class KstdBedevTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(KstdBedevTaskApplication.class, args);
    }

    @Autowired
    private LectureService lectureService;

    @PostConstruct
    public void initializeTestData() {
        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withMinute(0).withHour(0);
        for (int i = -10; i <= 10; i++) {
            lectureService.addLecture(LectureReq.builder()
                    .speaker("강연자" + i)
                    .venue("강연장" + i)
                    .maxAttendees(1 + Math.abs(i))
                    .startTime(currentTime.plusDays(i))
                    .contents("강연 내용" + i)
                    .build().toEntity());
        }
    }

}

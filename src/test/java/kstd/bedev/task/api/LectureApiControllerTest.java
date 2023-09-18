package kstd.bedev.task.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kstd.bedev.task.api.dto.LectureReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class LectureApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void 강연_등록() throws Exception {
        //given
        String speaker = "강연자";
        String venue = "강연장";
        int maxAttendees = 100;
        LocalDateTime startTime = LocalDateTime.now();
        String contents = "강연 내용";

        LectureReq lectureReq = LectureReq.builder()
                .speaker(speaker)
                .venue(venue)
                .maxAttendees(maxAttendees)
                .startTime(startTime)
                .contents(contents)
                .build();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lectureReq)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.speaker").value(speaker))
                .andExpect(MockMvcResultMatchers.jsonPath("$.venue").value(venue))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxAttendees").value(maxAttendees))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value(startTime.toString().substring(0, 19)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contents").value(contents));
    }

    @Test
    void BO_강연_목록() throws Exception {
        //given
        String speaker = "강연자";
        String venue = "강연장";
        LocalDateTime startTime = LocalDateTime.now();
        String content = "강연 내용";

        int caseSize = 12;

        //when
        for (int i = 0; i < caseSize; i++) {
            LectureReq lectureReq = LectureReq.builder()
                    .speaker(speaker + i)
                    .venue(venue + i)
                    .maxAttendees(i)
                    .startTime(i >= caseSize / 2 ? startTime.plusDays(i) : startTime.minusDays(caseSize - i))
                    .contents(content)
                    .build();

            mockMvc.perform(MockMvcRequestBuilders.post("/api/lectures")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(lectureReq)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lectures")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(caseSize));
    }

    @Test
    void Front_강연_목록() throws Exception {
        // 테스트시 @PostConstruct 주석처리 필요
        //given
        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withMinute(0).withHour(0);

        List<LectureReq> lectureReqList = new ArrayList<>();

        for (int i = -10; i <= 10; i++) {
            LectureReq lectureReq = LectureReq.builder()
                    .speaker("강연자" + i)
                    .venue("강연장" + i)
                    .maxAttendees(1 + Math.abs(i))
                    .startTime(currentTime.plusDays(i))
                    .contents("강연 내용" + i)
                    .build();
            lectureReqList.add(lectureReq);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/lectures")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(lectureReq)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        List<LectureReq> list = new ArrayList<>();
        for (LectureReq lectureReq : lectureReqList) {
            if (lectureReq.getStartTime().isAfter(LocalDateTime.now().minusDays(1))
                    && lectureReq.getStartTime().isBefore(LocalDateTime.now().plusWeeks(1))) {
                list.add(lectureReq);
            }
        }
        int caseSize = list.size();

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/lectures/available")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(caseSize));

    }
}

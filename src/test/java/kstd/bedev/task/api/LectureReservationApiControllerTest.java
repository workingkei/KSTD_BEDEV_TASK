package kstd.bedev.task.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kstd.bedev.task.api.dto.LectureReq;
import kstd.bedev.task.api.dto.LectureRes;
import kstd.bedev.task.domain.LectureRepository;
import kstd.bedev.task.domain.LectureReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
public class LectureReservationApiControllerTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 강연_신청() throws Exception {
        // Given: Prepare the request data
        LectureReq lectureReq = LectureReq.builder()
                .speaker("강연자")
                .venue("강연장")
                .maxAttendees(100)
                .startTime(LocalDateTime.now())
                .contents("강연 내용")
                .build();

        String employeeId = "test1";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // When: Perform the API request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lectureReq)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // lectureResList.reverse()
        List<LectureRes> lectureResList = lectureRepository.findAll()
                .stream()
                .map(LectureRes::new)
                .collect(Collectors.toList());
        lectureResList.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        LectureRes lectureRes = lectureResList.get(0);

        // Then: Verify the result
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lecture-reservations/reserve/{lectureId}/{employeeId}", lectureRes.getId(), employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successfully reserved."));
    }

    @Test
    public void 강연_신청_중복체크() throws Exception {
        // Given: Prepare the request data
        LectureReq lectureReq = LectureReq.builder()
                .speaker("강연자")
                .venue("강연장")
                .maxAttendees(100)
                .startTime(LocalDateTime.now())
                .contents("강연 내용")
                .build();

        String employeeId = "test1";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // When: Perform the API request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lectureReq)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // lectureResList.reverse()
        List<LectureRes> lectureResList = lectureRepository.findAll()
                .stream()
                .map(LectureRes::new)
                .collect(Collectors.toList());
        lectureResList.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        LectureRes lectureRes = lectureResList.get(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lecture-reservations/reserve/{lectureId}/{employeeId}", lectureRes.getId(), employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successfully reserved."));

        // Then: Verify the result
        mockMvc.perform(MockMvcRequestBuilders.post("/api/lecture-reservations/reserve/{lectureId}/{employeeId}", lectureRes.getId(), employeeId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Already reserved."));
    }

}

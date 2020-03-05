package com.example.test_task.integration.global;

import com.example.test_task.model.dto.CommentDto;
import com.example.test_task.repository.CommentRepository;
import com.example.test_task.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class MainTest {

    @Autowired
    private MockMvc mvc;

    private String jsonComment;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = JsonMapper.builder() // or different mapper for other format
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                // and possibly other configuration, modules, then:
                .build();
        jsonComment = "{\n" +
                "  \"comment\": \"string\",\n" +
                "  \"id\": 0,\n" +
                "  \"time\": \"2020-03-05 19:35:05\"\n" +
                "}";
    }

    @Test
    public void test() throws Exception {
        int quantityOfRequests = 1000;
        for (int i = 0; i < quantityOfRequests; i++) {
            MockHttpServletResponse response = mvc.perform(post("/comment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonComment))
                    .andDo(print())
                    .andReturn().getResponse();
            int status = response.getStatus();
            CommentDto commentDto = objectMapper.readValue(response.getContentAsString(), CommentDto.class);
            if (status == 201) {
                assertTrue(commentRepository.findById(commentDto.getId()).isPresent());
                assertTrue(notificationRepository.findByCommentId(commentDto.getId()).isPresent());
            } else {
                assertFalse(commentRepository.findById(commentDto.getId()).isPresent());
                assertFalse(notificationRepository.findByCommentId(commentDto.getId()).isPresent());
            }
        }
        System.out.println("Percentage of successfully created comments: "
                + commentRepository.count() * 100 / quantityOfRequests
                + "%");
        System.out.println("Percentage of successfully delivered notifications: "
                + notificationRepository.countNotificationByDeliveredTrue() * 100 / quantityOfRequests
                + "%");
    }
}

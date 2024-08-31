package com.youngcamp.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngcamp.server.domain.Announcement;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementDeleteRequest;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementPostRequest;
import com.youngcamp.server.repository.AnnouncementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnnouncementControllerTest {

    @Autowired
    private AnnouncementController target;

    @Autowired
    private AnnouncementRepository announcementRepository;


    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .build();

        mapper = new ObjectMapper();
    }

    @AfterEach
    void clean() {
        announcementRepository.deleteAll();
    }

    @Test
    public void mockMvc가Null이아님() {
        assertThat(target).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void 공지사항등록() throws Exception {
        //given
        final String url = "/api/v1/announcements";
        AnnouncementPostRequest request = AnnouncementPostRequest.builder()
                .title("title")
                .content("content")
                .imageUrl("image.jpg")
                .isPinned(true)
                .build();
        String json = mapper.writeValueAsString(request);

        //expected
        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void 공지사항삭제() throws Exception {
        //given
        final String url = "/api/v1/announcements";

        List<Announcement> announcements = IntStream.range(0, 10)
                .mapToObj(i -> Announcement.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .imageUrl("imageUrl" + i)
                        .isPinned(true)
                        .build())
                .collect(Collectors.toList());
        List<Announcement> savedAnnouncements = announcementRepository.saveAll(announcements);

        Long id = announcements.get(0).getId();
        System.out.println(id);

        List<Long> collect = savedAnnouncements.stream().
                map(a -> a.getId())
                .collect(Collectors.toList());
        AnnouncementDeleteRequest request = AnnouncementDeleteRequest.builder()
                .ids(collect)
                .build();
        String json = mapper.writeValueAsString(request);

        //expected
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(url)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void 공지사항조회() throws Exception {
        //given
        final String url = "/api/v1/announcements";

        List<Announcement> announcements = IntStream.range(0, 15)
                .mapToObj(i -> Announcement.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .imageUrl("imageUrl" + i)
                        .isPinned(true)
                        .build())
                .collect(Collectors.toList());
        announcementRepository.saveAll(announcements);

        //expected
        mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("page", "1")
                                .param("size", "10")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(10));
    }


}

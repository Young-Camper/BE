package com.youngcamp.server.repository;

import com.youngcamp.server.domain.Announcement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnnouncementRepositoryTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Test
    public void AnnouncementRepository_IS_NOTNULL_기본테스트(){
        assertThat(announcementRepository).isNotNull();
    }

    @BeforeEach
    void clean() {
        announcementRepository.deleteAll();
    }

    @Test
    public void 공지사항등록() {
        //given
        final Announcement announcement = Announcement.builder()
                .title("title")
                .content("content")
                .isPinned(true)
                .imageUrl("s3-image-url")
                .build();
        //when
        final Announcement result = announcementRepository.save(announcement);

        //then
        assertThat(result.getTitle()).isNotNull();
        assertThat(result.getTitle()).isEqualTo("title");

        assertThat(result.getContent()).isNotNull();
        assertThat(result.getContent()).isEqualTo("content");

        assertThat(result.getIsPinned()).isNotNull();
        assertThat(result.getIsPinned()).isEqualTo(true);

        assertThat(result.getImageUrl()).isNotNull();
        assertThat(result.getImageUrl()).isEqualTo("s3-image-url");
    }

    @Test
    public void 공지사항삭제_1개() {
        //given
        final Announcement announcement = Announcement.builder()
                .title("title")
                .content("content")
                .imageUrl("s3-image-url")
                .isPinned(true)
                .build();

        //when
        Announcement savedAnnouncement = announcementRepository.save(announcement);
        announcementRepository.deleteAllByUserId(savedAnnouncement.getId());
        Optional<Announcement> result = announcementRepository.findById(announcement.getId());

        //then
        assertThat(result).isEmpty();

    }

    @Test
    public void 공지사항삭제_여러개() {
        //given
        List<Announcement> announcements = IntStream.range(0,10)
                .mapToObj(i -> Announcement.builder()
                        .title("title"+i)
                        .content("content"+i)
                        .imageUrl("s3-image-url#"+i)
                        .isPinned(true)
                        .build())
                .collect(Collectors.toList());
        List<Announcement> savedAnnouncements = announcementRepository.saveAll(announcements);

        List<Long> requestIds = savedAnnouncements.stream()
                .map(a -> a.getId())
                .collect(Collectors.toList());

        //when
        announcementRepository.deleteAllByUserId(requestIds);
        List<Announcement> result = announcementRepository.findAllById(requestIds);

        //then
        assertThat(result).isEmpty();
    }
}

package com.youngcamp.server.repository;

import com.youngcamp.server.domain.Announcement;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
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
        announcementRepository.deleteAllAnnouncementById(List.of(savedAnnouncement.getId()));
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
        announcementRepository.deleteAllAnnouncementById(requestIds);
        List<Announcement> result = announcementRepository.findAllById(requestIds);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void 존재하는공지사항만반환성공() {
        //given
        final Announcement announcement1 = Announcement.builder()
                .id(-1L)
                .title("공지사항 1")
                .content("내용 1")
                .isPinned(false)
                .imageUrl("image1.jpg")
                .build();

        final Announcement announcement2 = Announcement.builder()
                .id(-2L)
                .title("공지사항 2")
                .content("내용 2")
                .isPinned(false)
                .imageUrl("image2.jpg")
                .build();

        List<Announcement> savedAnnouncements = announcementRepository.saveAll(Arrays.asList(announcement1, announcement2));

        //when
        List<Long> idsToCheck = Arrays.asList(
                savedAnnouncements.get(0).getId(),
                savedAnnouncements.get(1).getId()
        );

        List<Long> existingIds = announcementRepository.findExistingIds(idsToCheck);

        //then
        assertThat(existingIds).containsExactlyInAnyOrder(savedAnnouncements.get(0).getId(), savedAnnouncements.get(1).getId());
        assertThat(existingIds).doesNotContain(-3L);
        assertThat(2).isEqualTo(existingIds.size());
    }

    @Test
    public void 공지사항조회성공() {
        //given
        List<Announcement> announcements = IntStream.range(0, 20)
                .mapToObj(i -> Announcement.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .imageUrl("imageUrl" + i)
                        .isPinned(true)
                        .build())
                .collect(Collectors.toList());

        announcementRepository.saveAll(announcements);

        AnnouncementSearch search = AnnouncementSearch.builder()
                .page(1)
                .build();

        PageRequest pageRequest = PageRequest.of(search.getPage() - 1, search.getSize());

        //when
        List<Announcement> results = announcementRepository.findAll(pageRequest).getContent();

        //then
        assertThat(results.size()).isEqualTo(10);
        assertThat(results.get(0).getTitle()).isEqualTo("title0");
    }

    @Test
    public void 게시글상세조회() {
        //given
        Announcement announcement = Announcement.builder()
                .title("title")
                .content("content")
                .imageUrl("image.jpg")
                .isPinned(true)
                .build();
        Announcement savedAnnouncement = announcementRepository.save(announcement);

        //when
        Optional<Announcement> result = announcementRepository.findById(savedAnnouncement.getId());

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getTitle()).isEqualTo("title");
    }
}

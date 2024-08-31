package com.youngcamp.server.service;

import com.youngcamp.server.domain.Announcement;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementDeleteRequest;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementPostRequest;
import com.youngcamp.server.dto.AnnouncementResponse.AnnouncementPostResponse;
import com.youngcamp.server.exception.NotFoundException;
import com.youngcamp.server.repository.AnnouncementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnnouncementServiceTest {

    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private AnnouncementService target;

    private final String title = "title";
    private final String content = "content";
    private final String imageUrl = "s3-image-url";
    private final Boolean isPinned = true;

    @Test
    public void 공지사항등록성공() {
        //given
        doReturn(announcement()).when(announcementRepository).save(any(Announcement.class));
        AnnouncementPostRequest request = AnnouncementPostRequest.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .isPinned(isPinned)
                .build();

        //when
        final AnnouncementPostResponse result = target.addAnnouncement(request);

        //then
        assertThat(result.getId()).isNotNull();

        //verify
        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }

    @Test
    public void 공지사항삭제실패_존재하지않은글삭제시도() {
        //given
        doReturn(Collections.EMPTY_LIST).when(announcementRepository).findExistingIds(Arrays.asList(-1L, -2L));
        AnnouncementDeleteRequest request = AnnouncementDeleteRequest.builder()
                .ids(Arrays.asList(-1L, -2L))
                .build();

        //when
        final NotFoundException result = assertThrows(NotFoundException.class, () -> target.deleteAnnouncement(request));

        //then
        assertThat(result.getResourceType()).isEqualTo("Announcement");
    }

    private Announcement announcement() {
        return Announcement.builder()
                .id(-1L)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .isPinned(isPinned)
                .build();
    }
}

package com.youngcamp.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AnnouncementRequest {

    @Getter
    @Builder
    public static class AnnouncementPostRequest {
        private String title;
        private String content;
        private String imageUrl;
        private String fileUrl;
        private Boolean isPinned;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnnouncementDeleteRequest{
        private List<Long> ids;
    }

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AnnouncementEditRequest {
        private String title;
        private String content;
        private String imageUrl;
        private String fileUrl;
        private Boolean isPinned;
    }
}

package com.youngcamp.server.dto;

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
        private Boolean isPinned;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnnouncementDeleteRequest{
        private List<Long> ids;
    }

    private static final int MAX_SIZE = 2000;
    @Builder
    @Getter
    public static class AnnouncementSearch {

        @Builder.Default
        private Integer page = 1;

        @Builder.Default
        private Integer size = 10;

        public long getOffset() {
            return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
        }
    }
}

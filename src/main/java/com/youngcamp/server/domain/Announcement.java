package com.youngcamp.server.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isPinned;

    private String imageUrl;

    private String fileUrl;

    public void editAnnouncement(String title, String content, String imageUrl, Boolean isPinned) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.isPinned = isPinned;
    }
}

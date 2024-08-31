package com.youngcamp.server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Boolean isPinned;

    private String imageUrl;

    public void editAnnouncement(String title, String content, String imageUrl, Boolean isPinned) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.isPinned = isPinned;
    }
}

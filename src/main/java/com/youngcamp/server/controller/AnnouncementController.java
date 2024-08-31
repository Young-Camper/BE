package com.youngcamp.server.controller;

import com.youngcamp.server.dto.AnnouncementRequest;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementDeleteRequest;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementEditRequest;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementPostRequest;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementSearch;
import com.youngcamp.server.dto.AnnouncementResponse;
import com.youngcamp.server.dto.AnnouncementResponse.AnnouncementEditResponse;
import com.youngcamp.server.dto.AnnouncementResponse.AnnouncementGetDetailResponse;
import com.youngcamp.server.dto.AnnouncementResponse.AnnouncementGetResponse;
import com.youngcamp.server.dto.AnnouncementResponse.AnnouncementPostResponse;
import com.youngcamp.server.service.AnnouncementService;
import com.youngcamp.server.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping("/api/v1/announcements")
    public SuccessResponse<AnnouncementPostResponse> postAnnouncement(@RequestBody AnnouncementPostRequest request) {
        AnnouncementPostResponse result = announcementService.addAnnouncement(request);
        return new SuccessResponse<>("Request processed successfully", result);
    }

    @DeleteMapping("/api/v1/announcements")
    public SuccessResponse<?> deleteAnnouncements(@RequestBody AnnouncementDeleteRequest request) {
        announcementService.deleteAnnouncement(request);
        return new SuccessResponse<>("Request processed successfully", null); //TODO
    }

    @GetMapping("/api/v1/announcements")
    public SuccessResponse<List<AnnouncementGetResponse>> getAnnouncements(@ModelAttribute AnnouncementSearch request) {
        List<AnnouncementGetResponse> result = announcementService.getAnnouncements(request);
        return new SuccessResponse<>("Request processed successfully", result);
    }

    @GetMapping("/api/v1/announcements/{announcementId}")
    public SuccessResponse<AnnouncementGetDetailResponse> getDetailAnnouncement(@PathVariable(name = "announcementId") Long announcementId) {
        AnnouncementGetDetailResponse result = announcementService.getDetailAnnouncement(announcementId);
        return new SuccessResponse<>("Request processed successfully", result);
    }

    @PatchMapping("/api/v1/announcements/{announcementId}")
    public SuccessResponse<AnnouncementEditResponse> editAnnouncement(
            @PathVariable(name = "announcementId") Long announcementId,
            @RequestBody AnnouncementEditRequest request
    ) {
        AnnouncementEditResponse result = announcementService.editAnnouncement(announcementId, request);
        return new SuccessResponse<>("Request processed successfully", result);
    }
}

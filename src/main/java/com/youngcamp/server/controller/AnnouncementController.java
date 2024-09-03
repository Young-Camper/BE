package com.youngcamp.server.controller;

import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementDeleteRequest;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementEditRequest;
import com.youngcamp.server.dto.AnnouncementRequest.AnnouncementPostRequest;
import com.youngcamp.server.dto.AnnouncementResponse.AnnouncementEditResponse;
import com.youngcamp.server.dto.AnnouncementResponse.AnnouncementGetDetailResponse;
import com.youngcamp.server.dto.AnnouncementResponse.AnnouncementGetResponse;
import com.youngcamp.server.dto.AnnouncementResponse.AnnouncementPostResponse;
import com.youngcamp.server.service.AdminChecker;
import com.youngcamp.server.service.AnnouncementService;
import com.youngcamp.server.utils.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Announcement", description = "공지사항 관련 API")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AdminChecker adminChecker;

    @Operation(
            summary = "공지사항 등록 API",
            description = "공지사항을 등록합니다."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/admin/v1/announcements")
    public SuccessResponse<AnnouncementPostResponse> postAnnouncement(@RequestBody AnnouncementPostRequest request) {
        checkAdminAccess();
        AnnouncementPostResponse result = announcementService.addAnnouncement(request);
        return new SuccessResponse<>("Request processed successfully", result);
    }

    @Operation(
            summary = "공지사항 삭제 API",
            description = "공지사항 ID값을 List로 넘겨 받아 Batch삭제 합니다."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/admin/v1/announcements")
    public SuccessResponse<?> deleteAnnouncements(@RequestBody AnnouncementDeleteRequest request) {
        checkAdminAccess();
        announcementService.deleteAnnouncement(request);
        return new SuccessResponse<>("공지사항 삭제 성공", null);
    }

    @Operation(
            summary = "공지사항 목록 조회 API",
            description = "존재하는 공지사항 목록을 조회합니다."
    )
    @GetMapping("/api/v1/announcements")
    public SuccessResponse<List<AnnouncementGetResponse>> getAnnouncements() {
        List<AnnouncementGetResponse> result = announcementService.getAnnouncements();
        return new SuccessResponse<>("공지사항 목록 조회 성공", result);
    }

    @Operation(
            summary = "공지사항 상세 조회 API",
            description = "공지사항 ID값으로 특정 공지 사항 내용을 조회합니다."
    )
    @GetMapping("/api/v1/announcements/{announcementId}")
    public SuccessResponse<AnnouncementGetDetailResponse> getDetailAnnouncement(@PathVariable(name = "announcementId") Long announcementId) {
        AnnouncementGetDetailResponse result = announcementService.getDetailAnnouncement(announcementId);
        return new SuccessResponse<>("공지사항 상세 조회 성공", result);
    }

    @Operation(
            summary = "공지사항 수정 API",
            description = "공지사항 ID값으로 특정 공지 사항 내용을 수정합니다."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/api/admin/v1/announcements/{announcementId}")
    public SuccessResponse<AnnouncementEditResponse> editAnnouncement(
            @PathVariable(name = "announcementId") Long announcementId,
            @RequestBody AnnouncementEditRequest request
    ) {
        checkAdminAccess();
        AnnouncementEditResponse result = announcementService.editAnnouncement(announcementId, request);
        return new SuccessResponse<>("공지사항 수정 성공", result);
    }

    @Operation(
            summary = "공지사항 검색 API",
            description = "QueryParameter keyword를 통해 공지사항을 목록을 조회합니다.(like %:keyword%)"
    )
    @GetMapping("/api/v1/announcements/search")
    public SuccessResponse<List<AnnouncementGetResponse>> searchAnnouncements(@RequestParam(name = "keyword") String keyword) {
        List<AnnouncementGetResponse> result = announcementService.searchAnnouncements(keyword);
        return new SuccessResponse<>("공지사항 검색 성공", result);
    }

    private void checkAdminAccess() {
        if (!adminChecker.isAdmin()) {
            throw new AccessDeniedException("Access is denied. Admin role required");
        }
    }
}

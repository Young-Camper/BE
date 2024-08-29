package com.youngcamp.server.controller;

import com.youngcamp.server.dto.PresignedUrlResponseDTO;
import com.youngcamp.server.exception.TooManyRequestsException;
import com.youngcamp.server.service.PresignedUrlService;
import com.youngcamp.server.utils.SuccessResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PresignedUrlController {

  private final Bucket bucket;
  private final PresignedUrlService presignedUrlService;

  // 생성자 주입
  @Autowired
  public PresignedUrlController(PresignedUrlService presignedUrlService) {
    this.presignedUrlService = presignedUrlService;
    // 10초에 5개의 요청을 허용하는 Rate Limiting
    Bandwidth limit = Bandwidth.simple(5, Duration.ofSeconds(10));
    this.bucket = Bucket4j.builder()
        .addLimit(limit)
        .build();
  }

  @Operation(
      summary = "Generate a Presigned URL",
      description = "Generates a presigned URL for uploading a file to S3.")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Presigned URL generated successfully",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SuccessResponse.class))),
          @ApiResponse(
              responseCode = "400",
              description = "Invalid request parameters",
              content = @Content),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content)
      })
  @GetMapping("/api/presigned")
  public SuccessResponse<PresignedUrlResponseDTO> getPresignedUrl(
      @Parameter(description = "The key for the object to be uploaded", example = "testimage.jpg")
      @RequestParam
      String key) {

    if (bucket.tryConsume(1)) {
      String presignedUrl = presignedUrlService.generatePresignedUrl(key);
      return new SuccessResponse<>(
          "Presigned URL successfully generated.", new PresignedUrlResponseDTO(presignedUrl));
    } else {
      throw new TooManyRequestsException("Too many requests");
    }
  }
}

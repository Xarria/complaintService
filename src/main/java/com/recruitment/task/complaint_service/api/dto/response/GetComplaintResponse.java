package com.recruitment.task.complaint_service.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record GetComplaintResponse(Long id, String productId, String content,
                                   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime creationDate,
                                   String reporter, String country, int count) {}

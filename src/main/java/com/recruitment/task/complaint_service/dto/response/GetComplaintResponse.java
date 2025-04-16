package com.recruitment.task.complaint_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record GetComplaintResponse(String productId, String content,
                                   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime creationDate,
                                   String reporter, String country, int count) {}

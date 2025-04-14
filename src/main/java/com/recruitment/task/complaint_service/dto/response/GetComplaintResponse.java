package com.recruitment.task.complaint_service.dto.response;

import java.time.LocalDateTime;

public record GetComplaintResponse(String productId, String content, LocalDateTime creationDate, String reporter,
                                   String country, int count) {}

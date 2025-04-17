package com.recruitment.task.complaint_service.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateComplaintContentRequest(@NotBlank String content) {}

package com.recruitment.task.complaint_service.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ComplaintCreateRequest(@NotBlank String productId, @NotBlank String content, @NotBlank String reporter) {


}

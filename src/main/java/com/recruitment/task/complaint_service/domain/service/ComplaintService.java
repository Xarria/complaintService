package com.recruitment.task.complaint_service.domain.service;

import com.recruitment.task.complaint_service.api.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.api.dto.response.GetComplaintResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ComplaintService {

    List<GetComplaintResponse> getAllComplaints();

    GetComplaintResponse updateComplaintContent(Long id, String content);

    GetComplaintResponse createOrUpdateComplaint(ComplaintCreateRequest request, HttpServletRequest httpRequest);
}

package com.recruitment.task.complaint_service.service;

import com.recruitment.task.complaint_service.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.dto.response.GetComplaintResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ComplaintService {

    List<GetComplaintResponse> getAllComplaints();

    GetComplaintResponse updateComplaintContent(Long id, String content);

    GetComplaintResponse createOrUpdateComplaint(ComplaintCreateRequest request, HttpServletRequest httpRequest);
}

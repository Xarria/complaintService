package com.recruitment.task.complaint_service.service;

import com.recruitment.task.complaint_service.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.dto.response.GetComplaintResponse;

import java.util.List;

public interface ComplaintService {

    List<GetComplaintResponse> getAllComplaints();

    GetComplaintResponse updateComplaintContent(String content);

    Long createComplaint(ComplaintCreateRequest complaintCreateRequest);
}

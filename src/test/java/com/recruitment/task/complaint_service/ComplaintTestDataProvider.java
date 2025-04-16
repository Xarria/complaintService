package com.recruitment.task.complaint_service;

import com.recruitment.task.complaint_service.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.dto.response.GetComplaintResponse;
import com.recruitment.task.complaint_service.model.Complaint;

import java.time.LocalDateTime;

public class ComplaintTestDataProvider {

    private static final String PRODUCT_ID = "product123";
    private static final String REPORTER = "reporter";

    public static Complaint validComplaint() {
        return Complaint.create(PRODUCT_ID, "some issue", REPORTER, "PL");
    }

    public static ComplaintCreateRequest validCreateRequest() {
        return new ComplaintCreateRequest(PRODUCT_ID, "some issue", REPORTER);
    }

    public static GetComplaintResponse sampleResponse() {
        return new GetComplaintResponse(PRODUCT_ID, "some issue",
                LocalDateTime.of(2025,5,12,12,30), REPORTER,
                "PL", 1);
    }
}

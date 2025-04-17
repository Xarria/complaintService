package com.recruitment.task.complaint_service.util;

import com.recruitment.task.complaint_service.api.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.api.dto.request.UpdateComplaintContentRequest;
import com.recruitment.task.complaint_service.api.dto.response.GetComplaintResponse;
import com.recruitment.task.complaint_service.domain.model.Complaint;

import java.time.LocalDateTime;

public class ComplaintTestDataProvider {

    private static final String PRODUCT_ID = "product123";
    private static final String PRODUCT_ID2 = "product456";
    private static final String REPORTER = "reporter";

    public static Complaint validComplaint() {
        return Complaint.create(PRODUCT_ID, "some issue", REPORTER, "PL");
    }

    public static Complaint validComplaint2() {
        return Complaint.create(PRODUCT_ID2, "some issue", REPORTER, "PL");
    }

    public static ComplaintCreateRequest validCreateRequest() {
        return new ComplaintCreateRequest(PRODUCT_ID, "some issue", REPORTER);
    }

    public static UpdateComplaintContentRequest validUpdateComplaintContentRequest() {
        return new UpdateComplaintContentRequest("new issue");
    }

    public static UpdateComplaintContentRequest invalidUpdateComplaintContentRequest() {
        return new UpdateComplaintContentRequest("");
    }

    public static GetComplaintResponse sampleResponse() {
        return new GetComplaintResponse(1L, PRODUCT_ID, "some issue",
                LocalDateTime.of(2025,5,12,12,30), REPORTER,
                "PL", 1);
    }
}

package com.recruitment.task.complaint_service.controller;

import com.recruitment.task.complaint_service.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.dto.response.GetComplaintResponse;
import com.recruitment.task.complaint_service.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    @Autowired
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping
    public ResponseEntity<List<GetComplaintResponse>> getAllComplaints() {
        List<GetComplaintResponse> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok().body(complaints);
    }

    @PostMapping
    public ResponseEntity<Long> createComplaint(@RequestBody ComplaintCreateRequest complaint) {
        this.complaintService.createComplaint(complaint);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping
    public ResponseEntity<GetComplaintResponse> updateComplaintContent(@RequestBody String content) {
        this.complaintService.updateComplaintContent(content);
        return ResponseEntity.ok().body(null);
    }

}

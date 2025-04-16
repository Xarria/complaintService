package com.recruitment.task.complaint_service.controller;

import com.recruitment.task.complaint_service.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.dto.response.GetComplaintResponse;
import com.recruitment.task.complaint_service.service.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
@Tag(name = "Complaints", description = "Complaint operations")
public class ComplaintController {
    private final ComplaintService complaintService;

    @Autowired
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping
    @Operation(summary = "Get all complaints")
    public ResponseEntity<List<GetComplaintResponse>> getAllComplaints() {
        List<GetComplaintResponse> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok().body(complaints);
    }

    @PostMapping
    @Operation(summary = "Create new complaint or increment counter on existing one based on reporter and productIc")
    public ResponseEntity<GetComplaintResponse> createComplaint(HttpServletRequest request,
                                                                @RequestBody @Valid ComplaintCreateRequest complaintRequest) {
        GetComplaintResponse complaint = this.complaintService.createOrUpdateComplaint(complaintRequest, request);
        return ResponseEntity.ok().body(complaint);
    }

    @PutMapping(path = "/{id}/content")
    @Operation(summary = "Update complaint's content")
    public ResponseEntity<GetComplaintResponse> updateComplaintContent(@PathVariable(name = "id") Long id,
                                                                       @RequestBody @NotBlank String content) {
        GetComplaintResponse complaint = this.complaintService.updateComplaintContent(id, content);
        return ResponseEntity.ok().body(complaint);
    }

}

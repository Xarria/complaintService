package com.recruitment.task.complaint_service.api.controller;

import com.recruitment.task.complaint_service.api.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.api.dto.request.UpdateComplaintContentRequest;
import com.recruitment.task.complaint_service.api.dto.response.GetComplaintResponse;
import com.recruitment.task.complaint_service.domain.service.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    @Operation(
            summary = "Get all complaints",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all complaints",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetComplaintResponse.class))))
            }
    )
    public ResponseEntity<List<GetComplaintResponse>> getAllComplaints() {
        List<GetComplaintResponse> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok().body(complaints);
    }

    @PostMapping
    @Operation(
            summary = "Create new complaint or increment counter on existing one",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Complaint created or counter incremented",
                            content = @Content(schema = @Schema(implementation = GetComplaintResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            }
    )
    public ResponseEntity<GetComplaintResponse> createComplaint(HttpServletRequest request,
                                                                @RequestBody @Valid ComplaintCreateRequest complaintRequest) {
        GetComplaintResponse complaint = this.complaintService.createOrUpdateComplaint(complaintRequest, request);
        return ResponseEntity.ok().body(complaint);
    }

    @PutMapping(path = "/{id}/content")
    @Operation(
            summary = "Update complaint's content",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Complaint content updated",
                            content = @Content(schema = @Schema(implementation = GetComplaintResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid content"),
                    @ApiResponse(responseCode = "404", description = "Complaint not found")
            }
    )
    public ResponseEntity<GetComplaintResponse> updateComplaintContent(@PathVariable(name = "id") Long id,
                                                                       @RequestBody @Valid UpdateComplaintContentRequest request) {
        GetComplaintResponse complaint = this.complaintService.updateComplaintContent(id, request.content());
        return ResponseEntity.ok().body(complaint);
    }

}

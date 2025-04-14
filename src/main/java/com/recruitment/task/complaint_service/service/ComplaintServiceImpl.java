package com.recruitment.task.complaint_service.service;

import com.recruitment.task.complaint_service.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.dto.response.GetComplaintResponse;
import com.recruitment.task.complaint_service.model.Complaint;
import com.recruitment.task.complaint_service.repository.ComplaintRepository;
import com.recruitment.task.complaint_service.util.mapper.ComplaintMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService{

    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper mapper;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository, ComplaintMapper mapper) {
        this.complaintRepository = complaintRepository;
        this.mapper = mapper;
    }

    public List<GetComplaintResponse> getAllComplaints() {
        return this.complaintRepository.findAll()
                .stream()
                .map(mapper::toGetComplaintResponse)
                .toList();
    }

    @Override
    public GetComplaintResponse updateComplaintContent(String content) {
        return null;
    }

    @Override
    public Long createComplaint(ComplaintCreateRequest complaintCreateRequest) {

        return null;
    }

    private Complaint createNewComplaint(ComplaintCreateRequest complaintCreateRequest) {
        Complaint complaintToCreate = mapper.toComplaint(complaintCreateRequest);
        return complaintRepository.save(complaintToCreate);
    }

    private Complaint incrementExistingComplaintCount(Complaint complaint) {
        complaint.incrementCount();
        return complaintRepository.save(complaint);
    }
}

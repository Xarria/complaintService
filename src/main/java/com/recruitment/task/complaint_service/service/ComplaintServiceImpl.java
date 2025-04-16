package com.recruitment.task.complaint_service.service;

import com.recruitment.task.complaint_service.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.dto.response.GetComplaintResponse;
import com.recruitment.task.complaint_service.exception.ComplaintNotFoundException;
import com.recruitment.task.complaint_service.model.Complaint;
import com.recruitment.task.complaint_service.repository.ComplaintRepository;
import com.recruitment.task.complaint_service.util.geolocation.CountryResolver;
import com.recruitment.task.complaint_service.util.geolocation.IpResolver;
import com.recruitment.task.complaint_service.util.mapper.ComplaintMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ComplaintServiceImpl implements ComplaintService{

    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper mapper;
    private final CountryResolver countryResolver;
    private final IpResolver ipResolver;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository complaintRepository,
                                ComplaintMapper mapper,
                                CountryResolver countryResolver,
                                IpResolver ipResolver) {
        this.complaintRepository = complaintRepository;
        this.mapper = mapper;
        this.countryResolver = countryResolver;
        this.ipResolver = ipResolver;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<GetComplaintResponse> getAllComplaints() {
        return this.complaintRepository.findAll().stream()
                .map(mapper::toGetComplaintResponse)
                .toList();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GetComplaintResponse updateComplaintContent(Long id, String content) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(ComplaintNotFoundException::new);
        complaint.updateContent(content);

        return mapper.toGetComplaintResponse(complaintRepository.save(complaint));
    }

    @Retryable(
            retryFor = {ObjectOptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public GetComplaintResponse createOrUpdateComplaint(ComplaintCreateRequest request, HttpServletRequest httpRequest) {
        String reporter = request.reporter();
        String productId = request.productId();
        Optional<Complaint> existing = complaintRepository.findByReporterAndProductId(reporter, productId);

        if (existing.isPresent()) {
            log.info("Found complaint for productId={} and reporter={}. Incrementing complaint's count.", reporter, productId);
            return mapper.toGetComplaintResponse(incrementExistingComplaintCount(existing.get()));
        } else {
            log.info("Complaint for productId={} and reporter={} not found. Creating new complaint.", reporter, productId);
            String ip = ipResolver.getIpFromRequest(httpRequest);
            String country = countryResolver.getCountryForIP(ip);
            return mapper.toGetComplaintResponse(createNewComplaint(request, country));
        }
    }

    private Complaint createNewComplaint(ComplaintCreateRequest request, String country) {
        Complaint complaintToCreate = Complaint.create(request.productId(), request.content(), request.reporter(), country);
        return complaintRepository.save(complaintToCreate);
    }

    private Complaint incrementExistingComplaintCount(Complaint complaint) {
        complaint.incrementCount();
        return complaintRepository.save(complaint);
    }
}

package com.recruitment.task.complaint_service.application.service;

import com.recruitment.task.complaint_service.api.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.api.dto.response.GetComplaintResponse;
import com.recruitment.task.complaint_service.api.exception.ComplaintNotFoundException;
import com.recruitment.task.complaint_service.domain.model.Complaint;
import com.recruitment.task.complaint_service.domain.repository.ComplaintRepository;
import com.recruitment.task.complaint_service.domain.service.CountryResolver;
import com.recruitment.task.complaint_service.domain.service.IpResolver;
import com.recruitment.task.complaint_service.infrastructure.mapper.ComplaintMapper;
import com.recruitment.task.complaint_service.util.ComplaintTestDataProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplaintServiceImplTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private ComplaintMapper complaintMapper;

    @Mock
    private CountryResolver countryResolver;

    @Mock
    private IpResolver ipResolver;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private ComplaintServiceImpl complaintService;

    @Test
    void shouldReturnAllComplaintsMappedToDto() {
        // given
        Complaint c1 = ComplaintTestDataProvider.validComplaint();
        Complaint c2 = ComplaintTestDataProvider.validComplaint();
        GetComplaintResponse dto1 = ComplaintTestDataProvider.sampleResponse();
        GetComplaintResponse dto2 = ComplaintTestDataProvider.sampleResponse();

        when(complaintRepository.findAll()).thenReturn(List.of(c1, c2));
        when(complaintMapper.toGetComplaintResponse(c1)).thenReturn(dto1);
        when(complaintMapper.toGetComplaintResponse(c2)).thenReturn(dto2);

        // when
        List<GetComplaintResponse> result = complaintService.getAllComplaints();

        // then
        assertTrue(result.containsAll(List.of(dto1, dto2)));
    }

    @Test
    void shouldUpdateComplaintContentAndReturnMappedDto() {
        // given
        Long id = 1L;
        String newContent = "Updated content";
        Complaint complaint = spy(ComplaintTestDataProvider.validComplaint());
        GetComplaintResponse response = ComplaintTestDataProvider.sampleResponse();

        when(complaintRepository.findById(id)).thenReturn(Optional.of(complaint));
        when(complaintRepository.save(complaint)).thenReturn(complaint);
        when(complaintMapper.toGetComplaintResponse(complaint)).thenReturn(response);

        // when
        GetComplaintResponse result = complaintService.updateComplaintContent(id, newContent);

        // then
        verify(complaint).updateContent(newContent);
        verify(complaintRepository).save(complaint);
        assertEquals(response, result);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingComplaintContent() {
        // given
        Long id = 123L;
        when(complaintRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(ComplaintNotFoundException.class,
                () -> complaintService.updateComplaintContent(id, "updated content"));
    }

    @Test
    void shouldIncrementExistingComplaintCount() {
        // given
        ComplaintCreateRequest request = ComplaintTestDataProvider.validCreateRequest();
        Complaint existingComplaint = spy(ComplaintTestDataProvider.validComplaint());
        GetComplaintResponse response = ComplaintTestDataProvider.sampleResponse();

        when(complaintRepository.findByReporterAndProductId(request.reporter(), request.productId()))
                .thenReturn(Optional.of(existingComplaint));
        when(complaintRepository.save(existingComplaint)).thenReturn(existingComplaint);
        when(complaintMapper.toGetComplaintResponse(existingComplaint)).thenReturn(response);

        // when
        GetComplaintResponse result = complaintService.createOrUpdateComplaint(request, httpServletRequest);

        // then
        verify(existingComplaint).incrementCount();
        verify(complaintRepository).save(existingComplaint);
        assertEquals(response, result);
    }

    @Test
    void shouldCreateNewComplaintWhenNotExisting() {
        // given
        ComplaintCreateRequest request = ComplaintTestDataProvider.validCreateRequest();
        String ip = "89.64.42.1";
        String country = "PL";
        Complaint savedComplaint = ComplaintTestDataProvider.validComplaint();
        GetComplaintResponse response = ComplaintTestDataProvider.sampleResponse();

        when(complaintRepository.findByReporterAndProductId(request.reporter(), request.productId()))
                .thenReturn(Optional.empty());
        when(ipResolver.getIpFromRequest(httpServletRequest)).thenReturn(ip);
        when(countryResolver.getCountryForIP(ip)).thenReturn(country);
        when(complaintRepository.save(any())).thenReturn(savedComplaint);
        when(complaintMapper.toGetComplaintResponse(savedComplaint)).thenReturn(response);

        // when
        GetComplaintResponse result = complaintService.createOrUpdateComplaint(request, httpServletRequest);

        // then
        verify(complaintRepository).save(any());
        assertEquals(response, result);
    }
}
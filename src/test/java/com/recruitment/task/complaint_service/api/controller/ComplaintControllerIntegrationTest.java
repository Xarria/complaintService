package com.recruitment.task.complaint_service.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recruitment.task.complaint_service.api.dto.request.UpdateComplaintContentRequest;
import com.recruitment.task.complaint_service.util.ComplaintTestDataProvider;
import com.recruitment.task.complaint_service.config.CountryResolverTestConfig;
import com.recruitment.task.complaint_service.api.dto.request.ComplaintCreateRequest;
import com.recruitment.task.complaint_service.domain.model.Complaint;
import com.recruitment.task.complaint_service.domain.repository.ComplaintRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@Import(CountryResolverTestConfig.class)
class ComplaintControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ComplaintRepository complaintRepository;

    @AfterEach
    void setUp() {
        complaintRepository.deleteAll();
    }

    @Test
    void shouldCreateNewComplaint() throws Exception {
        ComplaintCreateRequest request = ComplaintTestDataProvider.validCreateRequest();

        mockMvc.perform(post("/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Forwarded-For", "89.64.42.1")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("product123"))
                .andExpect(jsonPath("$.country").value("PL"))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    void shouldCreateNewComplaintWithUndefinedCountryWhenIpIncorrect() throws Exception {
        ComplaintCreateRequest request = ComplaintTestDataProvider.validCreateRequest();

        mockMvc.perform(post("/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Forwarded-For", "127.0.0.1")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("product123"))
                .andExpect(jsonPath("$.country").value("Undefined"))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    void shouldIncrementCountWhenDuplicateComplaint() throws Exception {
        complaintRepository.save(ComplaintTestDataProvider.validComplaint());

        ComplaintCreateRequest request = ComplaintTestDataProvider.validCreateRequest();

        mockMvc.perform(post("/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2));
    }

    @Test
    void shouldReturnAllComplaints() throws Exception {
        complaintRepository.save(ComplaintTestDataProvider.validComplaint());
        complaintRepository.save(ComplaintTestDataProvider.validComplaint2());

        mockMvc.perform(get("/complaints"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateComplaintContent() throws Exception {
        Complaint saved = complaintRepository.save(ComplaintTestDataProvider.validComplaint());
        UpdateComplaintContentRequest request = ComplaintTestDataProvider.validUpdateComplaintContentRequest();

        mockMvc.perform(put("/complaints/" + saved.getId() + "/content")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(request.content()));
    }

    @Test
    void shouldReturnNotFoundWhenComplaintNotExisting() throws Exception {
        UpdateComplaintContentRequest request = ComplaintTestDataProvider.validUpdateComplaintContentRequest();
        mockMvc.perform(put("/complaints/123/content")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        ComplaintCreateRequest request = new ComplaintCreateRequest("", "", "");

        mockMvc.perform(post("/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

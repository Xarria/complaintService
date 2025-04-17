package com.recruitment.task.complaint_service.domain.repository;

import com.recruitment.task.complaint_service.domain.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findByReporterAndProductId(String reporter, String productId);
}

package com.recruitment.task.complaint_service.infrastructure.mapper;

import com.recruitment.task.complaint_service.api.dto.response.GetComplaintResponse;
import com.recruitment.task.complaint_service.domain.model.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ComplaintMapper {

    GetComplaintResponse toGetComplaintResponse(Complaint complaint);

}

package com.recruitment.task.complaint_service.domain.service;

import jakarta.servlet.http.HttpServletRequest;

public interface IpResolver {

    String getIpFromRequest(HttpServletRequest httpServletRequest);
}

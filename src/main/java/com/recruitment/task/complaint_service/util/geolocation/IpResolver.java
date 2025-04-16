package com.recruitment.task.complaint_service.util.geolocation;

import jakarta.servlet.http.HttpServletRequest;

public interface IpResolver {

    String getIpFromRequest(HttpServletRequest httpServletRequest);
}

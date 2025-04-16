package com.recruitment.task.complaint_service.util.geolocation;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HttpHeaderIpResolver implements IpResolver {

    private static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";

    @Override
    public String getIpFromRequest(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(httpServletRequest.getHeader(X_FORWARDED_FOR_HEADER))
                .filter(header -> !header.isBlank())
                .map(header -> header.split(",")[0].trim())
                .orElse(httpServletRequest.getRemoteAddr());
    }
}

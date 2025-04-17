package com.recruitment.task.complaint_service.infrastructure.ip;

import com.recruitment.task.complaint_service.domain.service.CountryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class IpApiCountryResolver implements CountryResolver {

    private static final String IP_UNDEFINED = "Undefined";
    @Value("${ipapi.base-url}")
    private String baseUrl;
    @Value("${ipapi.suffix}")
    private String urlSuffix;

    private final RestTemplate restTemplate;

    @Autowired
    public IpApiCountryResolver(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getCountryForIP(String ip) {
        try {
            String url = baseUrl + ip + urlSuffix;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception ex) {
            log.error("Country code undefined. API threw exception={}", ex);
            return IP_UNDEFINED;
        }
        return IP_UNDEFINED;
    }
}

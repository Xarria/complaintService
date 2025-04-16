package com.recruitment.task.complaint_service.util.geolocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IpApiCountryResolver implements CountryResolver {

    private final static String IP_UNDEFINED = "Undefined";
    private final RestTemplate restTemplate;

    @Autowired
    public IpApiCountryResolver(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getCountryForIP(String ip) {
        try {
            String url = "https://ipapi.co/" + ip + "/country/";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            return IP_UNDEFINED;
        }
        return IP_UNDEFINED;
    }
}

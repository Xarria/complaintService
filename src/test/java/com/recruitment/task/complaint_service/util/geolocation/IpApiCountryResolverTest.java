package com.recruitment.task.complaint_service.util.geolocation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IpApiCountryResolverTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private IpApiCountryResolver countryResolver;

    @Test
    void shouldReturnCountryCodeWhenApiResponseSuccessful() {
        // given
        String ip = "89.64.42.1";
        String expectedCountry = "PL";
        ResponseEntity<String> response = new ResponseEntity<>(expectedCountry, HttpStatus.OK);

        when(restTemplate.getForEntity("https://ipapi.co/" + ip + "/country/", String.class))
                .thenReturn(response);

        // when
        String result = countryResolver.getCountryForIP(ip);

        // then
        assertThat(result).isEqualTo("PL");
    }

    @Test
    void shouldReturnUndefinedWhenApiResponseError() {
        // given
        String ip = "89.64.42.1";
        ResponseEntity<String> response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(response);

        // when
        String result = countryResolver.getCountryForIP(ip);

        // then
        assertThat(result).isEqualTo("Undefined");
    }

}
package com.recruitment.task.complaint_service.util.geolocation;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HttpHeaderIpResolverTest {

    @InjectMocks
    private HttpHeaderIpResolver ipProvider;

    @Mock
    private HttpServletRequest request;

    @Test
    void shouldReturnFirstIpFromXForwardedForHeader() {
        // given
        String forwardedHeader = "89.64.42.1, 192.168.0.1, 127.0.0.1";
        when(request.getHeader("X-Forwarded-For")).thenReturn(forwardedHeader);

        // when
        String ip = ipProvider.getIpFromRequest(request);

        // then
        assertThat(ip).isEqualTo("89.64.42.1");
    }

    @Test
    void shouldReturnRemoteAddrWhenXForwardedForHeaderIsNull() {
        // given
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        // when
        String ip = ipProvider.getIpFromRequest(request);

        // then
        assertThat(ip).isEqualTo("127.0.0.1");
    }

    @Test
    void shouldReturnRemoteAddrWhenXForwardedForIsBlank() {
        // given
        when(request.getHeader("X-Forwarded-For")).thenReturn("");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        // when
        String ip = ipProvider.getIpFromRequest(request);

        // then
        assertThat(ip).isEqualTo("127.0.0.1");
    }
}
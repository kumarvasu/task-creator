package com.task.agentic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

@Component
public class TaskPublisher {

    String encodeUrl() throws UnsupportedEncodingException {
        String baseUrl = "https://prod-24.uksouth.logic.azure.com/workflows/033a31d83c4b4a29b2e69548c7e3b023/triggers/manual/paths/invoke";

        String queryParams = String.format(
                "?api-version=%s&sp=%s&sv=%s&sig=%s",
                URLEncoder.encode("2016-06-01", "UTF-8"),
                URLEncoder.encode("/triggers/manual/run", "UTF-8"),
                URLEncoder.encode("1.0", "UTF-8"),
                URLEncoder.encode("GHOyFyeP7nA6s3pXHvDTdxjJZBBrzhYP8cKj7bQ__Qk", "UTF-8")
        );

        String encodedUrl = baseUrl + queryParams;
        System.out.println("Encoded URL: " + encodedUrl);
        return encodedUrl;
    }


    public String publish(Response jsonPayload) {
        String url = null;
        try {
            url = encodeUrl();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost(url);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(jsonPayload)));

            try (ClassicHttpResponse response = httpClient.execute(postRequest)) {
                System.out.println("Response Code: " + response.getCode());
                System.out.println("Response Body: " + response.getEntity().getContent().readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}

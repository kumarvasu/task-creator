package com.task.agentic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TaskPublisher {

    private final RestTemplate restTemplate = new RestTemplate();

    public String publish(Response response){
        String url = "https://prod-24.uksouth.logic.azure.com:443/workflows/033a31d83c4b4a29b2e69548c7e3b023/triggers/manual/paths/invoke?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=GHOyFyeP7nA6s3pXHvDTdxjJZBBrzhYP8cKj7bQ__Qk";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Response> entity = new HttpEntity<>(response, headers); // Adjust payload as needed
        ResponseEntity<String> response1 = restTemplate.postForEntity(url, entity, String.class);
        return response1.getBody();
    }
}

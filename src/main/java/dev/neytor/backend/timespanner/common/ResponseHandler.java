package dev.neytor.backend.timespanner.common;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@NoArgsConstructor
public class ResponseHandler {
    public ResponseEntity<Object> generateResponse(HttpStatus status, Optional<Object> contentObject){
        Map<String, Object> responseMap = new HashMap<>();
        contentObject.ifPresent(content -> responseMap.put("content", content));
        responseMap.put("timestamp", LocalDateTime.now());
        responseMap.put("status", status);
        return ResponseEntity.status(status).body(responseMap);
    }
}

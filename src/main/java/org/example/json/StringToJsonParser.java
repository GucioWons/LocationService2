package org.example.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class StringToJsonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode parse(String src){
        try {
            return objectMapper.readTree(src);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

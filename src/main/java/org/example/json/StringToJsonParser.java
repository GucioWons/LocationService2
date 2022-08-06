package org.example.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class StringToJsonParser {
    private final ObjectMapperConfig objectMapperConfig;

    public StringToJsonParser(ObjectMapperConfig objectMapperConfig) {
        this.objectMapperConfig = objectMapperConfig;
    }

    public JsonNode parse(String src){
        try {
            return objectMapperConfig.getObjectMapper().readTree(src);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

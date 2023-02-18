package org.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class JsonToCsvConverter {
    private final CsvMapper csvMapper = new CsvMapper();

    public void jsonToCsv(JsonNode jsonNode){
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        jsonNode.elements().next().fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
        try {
            csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValue(new File("src/main/resources/data.csv"), jsonNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

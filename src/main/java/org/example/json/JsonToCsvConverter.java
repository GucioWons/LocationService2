package org.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;

public class JsonToCsvConverter {
    private static CsvMapper csvMapper = getDefaultCsvMapper();

    private static CsvMapper getDefaultCsvMapper(){
        return new CsvMapper();
    }

    public static void JsonToCsv(JsonNode jsonNode){
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

package org.example.csv;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.csv.CsvReader.getJsonParsedToCsv;
import static org.example.json.Json.*;
import static org.example.json.Json.getJsonFromUrl;

@Service
public class CsvService {
    public static String getFieldsFromUrl(int x){
        JsonToCsv(getFlattenedJson(getJsonFromUrl(x)));
        List<String> fields = new ArrayList<>();
        fields.add("_type");
        fields.add("_id");
        fields.add("name");
        fields.add("type");
        fields.add("latitude");
        fields.add("longitude");
        return getJsonParsedToCsv(fields);
    }

    public static String getChoosenFieldsFromUrl(int x, List<String> fields){
        JsonToCsv(getFlattenedJson(getJsonFromUrl(x)));
        return getJsonParsedToCsv(fields);
    }

}

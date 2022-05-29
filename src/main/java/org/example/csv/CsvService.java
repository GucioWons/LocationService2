package org.example.csv;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.csv.CsvReader.CsvReader;
import static org.example.json.Json.*;
import static org.example.json.Json.JsonFromUrl;

@Service
public class CsvService {
    public static String endpoint_1(int x){
        JsonToCsv(flatten(JsonFromUrl(x)));
        List<String> list = new ArrayList<>();
        list.add("_type");
        list.add("_id");
        list.add("name");
        list.add("type");
        list.add("latitude");
        list.add("longitude");
        return CsvReader(list);
    }
    public static String endpoint_2(int x, List<String> list){
        JsonToCsv(flatten(JsonFromUrl(x)));
        return CsvReader(list);
    }

}

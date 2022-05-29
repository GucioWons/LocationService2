package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.example.csv.Csv.CsvReader;
import static org.example.json.Json.*;

public class Main {
    public static void main(String[] args) {
        JsonToCsv(flatten(JsonFromUrl(5)));
        List<String> list = new ArrayList<>();
        list.add("_type");
        list.add("name");
        CsvReader(list);
    }
}
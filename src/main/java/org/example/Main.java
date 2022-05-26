package org.example;

import static org.example.json.Json.*;

public class Main {
    public static void main(String[] args) {
        JsonToCsv(flatten(JsonFromUrl(5)));;
    }
}
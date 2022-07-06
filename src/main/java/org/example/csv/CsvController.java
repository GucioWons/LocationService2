package org.example.csv;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.example.csv.CsvService.*;

@RestController
@RequestMapping("/generate")
public class CsvController {
    @GetMapping("/endpoint1/{x}")
    public static void getFields(HttpServletResponse response, @PathVariable int x) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(getFieldsFromUrl(x));
    }

    @GetMapping("/endpoint2/{x}/{fields}")
    public static void getChoosenFields(HttpServletResponse response,@PathVariable int x, @PathVariable List<String> fields) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(getChoosenFieldsFromUrl(x, fields));
    }
}

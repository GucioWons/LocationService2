package org.example.csv;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/generate")
public class CsvController {
    private CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("/endpoint1/{x}")
    public void getFields(HttpServletResponse response, @PathVariable int x) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(csvService.getFieldsFromUrl(x));
    }

    @GetMapping("/endpoint2/{x}/{fields}")
    public void getChoosenFields(HttpServletResponse response,@PathVariable int x, @PathVariable List<String> fields) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(csvService.getChosenFieldsFromUrl(x, fields));
    }
}

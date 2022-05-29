package org.example.csv;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.example.csv.CsvService.endpoint_1;
import static org.example.csv.CsvService.endpoint_2;
@RestController
@RequestMapping("/generate")
public class CsvController {
    @GetMapping("/endpoint1/{x}")
    public static void endpoint1(HttpServletResponse response, @PathVariable int x) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(endpoint_1(x));
    }
    @GetMapping("/endpoint2/{x}/{list}")
    public static void endpoint2(HttpServletResponse response,@PathVariable int x, @PathVariable List<String> list) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(endpoint_2(x, list));
    }
}

package com.exercise;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExerciseController {
    final ExerciseRepo repo;

    public ExerciseController(ExerciseRepo repo) {
        this.repo = repo;
    }

    @PostMapping("/upload")
    public void upload(@Validated @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                var csvToBean = new CsvToBeanBuilder<Exercise>(reader)
                        .withType(Exercise.class)
                        .build();
                var codeList = csvToBean.parse();
                if (!codeList.isEmpty()) {
                    repo.saveAll(codeList);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    @GetMapping("/downloadAll")
    public void downloadAll(HttpServletResponse response)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        export(response, repo.findAll());
    }

    @GetMapping("/downloadByCode/{code}")
    public void downloadByCode(HttpServletResponse response, @PathVariable("code") String code)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        var exercise = repo.findById(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        export(response, List.of(exercise));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("deleteAll")
    public void deleteAll() {
        repo.deleteAll();
    }

    private void export(HttpServletResponse response, List<Exercise> list)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        var filename = "exercise.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        var writer = new StatefulBeanToCsvBuilder<Exercise>(response.getWriter())
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();
        writer.write(list);
    }
}

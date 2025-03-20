package com.QuestionBankProject.QBanker.Controller;

import com.QuestionBankProject.QBanker.Services.Impl.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @PostMapping("/generate")
    //it takes the JSON request body as input
    /*
    * Map<String, Object> request = {
                                        "noOfSets" -> 3,                      // Integer
                                        "htmlContent" -> "<html>...</html>"   // String
                                    };
    *
    * The @RequestBody annotation in Spring Boot is used to bind the HTTP request body to a method parameter.

    * */
    public ResponseEntity<byte[]> generatePDFs(@RequestBody Map<String, Object> request) {
        try {
            int noOfSets = Integer.parseInt(request.get("noOfSets").toString()); //string to integer
            String htmlContent = request.get("htmlContent").toString();


            //Fetch image data from the database
//            String base64Image=pdfGeneratorService.getBase64ImageFromDB();


            ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(zipOutputStream);

            if (noOfSets==1){
                ByteArrayOutputStream pdfOutputStream=pdfGeneratorService.generatePdfFromHtml(htmlContent);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=QuestionBank.pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfOutputStream.toByteArray());
            }

            for (int i = 1; i <= noOfSets; i++) {
                htmlContent=shuffleHtmlQuestions(htmlContent);
                ByteArrayOutputStream pdfOutputStream = pdfGeneratorService.generatePdfFromHtml(htmlContent);

                ZipEntry zipEntry = new ZipEntry("QuestionBank_Set" + i + ".pdf");
                zip.putNextEntry(zipEntry);
                zip.write(pdfOutputStream.toByteArray());
                zip.closeEntry();
            }

            zip.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=QuestionBanks.zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(zipOutputStream.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    private String shuffleHtmlQuestions(String htmlContent) {
        // Extract the question list <ul> content
        Pattern pattern = Pattern.compile("<ol.*?>(.*?)</ol>", Pattern.DOTALL); //The regex pattern
        // "<ul.*?>(.*?)</ul>" is used to match the <ul> element and capture its inner content.
        //The Pattern.DOTALL flag allows . (dot) to match newline characters, ensuring multi-line <ul> content is captured.
        Matcher matcher = pattern.matcher(htmlContent);

        if (matcher.find()) { //matcher.find() checks if the <ul> tag exists in the htmlContent.
            String listContent = matcher.group(1); // Get inner <li> elements

            // Extract individual <li> elements
            List<String> questions = new ArrayList<>(Arrays.asList(listContent.split("(?=<li)")));

            // Shuffle the questions
            Collections.shuffle(questions);

            // Rebuild the shuffled HTML list
            String shuffledList = String.join("", questions); //Rejoins the shuffled <li> elements into a single string.
            return htmlContent.replace(listContent, shuffledList);
        }

        return htmlContent; // Return original if <ul> not found
    }


    @GetMapping("/header/get")
    public ResponseEntity<String> getHeader() {
        String headerHtml = "<div style='text-align:center; font-weight:bold;'>Question Bank Header</div>";
        return ResponseEntity.ok(headerHtml);
    }

}

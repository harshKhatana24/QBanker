package com.QuestionBankProject.QBanker.Services.Impl;

import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PdfGeneratorService {

    public ByteArrayOutputStream generatePdfFromHtml(String htmlContent) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        try {
            // **Step 1: Fix image URLs**
            String baseUrl = "http://localhost:8080/";
            htmlContent = processImageUrls(htmlContent);

            // **Step 2: Set base URL for correct image rendering**
            renderer.getSharedContext().setBaseURL(baseUrl);

            // **Step 3: Set document styles (if any) and layout**
            // Add any custom layout or styles if necessary

            System.out.println(htmlContent);

            // **Step 4: Render PDF**
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            e.printStackTrace(); // Print any error logs
            throw new DocumentException("Error while generating PDF: " + e.getMessage());
        }

        return outputStream;
    }

    /**
     * Fixes image URLs by making sure they are absolute and properly encoded.
     */
    public static String processImageUrls(String htmlContent) {
        Pattern pattern = Pattern.compile("src=['\"](http://localhost:8080/[^'\"]*)['\"]");
        Matcher matcher = pattern.matcher(htmlContent);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            System.out.println();
            System.out.println(imageUrl.replace(" ","%20"));
            imageUrl=imageUrl.replace(" ","%20");
            System.out.println();
            try {
                // Convert the image URL to Base64
                String base64Image = convertImageToBase64(imageUrl);

                // Prepare the Base64 image as an embedded src
                String base64ImageSrc = "data:image/jpeg;base64," + base64Image;
                matcher.appendReplacement(sb, "src=\"" + base64ImageSrc + "\"");
            } catch (IOException e) {
                e.printStackTrace();
                // If there's an error converting the image, you can leave the URL as is or handle it differently
            }
        }
        matcher.appendTail(sb);

        // Ensure all <img> tags are properly closed in XHTML format
        return sb.toString().replaceAll("<img([^>]*)>", "<img$1 />");
    }


    /**
     * (Optional) Converts image URL to Base64 encoded string.
     */
    public static String convertImageToBase64(String imageUrl) throws IOException {
        // Read the image file from the URL
        URL url = new URL(imageUrl);
        try (InputStream is = url.openStream()) {
            // Read the image data into a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            // Convert the image byte array into Base64
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        }
    }

}

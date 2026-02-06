package com.insurance.claims.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Service for parsing PDF and TXT documents to extract raw text
 */
@Service
public class DocumentParserService {
    
    /**
     * Parse document and extract raw text
     * @param file Uploaded document (PDF or TXT)
     * @return Raw text content
     * @throws IOException if parsing fails
     */
    public String parseDocument(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        
        if (filename == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }
        
        if (filename.toLowerCase().endsWith(".pdf")) {
            return parsePdf(file);
        } else if (filename.toLowerCase().endsWith(".txt")) {
            return parseTxt(file);
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only PDF and TXT files are supported.");
        }
    }
    
    /**
     * Parse PDF document using Apache PDFBox
     */
    private String parsePdf(MultipartFile file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
    
    /**
     * Parse TXT document
     */
    private String parseTxt(MultipartFile file) throws IOException {
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }
}


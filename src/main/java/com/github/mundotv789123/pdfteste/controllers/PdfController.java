package com.github.mundotv789123.pdfteste.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    private final TemplateEngine templateEngine;

    public PdfController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Object> viewPdf(@PathVariable String id) {
        Context context = new Context();
        context.setVariable("obs", "Se não pagar vai dar ruim: "+id);
        String html = templateEngine.process("pdf-template", context);

        return ResponseEntity.ok().body(html);
    }

    @GetMapping("/generate/{id}")
    public ResponseEntity<Object> generatePdf(@PathVariable String id) throws IOException {
        Context context = new Context();
        context.setVariable("obs", "Documento de teste, id: "+id);
        String html = templateEngine.process("pdf-template", context);

        String outputFolder = "documento.pdf";
        File outputFile = new File("documento.pdf");
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new UrlResource(outputFile.toURI()));
    }
}

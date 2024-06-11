package com.github.mundotv789123.pdfteste.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.mundotv789123.pdfteste.models.PdfDataModel;
import com.github.mundotv789123.pdfteste.models.PdfDataRowModel;
import com.github.mundotv789123.pdfteste.services.PdfService;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    private final PdfService service;
    
    private final PdfDataModel data;

    public PdfController(PdfService service) {
        this.service = service;

        java.util.List<PdfDataRowModel> rows = new ArrayList<PdfDataRowModel>();
        rows.add(new PdfDataRowModel(1, "Item exemplo"));
        rows.add(new PdfDataRowModel(1, "Item exemplo2"));
        this.data = new PdfDataModel(1, "teste", "teste@gmail.com", "21999999999", null, rows);
    }

    @GetMapping("/view")
    public ResponseEntity<Object> viewPdf(@PathParam("obs") String obs) {
        if (obs != null)
            data.setObs(obs);

        return ResponseEntity.ok().body(service.viewHtmlTemplate(this.data));
    }

    @GetMapping("/generate")
    public ResponseEntity<Object> generatePdf(@PathParam("id") String obs) throws IOException {
        if (obs != null)
            data.setObs(obs);

        File file = service.generatePdf(data, "documento.pdf");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new UrlResource(file.toURI()));
    }
}

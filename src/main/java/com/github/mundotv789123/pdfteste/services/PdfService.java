package com.github.mundotv789123.pdfteste.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.github.mundotv789123.pdfteste.models.PdfDataModel;

@Service
public class PdfService {

    @Value("${spring.application.pdf.dirname}")
    private String dirName;

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String viewHtmlTemplate(PdfDataModel data) {
        Context context = new Context();
        context.setVariable("data", data);
        String html = templateEngine.process("pdf-template", context);

        return html;
    }

    public File generatePdf(PdfDataModel data, String fileName) throws IOException {
        File dir = new File(this.dirName);

        if (!dir.exists())
            dir.mkdirs();
        else if(!dir.isDirectory())
            throw new RuntimeException("dirname is not a directory");

        String html = this.viewHtmlTemplate(data);

        File outputFile = new File(dir, fileName);
        OutputStream outputStream = new FileOutputStream(outputFile);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
        return outputFile;
    }
}

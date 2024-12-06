package com.github.mundotv789123.pdfteste.models;

import java.text.ParseException;
import java.util.List;

import javax.swing.text.MaskFormatter;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class PdfDataModel extends AbstractModel {
    private static String TEMPLATE_PATH = "pdf-template";

    private final @Getter int id;
    private final @Getter String nome;
    private final @Getter String email;
    private final @Getter String telefone;
    private @Getter @Setter @Nullable String obs;
    private final @Getter List<PdfDataRowModel> rows;

    public String getFormatedTelefone() throws ParseException {
        MaskFormatter maskFormatter = new MaskFormatter("(##) #####-####"); 
        maskFormatter.setValueContainsLiteralCharacters(false);
        return maskFormatter.valueToString(telefone);
    }

    @Override
    public String getTemplatePath() {
        return TEMPLATE_PATH;
    }
}

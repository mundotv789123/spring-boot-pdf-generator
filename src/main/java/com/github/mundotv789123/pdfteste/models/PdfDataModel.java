package com.github.mundotv789123.pdfteste.models;

import java.text.ParseException;
import java.util.List;

import javax.swing.text.MaskFormatter;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class PdfDataModel {
    private @Getter int id;
    private @Getter String nome;
    private @Getter String email;
    private @Getter String telefone;
    private @Getter @Setter @Nullable String obs;
    private @Getter List<PdfDataRowModel> rows;

    public String getFormatedTelefone() throws ParseException {
        MaskFormatter maskFormatter = new MaskFormatter("(##) #####-####"); 
        maskFormatter.setValueContainsLiteralCharacters(false);
        return maskFormatter.valueToString(telefone);
    }
}

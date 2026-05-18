package com.viajessolparaiso.gestion_ofertas.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfService {

    public String extraerTexto(MultipartFile file) throws IOException {

        PDDocument document = Loader.loadPDF(file.getInputStream().readAllBytes());

        PDFTextStripper stripper = new PDFTextStripper();

        String texto = stripper.getText(document);

        document.close();

        return texto;
    }
}
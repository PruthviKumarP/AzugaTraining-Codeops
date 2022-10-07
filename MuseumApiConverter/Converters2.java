package com.azuga.museum;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface Converters2 {
    String url = "https://collectionapi.metmuseum.org/public/collection/v1/objects/";
    String csvPath = "./src/com/azuga/museum/Reports2/museum.csv";
    String jsonPath = "./src/com/azuga/museum/Reports2/museum.json";
    String excelPath = "./src/com/azuga/museum/Reports2/museum.xlsx";
    String htmlPath = "./src/com/azuga/museum/Reports2/museum.html";
    String pdfPath = "./src/com/azuga/museum/Reports2/museum.pdf";
    String xmlPath = "./src/com/azuga/museum/Reports2/museum.xml";


    void restApiCall(String url);
    public void jsonCsv(String inputJsonPath, String outputCsvPath) throws Exception;
    void csvExcel(String inputCsvPath, String outputExcelPath);
    void csvHtml(String inputCsvPath, String outputHtmlPath);
    void csvPdf(String inputCsvPath, String outputPdfPath) throws IOException, DocumentException, CsvValidationException;
    void jsonXml(String inputJsonPath, String outputXmlPath) throws IOException, NoSuchAlgorithmException;

    String checkSum(String filename) throws IOException, NoSuchAlgorithmException;

}

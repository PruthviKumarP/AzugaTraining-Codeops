/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author - PruthviKumarP.
 */

package com.azuga.museum;

import java.io.IOException;

public interface Converters {
    String url = "https://collectionapi.metmuseum.org/public/collection/v1/objects/";
    String csvPath = "./src/com/azuga/museum/Reports/museum.csv";
    String jsonPath = "./src/com/azuga/museum/Reports/museum.json";
    String excelPath = "./src/com/azuga/museum/Reports/museum.xlsx";
    String htmlPath = "./src/com/azuga/museum/Reports/museum.html";
    String pdfPath = "./src/com/azuga/museum/Reports/museum.pdf";
    String xmlPath = "./src/com/azuga/museum/Reports/museum.xml";

    void api() throws IOException, InterruptedException;
    void csvHtml(String inputCsvPath, String outputHtmlPath);
    void csvPdf(String inputCsvPath, String outputPdfPath) throws IOException;
    void jsonXml(String inputJsonPath, String outputXmlPath);
    void csvExcel(String inputCsvPath, String outputExcelPath);
}

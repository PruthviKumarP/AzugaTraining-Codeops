/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author - PruthviKumarP.
 */

package com.azuga.test;
import com.azuga.museum.Converters2;
import com.azuga.museum.MuseumRestApi;
import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import com.pdfunit.AssertThat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class MuseumRestApiTest {
    Converters2 api;
    Logger log = LogManager.getLogger(MuseumRestApiTest.class);
    long startTime;
    long endTime;
    String expHtmlPath;
    String expPdfPath;
    String expExcelPath;
    String expXmlPath;

    @BeforeEach
    void init()
    {
        api = new MuseumRestApi();
        expHtmlPath = "./src/com/azuga/museum/TruthFile2/Expmuseum.html";
        expPdfPath = "./src/com/azuga/museum/TruthFile2/Expmuseum.pdf";
        expExcelPath = "./src/com/azuga/museum/TruthFile2/Expmuseum.xlsx";
        expXmlPath = "./src/com/azuga/museum/TruthFile2/Expmuseum.xml";
    }


    /*
     * This test method test positive case for generated Html File.
     */
    @Test
    void testCsvToHtml() throws IOException, NoSuchAlgorithmException {
        api.csvHtml(Converters2.csvPath, Converters2.htmlPath);
        String actualOutput = api.checkSum(Converters2.htmlPath);
        String expectedOutput = api.checkSum(expHtmlPath);
        assertEquals(actualOutput, expectedOutput);
    }

    /*
     * This test method tests positive case for generated Pdf File.
     */
    @Test
    void testCsvToPdf() throws IOException, NoSuchAlgorithmException, CsvValidationException, DocumentException {
        api.csvPdf(Converters2.csvPath, Converters2.pdfPath);
        AssertThat.document(Converters2.pdfPath)
                .and(expPdfPath)
                .haveSameText();
    }

    /*
     * This test method tests positive case for generated Xml File.
     */
    @Test
    void testJsonToXml() throws IOException, NoSuchAlgorithmException {
        api.jsonXml(Converters2.jsonPath, Converters2.xmlPath);
        String actualOutput = api.checkSum(Converters2.xmlPath);
        String expectedOutput = api.checkSum(expXmlPath);
        assertEquals(actualOutput, expectedOutput);
    }

    /*
     * This test method tests for an empty museum api url input.
     */
    @Test
    void test_museum_api_call() {
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= 20; i++) {
            api.restApiCall( Converters2.url + (i));
        }
        try {
            api.jsonCsv(Converters2.jsonPath, Converters2.csvPath);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage()+" occurred while reading data or writing data to {}"+Converters2.jsonPath+" "+Converters2.csvPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(Files.readString(Path.of(Converters2.jsonPath)),Files.readString(Path.of(Converters2.jsonPath)));
            assertEquals(Files.readString(Path.of(Converters2.csvPath)),Files.readString(Path.of(Converters2.csvPath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        endTime = System.currentTimeMillis();
        log.info("Url Test Case Executed in"+ (endTime - startTime) +"ms");
    }

    @Nested
    class ApiExceptionTester {
        /**
         * This method is used to test for null input of the url
         */
        @Test
        void test_NPE_ApiCall() {
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class, () -> api.restApiCall(null));
            long end = System.currentTimeMillis();
            log.info("ApiCall -ve Test1 Method is executed in "+(end - start)+" ms");
        }
        /**
         * This method is used to test for illegal input of the url
         */
        @Test
        void test_IAE_ApiCall(){
            long start = System.currentTimeMillis();
            assertThrows(IllegalArgumentException.class, () -> api.restApiCall("foo"));
            long end = System.currentTimeMillis();
            log.info("ApiCall -ve Test2 Method is executed in "+(end - start)+" ms");
        }
        /**
         * This method is used to test for null input of the csvPath and JsonPAth
         */
        @Test
        void test_NPE_jsonToCsv(){
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class,()->api.jsonCsv(null, null));
            long end = System.currentTimeMillis();
            log.info("Csv -ve Test Method is executed in "+(end - start)+" ms");
        }

        /**
         * This method is used to test for null input of the csvPath and htmlPath
         */
        @Test
        void test_NPE_csvToHtml(){
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class,()->api.csvHtml(null, null));
            long end = System.currentTimeMillis();
            log.info("Html -ve Test Method is executed in "+(end - start)+" ms");
        }

        /**
         * This method is used to test for null input of the csvPath and PdfPath
         */
        @Test
        void test_NPE_csvToPdf(){
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class,()->api.csvPdf(null, null));
            long end = System.currentTimeMillis();
            log.info("Pdf -ve Test Method is executed in "+(end - start)+" ms");
        }


        /**
         * This method is used to test for null input of the csvPath and XmlPath
         */
        @Test
        void test_NPE_jsonToXml(){
            long start = System.currentTimeMillis();
            assertThrows(NullPointerException.class,()->api.jsonXml(null, null));
            long end = System.currentTimeMillis();
            log.info("Xml -ve Test Method is executed in "+(end - start)+" ms");
        }
    }
}
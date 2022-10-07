package com.azuga.museum;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author - PruthviKumarP.
 */

import com.opencsv.CSVWriter;
import au.com.bytecode.opencsv.CSVReader;
import com.github.opendevl.JFlat;
import com.github.underscore.U;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MuseumRestApi implements Converters2{
    public long startTime;
    public long endTime;

    public static Logger  log = LogManager.getLogger(MuseumRestApi.class);
    private static StringBuilder sb = new StringBuilder();

    public MuseumRestApi() {

    }

    /**
     *  Calling Museum RESET API and generating a CSV file.
     */
    @Override
    public void restApiCall(String url) {
        startTime = System.currentTimeMillis();
        log.info("Museum API Endpoint: " + url);
        //Sending requests and retrieve their responses from server
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        var client = HttpClient.newBuilder()
                .build();
        HttpResponse<String> response = null;
        try {
            //The request has been sent and the response has been received in synchronous manner.
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            log.error(e + " occurred while getting a response museum api url " + url);
        }
        if (response == null) log.error("Unsuccessful response from the server " + url);
        else {
            var ok = response.statusCode();
            if (ok != 200) log.fatal("Unsuccessfully api call " + url);
            if (ok == 404) log.fatal("Wrong Url " + this.url);
            if (ok == 500) log.fatal("Internal Server Error " + url);
            log.debug("Server Response is " + response.body());
            sb.append(response.body().replaceAll("\"\"", "null"));
        }
        endTime = System.currentTimeMillis();
        log.info(" Museum Api Response Received in " + (endTime - startTime) + " ms");
    }

    /**
     * This method json the csv file to csv format
     */
    @Override
    public void jsonCsv(String inputJsonPath, String outputCsvPath) throws FileNotFoundException{
        startTime = System.currentTimeMillis();
        if(sb!=null){
            try ( PrintWriter pw = new PrintWriter(inputJsonPath)){
                pw.append(sb.toString());
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(e.getMessage());
            }
        }else
            log.error("Response from Museum api is null");
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get(inputJsonPath)));
        } catch (IOException e) {
            log.error(e+" occurred while reading file from "+inputJsonPath);
        }
        if (json != null) {
            String s="["+json.replaceAll("}\\{","},{")+"]";
            JFlat flatMe = new JFlat(s);
            try {
                flatMe.json2Sheet().headerSeparator("_").write2csv(outputCsvPath);
            } catch (Exception e) {
                log.error(e+" occurred while writing file "+outputCsvPath);
            }
            try {
                CSVReader reader = new CSVReader(new FileReader(outputCsvPath));
                log.info("Reading file "+outputCsvPath);
                List<String[]> csvBody = reader.readAll();

                for (int m = 1; m < csvBody.size(); m++) {
                    String i = csvBody.get(m)[0].replaceAll(".0+$", "");
                    csvBody.get(m)[0] = i;
                }
                for (int m = 0; m < csvBody.size(); m++) {
                    int n = m + 1;
                    while (n < csvBody.size() && (csvBody.get(m)[0]).equals(csvBody.get(n)[0])) {
                        String[] i = csvBody.get(m);
                        csvBody.remove(i);
                    }
                }
                reader.close();
                log.trace("opening file on "+outputCsvPath);
                CSVWriter writer = new CSVWriter(new FileWriter(outputCsvPath));
                writer.writeAll(csvBody);
                writer.close();
                log.trace("closing file on "+outputCsvPath);
            } catch (IOException e) {
                log.error(e+" occurred while writing file "+ outputCsvPath);
            }
        }
        endTime = System.currentTimeMillis();
        log.info(" CSV file generated in " + (endTime - startTime) + " ms");
    }

    /**
     * This method converts the csv file to xlsx format
     */
    @Override
    public void csvExcel(String inputCsvPath, String outputExcelPath) {
        startTime = System.currentTimeMillis();
        try(FileWriter fw = new FileWriter(outputExcelPath)){
            log.info("Reading data from "+inputCsvPath);
            log.info("Writing the excel data to "+outputExcelPath);
            fw.write(Files.readString(Path.of(inputCsvPath)));
        }catch (Exception e) {
            log.error(e + " occurred while writing file to " + inputCsvPath);
        }
        endTime = System.currentTimeMillis();
        log.info(" Excel file generated in " + (endTime - startTime) + " ms");
    }

    /**
     * This method converts the csv file to html format
     */
    @Override
    public void csvHtml(String inputCsvPath, String outputHtmlPath) {
        startTime = System.currentTimeMillis();
        ArrayList<String> lines = new ArrayList<>();
        log.info("Reading data from " + inputCsvPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(inputCsvPath))) {
            //reading the file here
            String currentLine;
            String str;
            while ((currentLine = reader.readLine()) != null) {
                str = currentLine//.replaceAll("\"\"","null")
                        .replaceAll("\"", "")
                        .replaceAll("https://images.metmuseum.org/CRDImages", "<img src=https\\://images.metmuseum.org/CRDImages")
                        .replaceAll("jpg", "jpg style=\"width:100px;height:100px;\" >")
                        .replaceAll("https://www.", "<a href=url>")
                        .replaceAll("http://http://", "<a href=url>");
                lines.add(str);
            }
            log.debug("CSV data stored in " + lines + "list");
        } catch (IOException e) {
            log.error(e + "occurred while reading file from " + inputCsvPath);
        }
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, "<tr><td>" + lines.get(i) + "</td></tr>");
            lines.set(i, lines.get(i).replaceAll(",", "</td><td>"));
        }
        lines.set(0, "<table border>" + lines.get(0));
        lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "</table>");
        log.info("writing the html data " + outputHtmlPath);
        try (FileWriter writer = new FileWriter(outputHtmlPath)) {
            //writing String to html file
            for (String line : lines) {
                log.debug("data which is converted to html form " + line);
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            log.error(e + " occurred while writing file " + e.getMessage());
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        log.info(" Html file generate in " + (endTime - startTime) + " ms");
    }

    /**
     * This method converts the csv file to pdf format
     */
    @Override
    public void csvPdf(String inputCsvPath, String outputPdfPath) throws  IOException {
        startTime = System.currentTimeMillis();
        ArrayList<String> lines = new ArrayList<>();
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(inputCsvPath));
            //reading csv file from given path
        } catch (FileNotFoundException e) {
            log.error("Exception " + e.getMessage());
            throw new RuntimeException(e);
        }
        String[] nextLine;
        Document my_pdf_data = new Document();

        Rectangle rc = new Rectangle(8300f, 8000f);
        my_pdf_data.setPageSize(rc);

        try {
            PdfWriter.getInstance(my_pdf_data, new FileOutputStream(outputPdfPath));
            //writing pdf file to given path
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        my_pdf_data.open();
        var myTable = new PdfPTable(68);
        PdfPCell cells;
        String[] Lines;
        int j = 0;
        while ((Lines = reader.readNext()) != null) {
            var str = Lines[j].replaceAll("\"\"", "null")
                    .replaceAll("\"", "");
            lines.add(str);
            for (var i = 0; i < 68; i++) {
                cells = new PdfPCell(new Phrase(Lines[i]));
                myTable.addCell(cells);
            }
            j++;
        }
        try {
            my_pdf_data.add(myTable);
        } catch (DocumentException e) {
            log.error("Exception " + e.getMessage());
            throw new RuntimeException(e);
        }
        my_pdf_data.close();
        endTime = System.currentTimeMillis();
        log.info("Pdf file generated  in " + (endTime - startTime) + " ms");
    }

    /**
     * This method converts the json file to xml format
     */
    @Override
    public void jsonXml(String inputJsonPath, String outputXmlPath) throws NoSuchAlgorithmException, IOException {
        startTime = System.currentTimeMillis();
        String result;
        try {
            // reading byte data from json file
            ArrayList<String> list = new ArrayList<>();
            result = new String(Files.readAllBytes(Paths.get(inputJsonPath)));
            log.info("Reading data from " + inputJsonPath);
            String[] array = result.substring(1, result.length() - 1).split("\\}\\{");
            FileWriter file = new FileWriter(xmlPath);
            for (int i = 0; i < array.length; i++) {
                list.add(i, "{" + array[i] + "}");
                String lt = list.get(i);
                // This method converts json object to xml string
                String xml = U.jsonToXml(lt);
                log.debug("xml of Json" + " " + xml);
                log.info("json obtained from output.json is converted to xml");
                if (xml != null) {
                    file.write(xml);
                    log.info("xml is written to ToXML.xml file");
                    file.write("\n");
                } else log.error("error occurred while converting json to xml");
                file.flush();
            }
            // close FileWriter
            file.close();
            System.out.println(" XML data is successfully written " + xmlPath);
            log.info("ToXML.xml  file is created with required data");
        } catch (IOException e) {
            log.error(e + " occurred while writing xml " + e.getMessage());
        }
        endTime = System.currentTimeMillis();
        log.info("Xml file generated  in " + (endTime - startTime) + " ms");
    }

    /**
     * checking metadata for check generated files.
     */
    @Override
    public String checkSum(String filename) throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        FileInputStream fileInput = new FileInputStream(filename);
        byte[] dataBytes = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = fileInput.read(dataBytes)) != -1) {
        messageDigest.update(dataBytes, 0, bytesRead);
        }
        byte[] digestBytes = messageDigest.digest();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < digestBytes.length; i++) {
            sb.append(Integer.toString((digestBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        String s = sb.toString();
        fileInput.close();
        return s;
    }
}


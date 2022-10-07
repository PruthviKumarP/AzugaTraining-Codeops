/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author - PruthviKumarP.
 */

package com.azuga.museum;

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
import com.opencsv.CSVWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * This class converts one file to other file such as
 *  - csv to html
 *  - json to xml
 *  - csv to pdf
 *  - csv to xlsx
*/
public class MuseumApi implements Converters, Charts{
    private static final Logger log = LogManager.getLogger(MuseumApi.class);

    public MuseumApi(){

    }

    /**
     *  Calling Museum RESET API and generating a CSV file.
     */
    @Override
    public void api(){
        try {
            var sc = new Scanner(System.in);
            var pw = new PrintWriter(jsonPath);
            System.out.println("Enter the Record Range: ");
            var starting_range = sc.nextInt();
            var ending_range = sc.nextInt();
            log.info("Museum API Endpoint: " + url);
            log.warn("Waiting for Http Response ");
            log.info("Setting the URL connection for get api request");
            for (var i = starting_range; i <= ending_range; i++) {
                var uniformResourceLocators = url + i;
                //Sending requests and retrieve their responses from server
                var request = HttpRequest.newBuilder().GET().uri(URI.create(uniformResourceLocators)).build();
                var client = HttpClient.newBuilder().build();
                // The request has been sent and the response has been received in synchronous manner.
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                var ok = response.statusCode();

                //log.debug("Museum Object Data from range "+starting_range+" to "+ending_range+ " : "+response.body());
                //Determining whether a certain HTTP request was successful or not
                if (ok != 200) {
                    log.error("Unsuccessful HTTPS Request");
                }
                pw.write(response.body().replaceAll("\\[]", "null").replaceAll("\\\\s+", "null"));
            }
            pw.close();
            log.info("museum.json File is Created");
            var json = new String(Files.readAllBytes(Paths.get(jsonPath)));
            log.info("Reading the data from museum.json file");
            var jsons = json.replaceAll("}\\{", "},{");
            log.info("Conversion of JSON to String Format");
            var s = "[" + jsons + "]";
            var flatMe = new JFlat(s);
            flatMe.json2Sheet().headerSeparator("_").write2csv(csvPath);
        } catch (Exception e){
            log.error("Error: "+e.getMessage());
            e.printStackTrace();
        }
        try {
            CSVReader reader = new CSVReader(new FileReader(csvPath));
            List<String[]> csvBody = reader.readAll();
            log.info("Type Conversion of column Floating point number to integer number");
            for (int m = 1; m < csvBody.size(); m++) {
                var i = csvBody.get(m)[0].replaceAll(".0+$", "");
                var j = csvBody.get(m)[29].replaceAll(".0+$", "");
                var k = csvBody.get(m)[30].replaceAll(".0+$", "");
                csvBody.get(m)[0] = i;
                csvBody.get(m)[29] = j;
                csvBody.get(m)[30] = k;
            }
            for (var m = 0; m < csvBody.size(); m++) {
                int n = m + 1;
                while (n < csvBody.size() && (csvBody.get(m)[0]).equals(csvBody.get(n)[0])) {
                    String[] i = csvBody.get(m);
                    csvBody.remove(i);
                }
            }
            reader.close();
            //log.info("museum.csv file is created");
            CSVWriter writer = new CSVWriter(new FileWriter(csvPath));
            writer.writeAll(csvBody);
            writer.close();
        } catch (Exception e) {
            log.error("Error " + e.getMessage());
        }
    }
    /**
     * This method converts the csv file to xlsx format
     */
    @Override
    public void csvHtml(String inputCsvPath, String outputHtmlPath) {
        long start = System.currentTimeMillis();
        ArrayList<String> lines = new ArrayList<>();
        log.info("Reading data from "+inputCsvPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(inputCsvPath))) {//reading the file here
            String currentLine;
            String str;
            while ((currentLine = reader.readLine()) != null) {
                str = currentLine.replaceAll("\"\"","null")
                        .replaceAll("\"","")
                        .replaceAll("https://images.metmuseum.org/CRDImages","<img src=https\\://images.metmuseum.org/CRDImages")
                        .replaceAll("jpg","jpg style=\"width:100px;height:100px;\" >")
                        .replaceAll("https://www.", "<a href=url>")
                        .replaceAll("http://http://", "<a href=url>");
                lines.add(str);
            }
        } catch (IOException e) {
            log.error(e+"occurred while reading file from "+inputCsvPath);
        }
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, "<tr><td>" + lines.get(i) + "</td></tr>");
            lines.set(i, lines.get(i).replaceAll(",", "</td><td>"));
        }
        lines.set(0, "<table border>" + lines.get(0));
        lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "</table>");
        log.info("writing the html data "+outputHtmlPath);
        try (FileWriter writer = new FileWriter(outputHtmlPath)) {
            //writing String to html file
            for (String line : lines) {
                log.debug("data which is converted to html form "+line);
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            log.error(e+" occurred while writing file to "+outputHtmlPath);
        }
        long end = System.currentTimeMillis();
        log.info("html method is executed in "+(end-start)+" ms");
    }

    /**
     * This method converts the csv file to pdf file format
     */
    @Override
    public void csvPdf(String inputCsvPath, String outputPdfPath) throws IOException {
        long start = System.currentTimeMillis();
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
        var myTable = new PdfPTable(69);
        PdfPCell cells;
        String[] Lines;
        int j = 0;
        while ((Lines = reader.readNext()) != null) {
            var str = Lines[j].replaceAll("\"\"", "null")
                    .replaceAll("\"", "");
            lines.add(str);
            for (var i = 0; i < 69; i++) {
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
        long end = System.currentTimeMillis();
        log.info("Pdf method is executed in "+(end-start)+" ms");
    }
    /**
     * This method converts the json file to xml file format
     */
    @Override
    public void jsonXml(String inputJsonPath, String outputXmlPath) {
        long start = System.currentTimeMillis();
        try {
            ArrayList<String> list = new ArrayList<>();
            var result = new String(Files.readAllBytes(Paths.get(inputJsonPath)));
            log.info("reading data from "+inputJsonPath);
            //log.debug("Data from json file is "+result);
            String[] array = result.substring(1, result.length() - 1).split("\\}\\{");
            var file = new FileWriter(outputXmlPath);
            for (int i = 0; i < array.length; i++) {
                list.add("{" + array[i] + "}");
                //System.out.println(list.get(i));
                var xml = U.jsonToXml(list.get(i));
                file.write(xml);
                file.flush();
            }
            file.close();
        } catch (IOException e) {
            log.error("Error occurred during the conversion of json to xml: " + e.getMessage());
        }
        long end = System.currentTimeMillis();
        log.info("XML method is executed in "+(end-start)+ "ms");
    }
    /**
     * This method converts the csv file to xlsx file format
     */
    @Override
    public void csvExcel(String inputCsvPath, String outputExcelPath) {
        long start = System.currentTimeMillis();
        try(FileWriter fw = new FileWriter(outputExcelPath)){
            log.info("reading data from "+inputCsvPath);

            log.info("writing the pdf data to "+outputExcelPath);
            fw.write(Files.readString(Path.of(inputCsvPath)));
            System.out.println("file is saved as museum123.xlsx");
        }catch (Exception e) {
            log.error(e+" occurred while writing file to "+inputCsvPath);
        }
        long end = System.currentTimeMillis();
        log.info("Excel method is executed in "+(end-start)+ "ms");
    }

    @Override
    public void barGraph(String outputBarGraph) {
        long start = System.currentTimeMillis();
        try {
            //Sending requests and retrieve their responses from server
            var request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(urlStore))
                    .build();
            var client = HttpClient.newBuilder().build();

            // The request has been sent and the response has been received in synchronous manner.
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Determining whether a certain HTTP request was successful or not
            if (response.statusCode() == 200) {
                log.warn("Waiting for Http Response");

                var jsonArray = new JSONArray(response.body());

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                for (Object i : jsonArray) {
                    log.debug("Getting Request from url: " + i);
                    JSONObject j = new JSONObject(i.toString());

                    JSONObject current = j.getJSONObject("rating");

                    dataset.addValue(Double.valueOf(j.get("price").toString()), "Price", j.get("title").toString());
                    dataset.addValue(Double.valueOf(current.get("count").toString()), "Review", j.get("title").toString());
                }
                JFreeChart barChart = ChartFactory.createBarChart(
                        "Fake Online Store Products",
                        "Products",
                        " $ Price & Total Count of Review",
                        dataset,
                        PlotOrientation.HORIZONTAL,
                        true, true, false);
                ChartUtils.saveChartAsJPEG(new File(outputBarGraph), barChart, 1920, 1080);
            }
        }catch (IOException | InterruptedException e){
            log.error("Error "+e.getMessage());
        }
        long end = System.currentTimeMillis();
        log.info("Bar Graph method is executed in "+(end-start)+" ms");
    }

    @Override
    public void pieGraph(String outputPieGraph) {
        long start = System.currentTimeMillis();
        try {
            //Sending requests and retrieve their responses from server
            var request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(urlStore))
                    .build();
            var client = HttpClient.newBuilder().build();

            // The request has been sent and the response has been received in synchronous manner.
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Determining whether a certain HTTP request was successful or not
            var jsonArray = new JSONArray(response.body());

            var ok = response.statusCode();

            if (ok != 200) {
                log.fatal("Your Request is Unsuccessful");
            } else {
                //Pie-Chart
                DefaultPieDataset<String> pieDataset = new DefaultPieDataset<String>();


                for (Object i : jsonArray) {
                    JSONObject j = new JSONObject(i.toString());
                    pieDataset.setValue(j.get("category").toString(), Integer.valueOf(j.get("id").toString()));
                }

                var chart = ChartFactory.createPieChart("Percentage of Categories Distributed in an Online Store", pieDataset, true, true, false);
                log.info("Created Pie Chart for Categories Distributed");
                var plot = (PiePlot) chart.getPlot();
                plot.setSimpleLabels(true);
                var gen = new StandardPieSectionLabelGenerator(
                        "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
                plot.setLabelGenerator(gen);
                try {
                    ChartUtils.saveChartAsJPEG(new File(outputPieGraph), chart, 720, 480);
                }catch (IOException e){
                    log.error("Error: "+e.getMessage());
                }
            }
            } catch(IOException | InterruptedException e){
                log.error("Error " + e.getMessage());
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            log.info("Pie Graph method is executed in " + (end - start) + " ms");
    }
}

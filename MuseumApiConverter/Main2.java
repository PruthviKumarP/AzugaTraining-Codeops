package com.azuga.museum;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author - PruthviKumarP.
 */

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws Exception {

        Converters2 api = new MuseumRestApi();
        Logger log = LogManager.getLogger(Main2.class);
        Scanner sc = new Scanner(System.in);

        log.info("Enter the Record Range: ");
        int startRange = sc.nextInt();
        int endRange = sc.nextInt();

        for(int i = startRange; i <= endRange; i++) {
            api.restApiCall(Converters2.url + i);
        }
        api.jsonCsv(Converters2.jsonPath, Converters2.csvPath);
        api.csvExcel(Converters2.csvPath, Converters2.excelPath);
        api.csvHtml(Converters2.csvPath, Converters2.htmlPath);
        api.csvPdf(Converters2.csvPath, Converters2.pdfPath);
        api.jsonXml(Converters2.jsonPath, Converters2.xmlPath);
}


}

package com.azuga.museum;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author - PruthviKumarP.
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        MuseumApi mapi = new MuseumApi();

        mapi.api();
        mapi.csvExcel(Converters.csvPath, Converters.excelPath);
        mapi.csvHtml(Converters.csvPath, Converters.htmlPath);
        mapi.csvPdf(Converters.csvPath, Converters.pdfPath);
        mapi.jsonXml(Converters.jsonPath, Converters.xmlPath);
        mapi.pieGraph(Charts.pieImgPath);
        mapi.barGraph(Charts.barImgPath);
    }
}

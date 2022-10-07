/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  is strictly prohibited-
 *  @Author - PruthviKumarP.
 */

package com.azuga.museum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface MailZip {

    String folderPath = "./src/com/azuga/museum/Reports";
    String zipFolderPath = "./src/com/azuga/museum/Reports.zip";
    String unZipFolderPath = "./src/com/azuga/museum/Reports_Unzip";
    File zipFolder = new File("./src/com/azuga/museum/Reports.zip");

    String host = "smtp.gmail.com";
    String from = "pruthvikumar.p24m.tech@gmail.com";

    String[] to = {"pruthvikumar.p24@gmail.com","krupa@codeops.tech","sudharshan@codeops.tech","indukurimr@azuga.com","adarshs@azuga.com","aparajitam@azuga.com","ashoop@azuga.com",
            "dushyants@azuga.com","kartiks@azuga.com","lokanathk@azuga.com","pruthvikp@azuga.com","rajatt@azuga.com",
            "rishabh@azuga.com","satvikm@azuga.com","suryaps@azuga.com","vijayyv@azuga.com"};

    String password = "ruyqpntndmzfnytf";

    String subject = "System Generated Reports / 30 Sept 2022";

    String bodyMsg = "Hi Team, \n Kindly find the generated reports for museum & fake online store API.";

    void zip(String sourceFolderPath, String zipPath);
    void unzip(String zipFolderPath, String unZipPath);
    void eMail(String from, String[] to, String pass, String sub, String msg, File file);

}

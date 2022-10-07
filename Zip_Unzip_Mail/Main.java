package com.azuga.museum;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;



public class Main2 {
    public static void main(String[] args) throws IOException {
        FolderFile ff = new FolderFile();
        Logger log = LogManager.getLogger(FolderFile.class);
        ff.zip(MailZip.folderPath, MailZip.zipFolderPath);
        ff.unzip(MailZip.zipFolderPath, MailZip.unZipFolderPath);
        ff.eMail(MailZip.from, MailZip.to, MailZip.password, MailZip.subject, MailZip.bodyMsg, MailZip.zipFolder);
    }
}

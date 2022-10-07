package com.azuga.museum;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;



public class FolderFile implements MailZip {
    private static final Logger log = LogManager.getLogger(FolderFile.class);

    @Override
    public void zip(String sourceFolderPath, String zipPath) {
        var startTime = System.currentTimeMillis();
        try {
            var zos = new ZipOutputStream(new FileOutputStream(Path.of(zipPath).toFile()));
            Files.walkFileTree(Path.of(sourceFolderPath), new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    zos.putNextEntry(new ZipEntry(Path.of(sourceFolderPath).relativize(file).toString()));
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
            zos.close();
            log.info("Zip File Saved Location is " + zipPath);
        } catch (IOException e) {
            log.error(e + " occurred while generating zip folder " + zipPath);
        }
        var endTime = System.currentTimeMillis();
    }

    @Override
    public void unzip(String zipFolderPath, String unZipPath) {
        var startTime = System.currentTimeMillis();
        try {
            File f = new File(unZipPath);
            if (!f.exists()) {
                f.mkdir();
            }
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFolderPath));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String filePath = unZipPath + File.separator + zipEntry.getName();
                log.debug("Extract Files " + filePath);
                if (!zipEntry.isDirectory()) {
                    FileOutputStream fos = new FileOutputStream(filePath);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            log.error(e + " occurred while unzip folder " + unZipPath);
        }
        var endTime = System.currentTimeMillis();
    }

    @Override
    public void eMail(String from, String[] to, String pass, String sub, String msg, File file) {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        log.info("PROPERTIES " + properties);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });
        session.setDebug(true);
        for(var toAll: to) {
            MimeMessage m = new MimeMessage(session);
            try {
                m.setFrom(from);
                m.addRecipient(Message.RecipientType.TO, new InternetAddress(toAll));
                m.setSubject(subject);
                MimeMultipart mimeMultipart = new MimeMultipart();
                MimeBodyPart textMineBodyPart = new MimeBodyPart();
                MimeBodyPart fileMimeBodyPart = new MimeBodyPart();
                try {
                    textMineBodyPart.setText(msg);
                    fileMimeBodyPart.attachFile(file);
                    mimeMultipart.addBodyPart(textMineBodyPart);
                    mimeMultipart.addBodyPart(fileMimeBodyPart);
                    m.setContent(mimeMultipart);
                    Transport.send(m);
                } catch (IOException e) {
                    log.error(e+ " occurred while attempting to deliver the message");
                }
                log.info("Your Mail sent successfully to "+toAll);
            } catch (MessagingException e) {
                log.error("Messaging Exception {}");
            }
        }
    }
}



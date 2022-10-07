[![Json2Flat 1.0.3](https://img.shields.io/badge/json2flat-1.0.3-green.svg)](https://search.maven.org/artifact/com.github.opendevl/json2flat/1.0.3/jar) 

[![opencsv 1.7](https://img.shields.io/badge/opencsv-1.7-green.svg)](https://sourceforge.net/projects/opencsv/) 

[![gson 2.9.1](https://img.shields.io/badge/gson-2.9.1-green.svg)](https://search.maven.org/artifact/com.google.code.gson/gson/2.9.1/jar) 

[![json-path 2.7.0](https://img.shields.io/badge/json_path-2.7.0-green.svg)](https://search.maven.org/artifact/com.jayway.jsonpath/json-path/2.7.0/jar) 

[![json-flattener 2.7.0](https://img.shields.io/badge/json_flattener-0.14.0-green.svg)](https://search.maven.org/artifact/com.github.wnameless.json/json-flattener/0.14.0/jar) 

[![pdfunit](https://img.shields.io/badge/pdfunit-jar-green.svg)](http://www.pdfunit.com/en/download/) 

[![Junit jupiter](https://img.shields.io/badge/JUnit_jupiter_engine-5.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine) 

[![Junit jupiter](https://img.shields.io/badge/JUnit_jupiter_API-5.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api) 

[![Junit jupiter](https://img.shields.io/badge/JUnit_jupiter_params-5.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params) 

[![Junit jupiter](https://img.shields.io/badge/JUnit_platform_launcher-1.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher) 

[![Junit jupiter](https://img.shields.io/badge/JUnit_vintage_engine-5.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.vintage/junit-vintage-engine) 


# Rest API 
[Museum API](https://metmuseum.github.io/ ) considered to fetch data and do program using it. This API has a complex json data which contains nested objects and arrays. Due to its complex structure, I have choosen it to learn string manipulation and other concepts of java.

## MuseumAPI
This program use calls API to get json data and covert it into csv file.

#### External library
1. [Json2Flat](https://github.com/opendevl/Json2Flat) 
2. [OpenCSV](https://opencsv.sourceforge.net/) 
3. [gson](https://github.com/google/gson)
4. [slf4j](https://www.slf4j.org/download.html)

## csv to html
This program converts csv file to html file including display of image through links.

## csv to pdf
This program converts csv file to pdf file into proper page structure.
#### External library
- [itextpdf](https://github.com/itext/itextpdf)

## json to xml
This program mimics use of pipe '|' symbol with ls, cat, wc and head and tail command. 
#### External library
- [underscore](https://github.com/jashkenas/underscore)


# Testing
[JUnit](https://junit.org/junit5/) testing for converter and pipe.

JUnit 5 = junit jupiter + junit platform + junit vintage 

All these library combined with will form a JUnit environment and to run Junit testing without using maven or gradle.

## CheckSum
CheckSum is a minified representation of a binary stream of data.

## ConverterTest
Test cases for csv to pdf, html and xml methods using checksum and pdfunit library.
#### External library
- [pdfunit-java-2016.05.jar](http://www.pdfunit.com/en/download/)

## PipeTest
Test case for pipe method.


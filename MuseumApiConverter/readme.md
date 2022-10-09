
# Metropolitan Museum of Art API - File Converter

The Metropolitan Museum of Art API provides maximum commercial and noncommercial usage of certain datasets, including information on over 470,000 artworks in its collection.
The API response dataset contains data in the form of json. Here calling the museum API programmatically to perform file conversion operations to convert .csv, .html, .xlsx, .pdf, and.xml file formats.

<div align="center">
  <img src="https://th-thumbnailer.cdn-si-edu.com/QOA7bQ93-6Es95SjW5mA-_5Pb_8=/1000x750/filters:no_upscale():focal(1485x1557:1486x1558)/https://tf-cmsv2-smithsonianmag-media.s3.amazonaws.com/filer/da/54/da541590-d437-4325-b640-3892f884f395/makingthemet_signature_image_girls_011320-jpg_original_300dpi.jpg" width="600px" height="450px">
</div>


## Authors

- [@PruthviKumarP](https://github.com/PruthviKumarP/AzugaTraining-Codeops.git)


## Installation

This project requires JDK 11 or above version and It need some external jar files to run application.

### Prerequisites

The project requires the installation of jar files/libraries or the addition of dependencies. The following are the Jar files that must be added to the classPath for various features:

[![Json2Flat 1.0.3](https://img.shields.io/badge/json2flat-1.0.3-green.svg)](https://search.maven.org/artifact/com.github.opendevl/json2flat/1.0.3/jar) 

[![opencsv 1.7](https://img.shields.io/badge/opencsv-1.7-green.svg)](https://sourceforge.net/projects/opencsv/) 

[![gson 2.9.1](https://img.shields.io/badge/gson-2.9.1-green.svg)](https://search.maven.org/artifact/com.google.code.gson/gson/2.9.1/jar) 

[![json-path 2.7.0](https://img.shields.io/badge/json_path-2.7.0-green.svg)](https://search.maven.org/artifact/com.jayway.jsonpath/json-path/2.7.0/jar) 

[![json-flattener 2.7.0](https://img.shields.io/badge/json_flattener-0.14.0-green.svg)](https://search.maven.org/artifact/com.github.wnameless.json/json-flattener/0.14.0/jar) 
## Features

<div align="center">
  <img src="https://i.ytimg.com/vi/3_U5T2Qg5_o/maxresdefault.jpg" width="400px" height="250px">
</div>

This project's features are listed below.

- [json-csv](): The method convert server response json data file to csv file.
- [csv-html](): The method convert csv file to html file.
- [csv-pdf](): The method convert csv file to pdf file.
- [json-xml](): The method convert json file to xml file.
- [csv-xsxl](): The method convert csv file to xsxl file.
## API Reference



### Museum Api

#### Get all items

```http
  GET https://collectionapi.metmuseum.org/public/collection/v1/objects
```

#### Get item

```http
  GET https://collectionapi.metmuseum.org/public/collection/v1/objects/${id}
```


## Testing

 [JUnit]() testing for converter and pipe.

 JUnit testing for converter and pipe.

 JUnit 5 = junit jupiter + junit platform + junit vintage


***Some additional jar files were required for testing***.

[![Junit jupiter](https://img.shields.io/badge/JUnit_jupiter_engine-5.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine) 

[![Junit jupiter](https://img.shields.io/badge/JUnit_jupiter_API-5.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api) 

[![Junit jupiter](https://img.shields.io/badge/JUnit_jupiter_params-5.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params) 

[![Junit jupiter](https://img.shields.io/badge/JUnit_platform_launcher-1.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher) 

[![Junit jupiter](https://img.shields.io/badge/JUnit_vintage_engine-5.9.1-green.svg)](https://mvnrepository.com/artifact/org.junit.vintage/junit-vintage-engine) 

All these library combined with will form a JUnit environment and to run Junit testing without using maven or gradle.

```bash
 javac test_filename.java
 java test_filename
```

## Badges

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://github.com/PruthviKumarP/AzugaTraining-Codeops/blob/main/LICENSE)

## 🔗 Links

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/)
[![twitter](https://img.shields.io/badge/twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/)


## Feedback

If you have any feedback, please reach out to me at [pruthvikumar.p24m.tech@gmail.com](pruthvikumar.p24m.tech@gmail.com).


![Pruthvi GitHub stats](https://github-readme-stats.vercel.app/api?username=PruthviKumarP&show_icons=true&theme=radical)



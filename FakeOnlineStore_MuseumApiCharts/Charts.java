package com.azuga.museum;

public interface Charts {

    String urlStore = "https://fakestoreapi.com/products";
    String barImgPath = "./src/com/azuga/museum/Reports/bar.jpeg";
    String pieImgPath = "./src/com/azuga/museum/Reports/pie.jpeg";

    void barGraph(String outputBarGraph);
    void pieGraph(String outputPieGraph);
}

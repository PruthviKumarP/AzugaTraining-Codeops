package com.main.java;

public interface Commands {
    String cat(String pathFileName);
    String ls();
    String head(String value, int n);
    String tail(String value, int n);
    String uniq(String value);
    String wc(String value);
    String sort(String value);
    String pipes(String args);
}

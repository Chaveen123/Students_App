package com.example.group_project;

public class QuoteResponse {

    String text= "";
    String author= "";

    public String getText() {
        return text;
    }

    public String getAuthor() {

        if (author == null || author.isEmpty() || author.equals("null")) {
          author = "Unknown Author";
        }
        return author;
    }
}

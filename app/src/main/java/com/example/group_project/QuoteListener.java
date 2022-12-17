package com.example.group_project;

import java.util.List;

public interface QuoteListener {
    void Fetch(List<QuoteResponse> responses, String message);
    void Error(String message);
}

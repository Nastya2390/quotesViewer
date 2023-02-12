package com.example.quotesViewer.api.response;

import lombok.Data;

@Data
public class QuotesViewerResponse {
    private boolean result;
    private String errorMessage;

    public QuotesViewerResponse(boolean result) {
        this.result = result;
        errorMessage = "";
    }

    public QuotesViewerResponse(boolean result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

}

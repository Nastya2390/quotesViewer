package com.example.quotesViewer.services;

import com.example.quotesViewer.api.response.QuotesViewerResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<QuotesViewerResponse> createUser(String name, String email, String password);

}

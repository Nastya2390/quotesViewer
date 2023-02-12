package com.example.quotesViewer.services;

import com.example.quotesViewer.api.response.QuotesViewerResponse;
import com.example.quotesViewer.model.Quote;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface QuoteService {

    ResponseEntity<QuotesViewerResponse> addQuote(String content, Integer userId);

    ResponseEntity<?> viewQuote(Integer id);

    ResponseEntity<?> viewRandomQuote();

    ResponseEntity<List<Quote>> viewTopTenQuotes();

    ResponseEntity<List<Quote>> viewWorseTenQuotes();

    ResponseEntity<List<Quote>> viewAllQuotes();

    ResponseEntity<?> updateQuote(Integer id, Map<String, String> patchQuote);

    ResponseEntity<?> upVote(Integer id);

    ResponseEntity<?> downVote(Integer id);

    ResponseEntity<?> getQuoteVotesGraph(Integer id);

    ResponseEntity<?> deleteQuote(Integer id);

    ResponseEntity<?> deleteAllQuotes();

}

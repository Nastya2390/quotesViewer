package com.example.quotesViewer.controllers;

import com.example.quotesViewer.api.response.QuotesViewerResponse;
import com.example.quotesViewer.model.Quote;
import com.example.quotesViewer.services.QuoteService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping(value = "/quote/")
    public ResponseEntity<QuotesViewerResponse> addQuote(@RequestParam(value = "content", defaultValue = "") String content,
                                                         @RequestParam(value = "userId", defaultValue = "") Integer userId) {
        return quoteService.addQuote(content, userId);
    }

    @GetMapping("/quote/{id}")
    public ResponseEntity<?> viewQuote(@PathVariable Integer id) {
        return quoteService.viewQuote(id);
    }

    @GetMapping("/quote/random/")
    public ResponseEntity<?> viewRandomQuote() {
        return quoteService.viewRandomQuote();
    }

    @GetMapping("/quote/topten/")
    public ResponseEntity<List<Quote>> viewTopTenQuotes() {
        return quoteService.viewTopTenQuotes();
    }

    @GetMapping("/quote/worseten/")
    public ResponseEntity<List<Quote>> viewWorseTenQuotes() {
        return quoteService.viewWorseTenQuotes();
    }

    @GetMapping("/quote/")
    public ResponseEntity<List<Quote>> viewAllQuotes() {
        return quoteService.viewAllQuotes();
    }

    @PatchMapping(value = "/quote/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateQuote(@PathVariable Integer id, @RequestBody Map<String, String> patchQuote) {
        return quoteService.updateQuote(id, patchQuote);
    }

    @PatchMapping(value = "/quote/upvote/{id}")
    public ResponseEntity<?> upVote(@PathVariable Integer id) {
        return quoteService.upVote(id);
    }

    @PatchMapping(value = "/quote/downvote/{id}")
    public ResponseEntity<?> downVote(@PathVariable Integer id) {
        return quoteService.downVote(id);
    }

    @GetMapping("/quote/votesgraph/{id}")
    public ResponseEntity<?> getQuoteVotesGraph(@PathVariable Integer id) {
        return quoteService.getQuoteVotesGraph(id);
    }

    @DeleteMapping("/quote/{id}")
    public ResponseEntity<?> deleteQuote(@PathVariable Integer id) {
        return quoteService.deleteQuote(id);
    }

    @DeleteMapping("/quotes/")
    public ResponseEntity<?> deleteAllQuotes() {
        return quoteService.deleteAllQuotes();
    }

}

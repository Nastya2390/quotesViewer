package com.example.quotesViewer.services;

import com.example.quotesViewer.api.response.QuotesViewerResponse;
import com.example.quotesViewer.model.Quote;
import com.example.quotesViewer.model.User;
import com.example.quotesViewer.model.Vote;
import com.example.quotesViewer.repository.QuoteRepository;
import com.example.quotesViewer.repository.UserRepository;
import com.example.quotesViewer.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    private final VoteRepository voteRepository;

    @Override
    public ResponseEntity<QuotesViewerResponse> addQuote(String content, Integer userId) {
        Quote quote = new Quote();
        quote.setContent(content);
        LocalDateTime currentDate = LocalDateTime.now();
        quote.setCreationDate(currentDate);
        quote.setModificationDate(currentDate);
        quote.setCurrentScore(0);
        Optional<User> userOpt = userRepository.getUserById(userId);
        if (userOpt.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Cannot find user with id = " + userId), HttpStatus.NOT_FOUND);
        quote.setUser(userOpt.get());
        quoteRepository.save(quote);
        Vote vote = new Vote();
        vote.setQuote(quote);
        vote.setScore(0);
        vote.setPutScoreDate(currentDate);
        voteRepository.save(vote);
        return new ResponseEntity<>(new QuotesViewerResponse(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> viewQuote(Integer id) {
        Optional<Quote> quoteOpt = quoteRepository.findById(id);
        if (quoteOpt.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Cannot find quote with id = " + id), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(quoteOpt.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> viewRandomQuote() {
        List<Quote> quotes = getQuotesList();
        if(quotes.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "No quote found"), HttpStatus.NOT_FOUND);
        Random random = new Random();
        int randomIndex = random.nextInt(quotes.size());
        return new ResponseEntity<>(quotes.get(randomIndex), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Quote>> viewTopTenQuotes() {
        List<Quote> quotes = getQuotesList();
        List<Quote> topTenQuotes = quotes.stream()
                .sorted(Comparator.reverseOrder())
                .limit(10).collect(Collectors.toList());
        return new ResponseEntity<>(topTenQuotes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Quote>> viewWorseTenQuotes() {
        List<Quote> quotes = getQuotesList();
        List<Quote> worseTenQuotes = quotes.stream()
                .sorted(Comparator.naturalOrder())
                .limit(10).collect(Collectors.toList());
        return new ResponseEntity<>(worseTenQuotes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Quote>> viewAllQuotes() {
        List<Quote> quotes = getQuotesList();
        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateQuote(Integer id, Map<String, String> patchQuote) {
        Optional<Quote> quoteOpt = quoteRepository.findById(id);
        if (quoteOpt.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Cannot find quote with id = " + id), HttpStatus.NOT_FOUND);
        Quote quote = quoteOpt.get();
        if (patchQuote.containsKey("content"))
            quote.setContent(patchQuote.get("content"));
        if (patchQuote.containsKey("creationDate"))
            quote.setCreationDate(LocalDateTime.parse(patchQuote.get("creationDate")));
        if (patchQuote.containsKey("modificationDate"))
            quote.setModificationDate(LocalDateTime.parse(patchQuote.get("modificationDate")));
        return new ResponseEntity<>(quoteRepository.save(quote), HttpStatus.OK);
    }

    @Override
    public synchronized ResponseEntity<?> upVote(Integer id) {
        Optional<Quote> quoteOpt = quoteRepository.findById(id);
        if (quoteOpt.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Cannot find quote with id = " + id), HttpStatus.NOT_FOUND);
        Quote quote = quoteOpt.get();
        List<Vote> votes = quote.getVotes();
        if (votes.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Cannot find votes for quote with id = " + id), HttpStatus.NOT_FOUND);
        int previousScore = quote.getCurrentScore();
        Vote currentVote = new Vote();
        currentVote.setScore(previousScore + 1);
        currentVote.setQuote(quote);
        currentVote.setPutScoreDate(LocalDateTime.now());
        voteRepository.save(currentVote);
        quote.setCurrentScore(previousScore + 1);
        quoteRepository.save(quote);
        return new ResponseEntity<>(currentVote, HttpStatus.OK);
    }

    @Override
    public synchronized ResponseEntity<?> downVote(Integer id) {
        Optional<Quote> quoteOpt = quoteRepository.findById(id);
        if (quoteOpt.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Cannot find quote with id = " + id), HttpStatus.NOT_FOUND);
        Quote quote = quoteOpt.get();
        List<Vote> votes = quote.getVotes();
        if (votes.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Cannot find votes for quote with id = " + id), HttpStatus.NOT_FOUND);
        int previousScore = quote.getCurrentScore();
        if (previousScore == 0)
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Current vote is 0. Cannot perform down vote for quote with id = " + id), HttpStatus.BAD_REQUEST);
        Vote currentVote = new Vote();
        currentVote.setScore(previousScore - 1);
        currentVote.setQuote(quote);
        currentVote.setPutScoreDate(LocalDateTime.now());
        voteRepository.save(currentVote);
        quote.setCurrentScore(previousScore - 1);
        quoteRepository.save(quote);
        return new ResponseEntity<>(currentVote, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getQuoteVotesGraph(Integer id) {
        Optional<Quote> quoteOpt = quoteRepository.findById(id);
        if (quoteOpt.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Cannot find quote with id = " + id), HttpStatus.NOT_FOUND);
        Quote quote = quoteOpt.get();
        List<Vote> votes = quote.getVotes();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteQuote(Integer id) {
        Optional<Quote> quoteOpt = quoteRepository.findById(id);
        if (quoteOpt.isEmpty())
            return new ResponseEntity<>(new QuotesViewerResponse(false, "Cannot find quote with id = " + id), HttpStatus.NOT_FOUND);
        quoteRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteAllQuotes() {
        quoteRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<Quote> getQuotesList() {
        Iterable<Quote> quoteIterable = quoteRepository.findAll();
        List<Quote> quotes = new ArrayList<>();
        for (Quote quote : quoteIterable) {
            quotes.add(quote);
        }
        return quotes;
    }

}

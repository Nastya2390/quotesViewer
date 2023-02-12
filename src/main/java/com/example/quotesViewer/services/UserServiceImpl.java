package com.example.quotesViewer.services;

import com.example.quotesViewer.api.response.QuotesViewerResponse;
import com.example.quotesViewer.model.User;
import com.example.quotesViewer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<QuotesViewerResponse> createUser(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreationDate(LocalDateTime.now());
        userRepository.save(user);
        return new ResponseEntity<>(new QuotesViewerResponse(true), HttpStatus.OK);
    }

}

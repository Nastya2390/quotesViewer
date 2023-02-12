package com.example.quotesViewer.repository;

import com.example.quotesViewer.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
}

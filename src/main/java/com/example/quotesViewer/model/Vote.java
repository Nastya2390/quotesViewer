package com.example.quotesViewer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int score;

    private LocalDateTime putScoreDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return id == vote.id && score == vote.score && Objects.equals(putScoreDate, vote.putScoreDate) &&
                Objects.equals(quote.getId(), vote.quote.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, putScoreDate, quote.getId());
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", score=" + score +
                ", putScoreDate=" + putScoreDate +
                ", quoteId=" + quote.getId() +
                '}';
    }

}

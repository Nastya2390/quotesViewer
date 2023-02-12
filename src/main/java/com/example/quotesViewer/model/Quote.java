package com.example.quotesViewer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Quote implements Comparable<Quote>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    private LocalDateTime modificationDate;

    private int currentScore;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "quote")
    @JsonManagedReference
    public List<Vote> votes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return id == quote.id && Objects.equals(content, quote.content) && Objects.equals(creationDate, quote.creationDate)
                && Objects.equals(modificationDate, quote.modificationDate) && Objects.equals(user.getId(), quote.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, creationDate, modificationDate, user.getId());
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", userId=" + user.getId() +
                '}';
    }

    @Override
    public int compareTo(Quote o) {
        return this.getCurrentScore() - o.getCurrentScore();
    }

}

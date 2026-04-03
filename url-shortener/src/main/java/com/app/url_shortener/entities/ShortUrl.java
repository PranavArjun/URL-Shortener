package com.app.url_shortener.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "short_urls")
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "short_urls_id_gen")
    @SequenceGenerator(name = "short_urls_id_gen",sequenceName = "short_urls_id_seq", allocationSize = 1)
    private Long id;

    @Column(name ="short_key",nullable = false, length = 10, unique = true)
    private String shortKey;

    @Column(name = "original_url", nullable = false , columnDefinition = "TEXT")
    private String originalUrl;

    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate = false;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = true)
    private User createdBy;

    @Column(name = "click_count", nullable = false)
    private Long clickCount=0L;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist(){
        if(clickCount==null){
            clickCount = 0L;
        }
        if(createdAt==null){
            createdAt= Instant.now();
        }
        if(isPrivate==null){
            isPrivate=false;
        }
    }
}

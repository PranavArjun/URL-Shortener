package com.app.url_shortener.repository;

import com.app.url_shortener.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> {

    List<ShortUrl> findByIsPrivateFalseOrderByCreatedAtDesc();
}

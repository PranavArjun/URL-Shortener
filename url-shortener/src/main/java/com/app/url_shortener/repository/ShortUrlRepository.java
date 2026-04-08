package com.app.url_shortener.repository;

import com.app.url_shortener.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> {

    @Query("select su from ShortUrl su left join fetch su.createdBy where su.isPrivate = false order by su.createdAt desc")
    List<ShortUrl> getAllPublicShortUrls();

    boolean existsByShortKey(String shortKey);


    Optional<ShortUrl> findByShortKey(String shortUrlKey);
}

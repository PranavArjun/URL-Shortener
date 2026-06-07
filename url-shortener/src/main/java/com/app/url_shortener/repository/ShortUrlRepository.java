package com.app.url_shortener.repository;

import com.app.url_shortener.entities.ShortUrl;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> {

    @Query("select su from ShortUrl su left join fetch su.createdBy where su.isPrivate = false ")
    Page<ShortUrl> getAllPublicShortUrls(Pageable pageable);

    boolean existsByShortKey(String shortKey);


    Optional<ShortUrl> findByShortKey(String shortUrlKey);

    @Query("select su from ShortUrl su left join fetch su.createdBy u where u.email= :email")
    Page<ShortUrl> findByCreatedByEmail(String email, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE ShortUrl s SET s.clickCount = s.clickCount + :delta WHERE s.shortKey = :shortKey")
    void incrementClickCount(@Param("shortKey") String shortKey, @Param("delta") Long delta);
}

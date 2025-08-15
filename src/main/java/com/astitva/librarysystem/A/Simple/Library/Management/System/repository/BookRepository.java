package com.astitva.librarysystem.A.Simple.Library.Management.System.repository;

import com.astitva.librarysystem.A.Simple.Library.Management.System.entities.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT b FROM BookEntity b WHERE b.publishedDate >= :date")
    List<BookEntity> findByPublishedDateGreaterThanAndEquals(LocalDate date);
}
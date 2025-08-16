package com.astitva.librarysystem.A.Simple.Library.Management.System.repository;

import com.astitva.librarysystem.A.Simple.Library.Management.System.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    List<AuthorEntity> findByNameIgnoreCase(String name);

    AuthorEntity findByName(String authorName);
}
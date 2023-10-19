package com.ale.blog.repository;

import com.ale.blog.entity.DocumentLinked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentLinkedRepository extends JpaRepository<DocumentLinked, Long> {
}

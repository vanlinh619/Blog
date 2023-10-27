package com.ale.blog.repository;

import com.ale.blog.entity.DocumentLinked;
import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentLinkedRepository extends JpaRepository<DocumentLinked, Long> {
    List<DocumentLinked> findAllBySection(DocumentSection section, Sort sort);
}

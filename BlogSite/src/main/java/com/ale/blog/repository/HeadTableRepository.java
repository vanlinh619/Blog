package com.ale.blog.repository;

import com.ale.blog.entity.TableOfContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadTableRepository extends JpaRepository<TableOfContent, Long> {
}

package com.ale.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository<T, ID> extends JpaRepository<T, ID> {
}

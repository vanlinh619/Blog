package com.ale.blog.repository;

import com.ale.blog.entity.HeadTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeadTableRepository extends JpaRepository<HeadTable, Long> {
}

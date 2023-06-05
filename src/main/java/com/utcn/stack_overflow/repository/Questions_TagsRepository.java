package com.utcn.stack_overflow.repository;

import com.utcn.stack_overflow.entity.Questions_Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Questions_TagsRepository extends JpaRepository<Questions_Tags,Long> {
}

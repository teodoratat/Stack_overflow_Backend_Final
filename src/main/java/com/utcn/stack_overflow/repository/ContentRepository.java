package com.utcn.stack_overflow.repository;

import com.utcn.stack_overflow.entity.Content;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findAllByQuestionIsNull(Sort sortByCreationDate);

    List<Content> findAllByQuestion_Id(Long questionId);

    List<Content> findAllByOrderByCreationDateDesc();
}

package com.utcn.stack_overflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "question_tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Questions_Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(  name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Content content;



}


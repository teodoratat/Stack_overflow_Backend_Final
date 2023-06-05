package com.utcn.stack_overflow.service;

import com.utcn.stack_overflow.entity.Content;
import com.utcn.stack_overflow.entity.Questions_Tags;
import com.utcn.stack_overflow.entity.Tag;
import com.utcn.stack_overflow.repository.ContentRepository;
import com.utcn.stack_overflow.repository.Questions_TagsRepository;
import com.utcn.stack_overflow.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ContentService {

    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }
    @Autowired
    public TagRepository tagRepository;

    @Autowired
    public Questions_TagsRepository questions_tagsRepository;
    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }
    public List<Content> getAllQuestionsOrderedByCreationDate() {
        Sort sortByCreationDate = Sort.by(Sort.Direction.DESC, "creationDate");
        return contentRepository.findAllByQuestionIsNull(sortByCreationDate);
    }
    public List<Content> getAllQuestionsWithTags() {
        List<Content> questions = contentRepository.findAllByOrderByCreationDateDesc();

        for (Content question : questions) {
            List<String> tagNames = new ArrayList<>();
            for (Long tagId : question.getTag_ids()) {
                Optional<Tag> tagOpt = tagRepository.findById(tagId);
                tagOpt.ifPresent(tag -> tagNames.add(tag.getName()));
            }
            //question.setTagNames(tagNames);
        }

        return questions;
    }

    public List<Content> getAllAnswersByQuestionId(Long questionId) {
        return contentRepository.findAllByQuestion_Id(questionId);
    }
    public Optional<Content> getContentById(Long id) {
        return contentRepository.findById(id);
    }

    public Content createContent(Content content) {
        return contentRepository.save(content);
    }
//    public Content insertQuestion(Content content, String tagNames) {
//        content.setCreationDate(LocalDateTime.now());
//        content = contentRepository.save(content);
//
//        String[] tagNameArray = tagNames.split(",");
//        for (String tagName : tagNameArray) {
//            String trimmedTagName = tagName.trim();
//            Optional<Tag> tagOpt = tagRepository.findByName(trimmedTagName);
//            Tag tag;
//            if (tagOpt.isPresent()) {
//                tag = tagOpt.get();
//            } else {
//                tag = new Tag(trimmedTagName);
//                tag = tagRepository.save(tag);
//            }
//            content.getTag_ids().add(tag.getId());
//        }
//
//        contentRepository.save(content);
//        return content;
//    }
public Content insertQuestion(Content content, String tagNames) {

    content = contentRepository.save(content);

    String[] tagNameArray = tagNames.split(",");
    Set<Long> tagIds = new HashSet<>();
    for (String tagName : tagNameArray) {
        Optional<Tag> tagOpt = tagRepository.findByName(tagName.trim());
        if(tagOpt.isPresent() ){
            Tag tag = tagOpt.get();
            if(!tagIds.contains(tag.getId())) {
                tagIds.add(tag.getId());
                content.setTag_ids(tagIds);
                contentRepository.save(content);
                questions_tagsRepository.save(new Questions_Tags(0L, new Tag(tag.getId()), new Content(content.getId())));
            }
        }
        else {
            Tag tag = new Tag(tagName.trim());
            tagRepository.save(tag);
            tagIds.add(tag.getId());
            content.setTag_ids(tagIds);
            contentRepository.save(content);
            questions_tagsRepository.save(new Questions_Tags(0L, new Tag(tag.getId()), new Content(content.getId())));
        }
    }


    return content;
}
    public Content updateContent(Long id, Content updatedContent) {
        Optional<Content> existingContentOptional = contentRepository.findById(id);
        if (existingContentOptional.isPresent()) {
            Content existingContent = existingContentOptional.get();
            existingContent.setUser(updatedContent.getUser());
            existingContent.setQuestion(updatedContent.getQuestion());
            existingContent.setTitle(updatedContent.getTitle());
            existingContent.setText(updatedContent.getText());
            existingContent.setPicture(updatedContent.getPicture());
            existingContent.setCreationDate(updatedContent.getCreationDate());
            existingContent.setTag_ids(updatedContent.getTag_ids());
            return contentRepository.save(existingContent);
        }
        return null;
    }

    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }
}

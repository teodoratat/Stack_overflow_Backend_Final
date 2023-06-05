package com.utcn.stack_overflow.controller;

import com.utcn.stack_overflow.entity.Content;
import com.utcn.stack_overflow.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contents")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Content>> getAllContents() {
        List<Content> contents = contentService.getAllContents();
        return ResponseEntity.ok(contents);
    }
    @GetMapping("/getAllQuestions")
    public ResponseEntity<List<Content>> getAllQuestions() {
        List<Content> contents = contentService.getAllQuestionsOrderedByCreationDate();
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/getAllAnswers")
    public ResponseEntity<List<Content>> getAllAnswers(@RequestParam Long questionId) {
        List<Content> contents = contentService.getAllAnswersByQuestionId(questionId);
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Content> getContentById(@PathVariable Long id) {
        Optional<Content> content = contentService.getContentById(id);
        return content.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/insertContent")
    public ResponseEntity<Content> createContent(@RequestBody Content content) {
        Content createdContent = contentService.createContent(content);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContent);
    }
    @PostMapping("/insertQuestion")
    public ResponseEntity<Content> insertQuestion(@RequestBody Content content,@RequestParam String tagNames) {
        Content createdContent = contentService.insertQuestion(content,tagNames);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContent);
    }

    @PutMapping("/updateContent/{id}")
    public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content updatedContent) {
        Content updatedContentResult = contentService.updateContent(id, updatedContent);
        return updatedContentResult != null ? ResponseEntity.ok(updatedContentResult) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }
}

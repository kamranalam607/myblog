package com.myblog13.controller;

import com.myblog13.payload.CommentDto;
import com.myblog13.payload.PostDto;
import com.myblog13.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/comments/{postId}
    @PostMapping("{postId}")
    public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto commentDto, @PathVariable long postId){
        CommentDto dto = commentService.saveComment(commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable long id){
        commentService.deleteCommentById(id);
        return new ResponseEntity<>("Comment is deleted: "+id,HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CommentDto> updateCommentById(@RequestBody CommentDto commentDto, @PathVariable long id){
        CommentDto dto = commentService.updateCommentById(commentDto,id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}

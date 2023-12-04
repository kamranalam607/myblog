package com.myblog13.controller;


import com.myblog13.payload.PostDto;
import com.myblog13.payload.PostResponse;
import com.myblog13.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,
        BindingResult result

    ){
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{postId}")
    public ResponseEntity<String> deleteById(@PathVariable long postId){
        postService.deleteById(postId);
        return new ResponseEntity<>("Post is deleted: " +postId,HttpStatus.OK);
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long postId){
       PostDto dto =  postService.getPostById(postId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }



    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=description&sortDir=desc
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = "id",required = false)String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){

        PostResponse postResponse = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return postResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{postId}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable long postId, @RequestBody PostDto postDto){
        PostDto dto = postService.updatePostById(postDto,postId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}

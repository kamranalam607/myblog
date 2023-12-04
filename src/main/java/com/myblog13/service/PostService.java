package com.myblog13.service;

import com.myblog13.payload.PostDto;
import com.myblog13.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);


    void deleteById(long postId);

    PostDto getPostById(long postId);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto updatePostById(PostDto postDto, long postId);


}

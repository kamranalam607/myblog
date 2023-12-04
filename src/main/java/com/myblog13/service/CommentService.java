package com.myblog13.service;

import com.myblog13.payload.CommentDto;
import com.myblog13.payload.PostDto;

import java.util.List;

public interface CommentService {

    CommentDto saveComment(CommentDto dto, long postId);

    void deleteCommentById(long id);

    CommentDto updateCommentById(CommentDto commentDto, long id);


}

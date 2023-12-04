package com.myblog13.serviceimpl;

import com.myblog13.entity.Comment;
import com.myblog13.entity.Post;
import com.myblog13.exception.ResourceNotFound;
import com.myblog13.payload.CommentDto;
import com.myblog13.payload.PostDto;
import com.myblog13.repository.CommentRepository;
import com.myblog13.repository.PostRepository;
import com.myblog13.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    private PostRepository postRepo;
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepo,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepo=postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto saveComment(CommentDto dto, long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with Id: " + postId)
        );
       //Comment comment = new Comment();
       //comment.setId(dto.getId());
       //comment.setName(dto.getName());
       //comment.setEmail(dto.getEmail());
       //comment.setBody(dto.getBody());
       //comment.setPost(post);

        Comment comment = mapToComment(dto);
        comment.setPost(post);


        Comment savedComment = commentRepository.save(comment);

        CommentDto commentDto = new CommentDto();
        commentDto.setId(savedComment.getId());
        commentDto.setName(savedComment.getName());
        commentDto.setEmail(savedComment.getEmail());
        commentDto.setBody(savedComment.getBody());
        return commentDto;
    }

    @Override
    public void deleteCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Comment id not found: " + id)
        );
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updateCommentById(CommentDto commentDto, long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Comment id not found: " + id)
        );
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        Comment updatedComment = commentRepository.save(comment);

        CommentDto dto  = new CommentDto();
        dto.setId(updatedComment.getId());
        dto.setName(updatedComment.getName());
        dto.setEmail(updatedComment.getEmail());
        dto.setBody(updatedComment.getBody());
        return dto;
    }



   public Comment mapToComment(CommentDto dto){
       Comment comment = modelMapper.map(dto, Comment.class);
       return comment;
   }

}

package com.myblog13.serviceimpl;

import com.myblog13.entity.Post;
import com.myblog13.exception.ResourceNotFound;
import com.myblog13.payload.PostDto;
import com.myblog13.payload.PostResponse;
import com.myblog13.repository.PostRepository;
import com.myblog13.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }


    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);

        PostDto dto = new PostDto();
        dto.setId(updatedPost.getId());
        dto.setTitle(updatedPost.getTitle());
        dto.setDescription(updatedPost.getDescription());
        dto.setContent(updatedPost.getContent());
        return dto;
    }

    @Override
    public void deleteById(long postId) {
        postRepository.findById((long) postId).orElseThrow(
                () -> new ResourceNotFound("PostId is not found: " + postId)
        );
        postRepository.deleteById(postId);

    }

    @Override
    public PostDto getPostById(long postId) {
        Post post = postRepository.findById((long) postId).orElseThrow(
                () -> new ResourceNotFound("PostId is not found: " + postId)
        );
        return mapToDto(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
       Sort sort =  sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
       Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(all.getNumber());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setTotalElements((int)all.getTotalElements());
        postResponse.setPageSize(all.getSize());
        postResponse.setLast(all.isLast());
        return postResponse;
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post Id not Found: " + postId)
        );

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatedPost = postRepository.save(post);

        PostDto dto = new PostDto();
        dto.setId(updatedPost.getId());
        dto.setTitle(updatedPost.getTitle());
        dto.setContent(updatedPost.getContent());
        dto.setDescription(updatedPost.getDescription());
        return dto;
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }
}

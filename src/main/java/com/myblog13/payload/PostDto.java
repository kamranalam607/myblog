package com.myblog13.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDto {

    private long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 character")
    private String title;

    @NotEmpty
    private String content;
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 character")
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

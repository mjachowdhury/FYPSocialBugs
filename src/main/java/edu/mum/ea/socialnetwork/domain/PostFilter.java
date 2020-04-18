package edu.mum.ea.socialnetwork.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
public class PostFilter {
    private Long categoryId;
    private Date creationDateAfter;

    public PostFilter(long categoryId, Date creationDateAfter) {
        this.categoryId = categoryId;
        this.creationDateAfter = creationDateAfter;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreationDateAfter() {
        return creationDateAfter;
    }

    public void setCreationDateAfter(Date creationDateAfter) {
        this.creationDateAfter = creationDateAfter;
    }
}

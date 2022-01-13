package com.bjss.desk_booking.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CommentJpa extends JpaRepository<Comment,Long> {
    //interface to delete comment data
    int deleteById(int id);
}

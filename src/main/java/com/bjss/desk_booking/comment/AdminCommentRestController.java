package com.bjss.desk_booking.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/comment")
public class AdminCommentRestController {

    @Autowired
    private CommentJpa commentJpa;

    @GetMapping(value = "fetchAll")
    public List<Comment> fetchAll() {
        return commentJpa.findAll();
    }

    @GetMapping(value = "fetch")
    public Page<Comment> fetch(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        return commentJpa.findAll(pageable);
    }

    @DeleteMapping(value = "delete/{id}")
    public String deleted(@PathVariable int id) {
        int result = commentJpa.deleteById(id);
        if (result == 0) {
            return "delete failed";
        }
        return "delete successful";
    }
}

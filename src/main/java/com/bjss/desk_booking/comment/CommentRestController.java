package com.bjss.desk_booking.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentRestController {

    @Autowired
    private CommentJpa commentJpa;

    @GetMapping(value = "fetchAll")
    public List<Comment> fetchAll() {
        return commentJpa.findAll();
    }
}

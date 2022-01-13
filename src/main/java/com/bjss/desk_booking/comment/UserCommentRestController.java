package com.bjss.desk_booking.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/comment")
public class UserCommentRestController {
    @Autowired
    private CommentJpa commentJpa;

    /** User add comment data */
    @PostMapping(value = "/add")
    public String postComment(@RequestBody Comment comment) {
        comment = commentJpa.save(comment);
        System.out.println("comment = " + comment);
        if (comment.getId() != null) {
            return "Successful!";
        } else {
            return "Fail!";
        }

    }

}

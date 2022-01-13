package com.bjss.desk_booking.comment;

import javax.persistence.*;
import java.util.Date;
/** a data transfer object for transferring comment data*/
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "comment")
    private String comment;
    @Column(name = "createdTime")
    private Date createdTime;
    @Column(name = "name")
    private String name;

    public Comment() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Comment(Integer id, String name, String comment, Date createdTime) {
        this.id = id;
        this.name=name;
        this.comment = comment;
        this.createdTime = createdTime;
    }
}

package com.krowfeather.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private Integer id;
    private User user;
    private String title;
    private String content;
    private Integer likes;
}

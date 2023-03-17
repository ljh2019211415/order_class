package com.back.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class scoreTable {
    private int uid;
    private String username;

    private int tId;
    private String tName;
    private LocalDate date;
    private String comment;
    private ExamineTable e;
    private String score;

}

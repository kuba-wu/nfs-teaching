package com.kubawach.nfs.teaching.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ExerciseFilterDto {

    private String taskId;
    private Date start;
    private Date end;
}

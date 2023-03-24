package io.github.davidchild.bitter.samples.business.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VoInUser {
    private String name;
    private Date date;
    private Integer age;
    private Integer deptId;
    private List<Integer> deptIds;
}

package io.github.davidchild.bitter.samples.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class VoOutUser {

    public String id;

    private String username;

    private String email;

    private String phone;

    private String password;

    private String nickname;

    private String avatar;

    private String telephone;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String updateBy;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer userAge;
}

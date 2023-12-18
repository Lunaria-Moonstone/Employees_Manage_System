package org.luna.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("member")
public class EmployeeBean {
    @TableId(type= IdType.AUTO)
    private Long id;


    private String username;

    private String password;

    private String name;

    private int age;

    private int gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String phone;
}

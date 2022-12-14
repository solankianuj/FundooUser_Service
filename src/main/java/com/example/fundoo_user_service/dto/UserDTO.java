package com.example.fundoo_user_service.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserDTO {
    @Pattern(regexp = "^[A-Z]{1}[a-z A-Z \\s]{2,}$",message = "invalid name !")
    private String fullName;
    private String emailId;
    private String password;
    private Date dob;
    private String phoneNo;
}

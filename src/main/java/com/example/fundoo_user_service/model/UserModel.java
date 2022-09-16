package com.example.fundoo_user_service.model;

import com.example.fundoo_user_service.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "fundooUserData")
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    @GenericGenerator(name = "fundooUserData",strategy = "increment")
    @GeneratedValue(generator = "fundooUserData")
    private Long id;
    private String fullName;
    private String emailId;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isActive;
    private Boolean isDeleted;
    private Date dob;
    private String phoneNo;
    @Lob
    private  byte[]  profilePic;

    public UserModel(UserDTO userDTO) {
        this.fullName=userDTO.getFullName();
        this.emailId=userDTO.getEmailId();
        this.password=userDTO.getPassword();
        this.phoneNo=userDTO.getPhoneNo();
        this.dob=userDTO.getDob();
    }
}

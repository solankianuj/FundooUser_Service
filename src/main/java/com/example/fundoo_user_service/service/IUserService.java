package com.example.fundoo_user_service.service;


import com.example.fundoo_user_service.dto.UserDTO;
import com.example.fundoo_user_service.util.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface IUserService {

    Response addUser(UserDTO userDTO);
    Response getUser(String token);
    Response getUserList(String token);
    Response updateUser(String token,UserDTO userDTO);
    Response deleteUser(String token);
    Response restoreUser(String token);
    Response deletePermanentlyUser(String token);
    Response setProfile(String token, MultipartFile profilePic) throws IOException;
    Response resetPassword(String emailId,String newPwd);
    Response changePassword(String token,String newPwd);
    Boolean verifyUser(Long userId);
    Boolean activateUser(String token);
    Boolean emailVerification(String emailId);
    Response login(String emailId,String password);

}

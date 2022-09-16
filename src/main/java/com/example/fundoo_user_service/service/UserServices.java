package com.example.fundoo_user_service.service;

import com.example.fundoo_user_service.dto.UserDTO;
import com.example.fundoo_user_service.exception.UserNotFound;
import com.example.fundoo_user_service.model.UserModel;
import com.example.fundoo_user_service.repository.UserRepository;
import com.example.fundoo_user_service.service.mailService.MailServices;
import com.example.fundoo_user_service.util.Response;
import com.example.fundoo_user_service.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Purpose-implementation of user operation API'S.
 * @author anuj solanki
 * @date 14/09/2022
 * @version 1.0
 */
@Service
public class UserServices implements IUserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    Token tokenUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailServices mailServices;

    /**
     * Purpose-Logic implementation of API to add user
     * @param userDTO
     * @return
     */
    @Override
    public Response addUser(UserDTO userDTO) {
        UserModel userModel=new UserModel(userDTO);
        userModel.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userModel.setCreatedDate(LocalDateTime.now());
        userRepository.save(userModel);
        mailServices.send(userModel.getEmailId(), "User registration","User added successfully\n"+userModel);
        return new Response("User added successfully",200,userModel);
    }
    /**
     * Purpose-Implementing logic of API to fetch user.
     * @param token
     * @return
     */
    @Override
    public Response getUser(String token) {
        Long id= tokenUtil.decodeToken(token);
       Optional<UserModel> userModel=userRepository.findById(id);
       if (userModel.isPresent()){
           return new Response("Getting User Details",200,userModel.get());
       }
            throw new UserNotFound(400,"user not found !");
    }
    /**
     * Purpose-Implementing logic of API to fetch all user.
     * @param token
     * @return
     */
    @Override
    public Response getUserList(String token) {
        Long userId= tokenUtil.decodeToken(token);
        Optional<UserModel> userModel=userRepository.findById(userId);
        if (userModel.isPresent()){
            List<UserModel> userList=userRepository.findAll().stream().filter(x->x.getIsDeleted().equals(false) && x.getIsActive().equals(true)).collect(Collectors.toList());
            return new Response("Getting all active user details",200,userList);
        }
        throw new UserNotFound(400,"user not found !");
    }
    /**
     * Purpose-Implementing logic of API to update user details.
     * @param token
     * @param userDTO
     * @return
     */
    @Override
    public Response updateUser(String token, UserDTO userDTO) {
        Long userId= tokenUtil.decodeToken(token);
        Optional<UserModel> userModel=userRepository.findById(userId);
        if (userModel.isPresent()) {
            userModel.get().setFullName(userDTO.getFullName());
            userModel.get().setEmailId(userDTO.getEmailId());
            userModel.get().setPassword(userDTO.getPassword());
            userModel.get().setPhoneNo(userDTO.getPhoneNo());
            userModel.get().setDob(userDTO.getDob());
            userModel.get().setUpdatedDate(LocalDateTime.now());
            userRepository.save(userModel.get());
            return new Response("Updating Admin Details", 200, userModel.get());
        }
        throw new UserNotFound(400,"user not found !");
    }
    /**
     * Purpose-Implementing logic of API to move user to trash.
     * @param token
     * @return
     */
    @Override
    public Response deleteUser(String token) {
        Long userId= tokenUtil.decodeToken(token);
        Optional<UserModel> userModel=userRepository.findById(userId);
        if (userModel.isPresent()) {
            userModel.get().setIsDeleted(true);
            userRepository.save(userModel.get());
            return new Response("user move to trash successfully",200,userModel.get());
        }
        throw new UserNotFound(400,"user not found !");
    }

    /**
     * Purpose-Implementing logic of API to restore user from trash.
     * @param token
     * @return
     */
    @Override
    public Response restoreUser(String token) {
        Long userId= tokenUtil.decodeToken(token);
        Optional<UserModel> userModel=userRepository.findById(userId);
        if (userModel.isPresent()) {
            userModel.get().setIsDeleted(false);
            userRepository.save(userModel.get());
            return new Response("user restore successfully",200,userModel.get());
        }
        throw new UserNotFound(400,"user not found !");
    }

    /**
     * Purpose-Implementing logic of API to delete user from database.
     * @param token
     * @return
     */
    @Override
    public Response deletePermanentlyUser(String token) {
        Long userId= tokenUtil.decodeToken(token);
        Optional<UserModel> userModel=userRepository.findById(userId);
        if (userModel.isPresent()) {
            if (userModel.get().getIsDeleted()==true){
                userRepository.delete(userModel.get());
                return new Response("user deleted permanently",200,userModel.get());
            }
            return new Response("user not in trash",200,userModel.get());
        }
        throw new UserNotFound(400,"user not found !");
    }

    /**
     * Purpose-Implementing logic of API to add profile.
     * @param token
     * @param profilePic
     * @return
     * @throws IOException
     */
    @Override
    public Response setProfile(String token, MultipartFile profilePic) throws IOException {
        Long userId= tokenUtil.decodeToken(token);
        Optional<UserModel> userModel=userRepository.findById(userId);
        if (userModel.isPresent()) {
            userModel.get().setProfilePic(profilePic.getBytes());
            userRepository.save(userModel.get());
            return new Response("profile added successfully",200,userModel.get());
        }
        throw new UserNotFound(400,"user not found !");
    }

    /**
     * Purpose-Implementing logic of API to reset password.
     * @param emailId
     * @param newPwd
     * @return
     */
    @Override
    public Response resetPassword(String emailId, String newPwd) {
        Optional<UserModel> userModel=userRepository.findByEmailId(emailId);
        if (userModel.isPresent()){
            if (userModel.get().getEmailId().equals(emailId)){
                String token=tokenUtil.createToken(userModel.get().getId());
                String link=System.getenv("resetPwdLink");
                String URL="click here "+link+token+"/"+newPwd;
                String subject="Password Reset Link .";
                mailServices.send(emailId,subject,URL);
                return new Response("Generating Reset Password link ", 200, URL);
            }
        }
        throw new UserNotFound(400,"user not found !");
    }

    /**
     * Purpose-Implementing logic of API to change password.
     * @param token
     * @param newPwd
     * @return
     */
    @Override
    public Response changePassword(String token, String newPwd) {
        Long userId= tokenUtil.decodeToken(token);
        Optional<UserModel> userModel=userRepository.findById(userId);
        if (userModel.isPresent()){
            userModel.get().setPassword(passwordEncoder.encode(newPwd));
            userRepository.save(userModel.get());
            return new Response("password change successfully",200,userModel.get());
        }
        throw new UserNotFound(400,"user not found !");
    }

    /**
     * Purpose-Implementing logic of API to validate user in database.
     * @param userId
     * @return
     */
    @Override
    public Boolean verifyUser(Long userId) {
        Optional<UserModel> userModel=userRepository.findById(userId);
        if (userModel.isPresent()){
            return true;
        }
        throw new UserNotFound(200,"User Not Found !");
    }

    /**
     * Purpose-Implementing logic of API to activate user.
     * @param token
     * @return
     */
    @Override
    public Boolean activateUser(String token) {
        Long userId= tokenUtil.decodeToken(token);
        Optional<UserModel> userModel=userRepository.findById(userId);
        if (userModel.isPresent()){
            userModel.get().setIsActive(true);
            userRepository.save(userModel.get());
            return  true;
        }
        throw new UserNotFound(200,"User Not Found !");
    }

    /**
     * Purpose-Implementing logic of API to verify user email in database.
     * @param emailId
     * @return
     */
    @Override
    public Boolean emailVerification(String emailId) {
        Optional<UserModel> userModel=userRepository.findByEmailId(emailId);
        if (userModel.isPresent()){
            return true;
        }
        throw new UserNotFound(200,"User Not Found !");
    }

    /**
     * Purpose-Implementing logic of API to user login and generate token.
     * @param emailId
     * @param password
     * @return
     */
    @Override
    public Response login(String emailId, String password) {
        Optional<UserModel> userModel=userRepository.findByEmailId(emailId);
        if (userModel.isPresent()){
            if (passwordEncoder.matches(password,userModel.get().getPassword())){
                String tokenObj=tokenUtil.createToken(userModel.get().getId());
                return new Response("Login Successful",200,tokenObj);
            }
            throw new UserNotFound(200,"invalid credential !");
        }
        throw new UserNotFound(400,"user not found !");
    }
}

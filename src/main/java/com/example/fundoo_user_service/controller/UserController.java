package com.example.fundoo_user_service.controller;

import com.example.fundoo_user_service.dto.UserDTO;
import com.example.fundoo_user_service.service.IUserService;
import com.example.fundoo_user_service.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Purpose-Creating user operation API'S.
 * @author anuj solanki
 * @date 14/09/2022
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userServices;

    /**
     * Purpose-API to add user
     * @param userDTO
     * @return
     */
    @PostMapping("/addUser")
    public ResponseEntity<Response> addUser(@Valid @RequestBody UserDTO userDTO){
        Response response=userServices.addUser(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose-API to fetch user.
     * @param token
     * @return
     */
    @GetMapping("/getUser")
    public ResponseEntity<Response> getUser(@RequestHeader String token){
        Response response=userServices.getUser(token);
     return new  ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to fetch all user.
     * @param token
     * @return
     */
    @GetMapping("/getAllUser")
    public ResponseEntity<Response> getUserList(@RequestHeader String token){
        Response response=userServices.getUserList(token);
        return new  ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to user login and generate token.
     * @param emailId
     * @param password
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<Response> login(@RequestParam String emailId,@RequestParam String password){
        Response response=userServices.login(emailId, password);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to update user details.
     * @param token
     * @param userDTO
     * @return
     */
    @PutMapping("/updateUser")
    public ResponseEntity<Response> updateUser(@RequestHeader String token,@Valid @RequestBody UserDTO userDTO){
        Response response=userServices.updateUser(token, userDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to move user to trash.
     * @param token
     * @return
     */
    @DeleteMapping("/moveIntoTrash")
    public ResponseEntity<Response> moveIntoTrash(@RequestHeader String token){
        Response response=userServices.deleteUser(token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to restore user from trash.
     * @param token
     * @return
     */
    @DeleteMapping("/restoreUser")
    public ResponseEntity<Response> restoreUser(@RequestHeader String token){
        Response response=userServices.restoreUser(token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to delete user from database.
     * @param token
     * @return
     */
    @DeleteMapping("/deletePermanentlyUser")
    public ResponseEntity<Response> deletePermanentlyUser(@RequestHeader String token){
        Response response=userServices.deletePermanentlyUser(token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to add profile.
     * @param token
     * @param profilePic
     * @return
     * @throws IOException
     */
    @PutMapping("/addProfile")
    public ResponseEntity<Response> addProfile(@RequestHeader String token, @RequestBody MultipartFile profilePic) throws IOException {
        Response response=userServices.setProfile(token, profilePic);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to reset password.
     * @param emailId
     * @param newPwd
     * @return
     */
    @GetMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword( @RequestParam String emailId,String newPwd){
        Response response = userServices.resetPassword(emailId,newPwd);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to change password.
     * @param token
     * @param newPwd
     * @return
     */
    @PutMapping("/changePassword/{token}/{newPwd}")
    public ResponseEntity<Response> changePassword( @PathVariable String token,@PathVariable String newPwd){
        Response response = userServices.changePassword(token,newPwd);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to validate user in database.
     * @param userId
     * @return
     */
    @GetMapping("/validatingUser/{userId}")
    public ResponseEntity<Response> validatingUser(@PathVariable Long userId){
        Response response= userServices.verifyUser(userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Purpose-API to activate user.
     * @param token
     * @return
     */
    @GetMapping("/activateUser/{token}")
    Boolean activatingUser(@PathVariable String token){
        return userServices.activateUser(token);
    }

    /**
     * Purpose-API to verify user email in database.
     * @param emailId
     * @return
     */
    @GetMapping("/emailVerify/{emailId}")
    ResponseEntity<Response> emailVerification(@PathVariable String emailId){
        Response response= userServices.emailVerification(emailId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

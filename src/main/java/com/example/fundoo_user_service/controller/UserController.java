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

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userServices;

    @PostMapping("/addUser")
    public ResponseEntity<Response> addUser(@Valid @RequestBody UserDTO userDTO){
        Response response=userServices.addUser(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getUser")
    public ResponseEntity<Response> getUser(@RequestHeader String token){
        Response response=userServices.getUser(token);
     return new  ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<Response> getUserList(@RequestHeader String token){
        Response response=userServices.getUserList(token);
        return new  ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/login")
    public ResponseEntity<Response> login(@RequestParam String emailId,@RequestParam String password){
        Response response=userServices.login(emailId, password);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/updateUser")
    public ResponseEntity<Response> updateUser(@RequestHeader String token,@Valid @RequestBody UserDTO userDTO){
        Response response=userServices.updateUser(token, userDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Response> deleteUser(@RequestHeader String token){
        Response response=userServices.deleteUser(token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/restoreUser")
    public ResponseEntity<Response> restoreUser(@RequestHeader String token){
        Response response=userServices.restoreUser(token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/deletePermanentlyUser")
    public ResponseEntity<Response> deletePermanentlyUser(@RequestHeader String token){
        Response response=userServices.deletePermanentlyUser(token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/addProfile")
    public ResponseEntity<Response> addProfile(@RequestHeader String token, @RequestParam(value = "File") MultipartFile profilePic){
        Response response=userServices.setProfile(token, profilePic);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword( @RequestParam String emailId,String newPwd){
        Response response = userServices.resetPassword(emailId,newPwd);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/changePassword/{token}/{newPwd}")
    public ResponseEntity<Response> changePassword( @PathVariable String token,@PathVariable String newPwd){
        Response response = userServices.changePassword(token,newPwd);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/validatingUser/{token}")
    Boolean validatingUser(@PathVariable String token){
        return userServices.verifyUser(token);
    }

}

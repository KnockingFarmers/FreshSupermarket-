package org.gl.controller;


import org.gl.entity.User;
import org.gl.service.UserService;
import org.gl.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public String registration(String email,
                                String userName,
                                String verificationCode){
        return userService.registration(email,userName,verificationCode);
    }

    @PostMapping("/getCode")
    public Integer registration(String email){
        return userService.sendVerificationCode(email);
    }

    @PostMapping("/login")
    public User login(String email,String verificationCode){
        return userService.login(email, verificationCode);
    }

    @PostMapping("/getUserInfo")
    public User getUserInfo(String token){
        return userService.getUserInformation(token);
    }

    @PostMapping("/recharge")
    public Integer recharge(String token, BigDecimal rechargeMoney){
        return userService.recharge(token,rechargeMoney);
    }

    @PostMapping("/modifyMailbox")
    public Map<String, Object> modifyMailbox(String token, String email){
        return userService.modifyMailbox(token,email);
    }

    @PutMapping("/modifyUser")
    public User modifyUser(String token, User user){
        return userService.modifyUser(token,user);
    }

    @PostMapping("/modifyUserAvatar")
    public String modifyUserAvatar(String token,@RequestParam("file") MultipartFile file) throws IOException {
        return userService.modifyUserAvatar(token,file);
    }

}


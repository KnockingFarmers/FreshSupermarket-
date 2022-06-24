package org.gl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gl.entity.User;
import org.gl.mapper.UserMapper;
import org.gl.service.UserService;
import org.gl.util.JwtUtil;
import org.gl.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final String LOCATION = "http://182.92.160.192:620/userAvatar";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Integer sendVerificationCode(String email) {
        SimpleMailMessage sm = new SimpleMailMessage();
        //设置邮件主题
        sm.setSubject("欢迎来到生鲜超市");
        //邮件内容
        String str = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder sb = new StringBuilder();
        //生成随机四位验证码
        while (sb.length() < 4) {
            int index = (new Random().nextInt(str.length()));
            //处理重复字符：每个新的随机字符在 sb 中使用indexOF查找。-1为没找到
            Character ch = str.charAt(index);
            if (sb.indexOf(ch.toString()) < 0) {
                sb.append(ch);
            }
        }
        sm.setText("验证码: " + sb + ", 十分钟内有效!");
        //发给谁
        sm.setTo(email);
        //发送者
        sm.setFrom("3237764237@qq.com");
        javaMailSender.send(sm);
        redisUtil.set(email, sb, 600L);
        return 1;
    }

    @Override
    public String registration(String email,
                               String userName,
                               String verificationCode) {
        //拿到验证码
        String code = (String) redisUtil.get(email);
        if (code != null && code.equalsIgnoreCase(verificationCode)) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("user_email", email);
            User dbUser = userMapper.selectOne(wrapper);
            if (dbUser != null) {
                return "该用户已存在";
            }
            User user = new User();
            user.setUserEmail(email);
            user.setUserName(userName);
            user.setGmtCreate(new Date());
            user.setUserAvatar("/static/default.webp");
            int result = userMapper.insert(user);
            return result == 1 ? "注册成功" : "注册失败";
        }

        return "验证码错误";
    }

    @Override
    public User login(String email, String verificationCode) {
        String code = (String) redisUtil.get(email);
        User user = null;
        if (code.equalsIgnoreCase(verificationCode)) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("user_email", email);
            user = userMapper.selectOne(wrapper);
            user.setToken(jwtUtil.createToken(user));
        }

        return user;
    }


    @Override
    public User getUserInformation(String token) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        return userMapper.selectById(((Long) map.get("userId")));
    }

    @Override
    public Integer recharge(String token, BigDecimal rechargeMoney) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        User user = userMapper.selectById(((Long) map.get("userId")));
        user.setUserBalance(user.getUserBalance().add(rechargeMoney));
        return userMapper.updateById(user);
    }

    @Override
    public Map<String, Object> modifyMailbox(String token, String email) {
        Map<String, Object> resultMap = new HashMap<>(1);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_email", email);

        User selectOne = userMapper.selectOne(queryWrapper);
        if (selectOne != null) {
            resultMap.put("error", "邮箱已存在");
        } else {
            Map<String, Object> map = jwtUtil.analyzeToken(token);
            User user = userMapper.selectById((Long) map.get("userId"));
            UpdateWrapper updateWrapper = new UpdateWrapper();
            user.setUserEmail(email);
            int i = userMapper.updateById(user);
            resultMap.put("success", i);
        }

        return resultMap;
    }

    @Override
    public String modifyUserAvatar(String token, MultipartFile userAvatar) throws IOException {
        String originalFilename = userAvatar.getOriginalFilename();
       //临时路径
        String tempPath = Thread.currentThread().getContextClassLoader().getResource("static").getPath();

        //新文件名
        String newFileName = UUID.randomUUID().toString();
        //后缀
        String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
        //路径
        String path = tempPath + "/userAvatar/" + newFileName+suffix;
        //存入文件
        userAvatar.transferTo(new File(path));
        //删除原来的头像
        File oldUserAvatar=new File(path+originalFilename);
        oldUserAvatar.deleteOnExit();

        Map<String, Object> map = jwtUtil.analyzeToken(token);
        User user = userMapper.selectById((Long) map.get("userId"));
        String avatarPath = LOCATION + "/userAvatar/" + newFileName+suffix;
        user.setUserAvatar(avatarPath);
        userMapper.updateById(user);
        return avatarPath;
    }

    @Override
    public User modifyUser(String token, User user) {

        Map<String, Object> map = jwtUtil.analyzeToken(token);

        User dbUser = userMapper.selectById((Long) map.get("userId"));
        dbUser.setUserName(user.getUserName())
                .setUserEmail(user.getUserEmail())
                .setGender(user.getGender());
        userMapper.updateById(dbUser);
        return userMapper.selectById((Long) map.get("userId"));
    }


}

package org.gl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gl.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-11
 */
public interface UserService extends IService<User> {

    /**
     * 给用户发送验证码并返回给前端
     * @param email 邮箱
     * @return 是否发送成功
     */
    Integer sendVerificationCode(String email);

    /**
     * 使用邮箱注册
     * @param email 邮箱
     * @param userName 用户名
     * @param verificationCode 验证码
     * @return 验证码
     */
    String registration(String email, String userName, String verificationCode);

    /**
     * 登录
     * @param email 邮箱
     * @param verificationCode 验证码
     * @return 用户对象
     */
    User login(String email,String verificationCode);


    /**
     * 获取用户信息
     * @param token token
     * @return 用户对象
     */
    User getUserInformation(String token);

    /**
     * 充值
     * @param token token
     * @param rechargeMoney 充值金额
     * @return 是否充值成功
     */
    Integer recharge(String token, BigDecimal rechargeMoney);

    /**
     *用户修改邮箱
     * @param token token
     * @param email 邮箱
     * @return 返回结果集
     */
    Map<String,Object> modifyMailbox(String token, String email);

    /**
     * 用户修改头像
     * @param token token
     * @param userAvatar 头像文件
     * @return URL路径
     * @throws IOException 文件存储时捕获异常
     */
    String modifyUserAvatar(String token, MultipartFile userAvatar) throws IOException;

    /**
     * 修改用户信息，头像、ID、注册时间不可被修改
     * @param token token
     * @param user 用户对象
     * @return 用户对象
     */
    User modifyUser(String token,User user);

}

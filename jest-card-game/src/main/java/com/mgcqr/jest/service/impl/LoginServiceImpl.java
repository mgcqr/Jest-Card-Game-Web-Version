package com.mgcqr.jest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mgcqr.jest.dto.LoginDto;
import com.mgcqr.jest.dto.LoginResDto;
import com.mgcqr.jest.dto.ResponseWrapper;
import com.mgcqr.jest.entity.UserEntity;
import com.mgcqr.jest.mapper.UserMapper;
import com.mgcqr.jest.model.RuntimeUserInfo;
import com.mgcqr.jest.repository.RedisCacheRepository;
import com.mgcqr.jest.service.LoginService;
import com.mgcqr.jest.util.AesEncryptUtil;
import com.mgcqr.jest.util.Base64Util;
import com.mgcqr.jest.util.KeyUtil;
import com.mgcqr.jest.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.security.PrivateKey;

/**
 * 用实时的RSA密钥(Base64)对获取用户输入的密码
 * 数据库存储AES加密后的用户密码(Base64)
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCacheRepository redis;

    private static String publicKey;
    private static PrivateKey privateKey;

    @PostConstruct
    private void init(){
        refreshKeyPair();
    }

    private void refreshKeyPair(){
        KeyPair keyPair = RSAUtil.getKeyPair();
        publicKey = RSAUtil.getPublicKey(keyPair);
        privateKey = keyPair.getPrivate();
    }

    @Override
    public String getPublicKey(){return publicKey;}

    @Override
    public ResponseWrapper<LoginResDto> checkUserLogin(LoginDto loginDto){

        String guestName = loginDto.getUserName();

        String pswSave = passWordTransform(loginDto.getPassWord());

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUserName, guestName);
        UserEntity userEntity = userMapper.selectOne(wrapper);
        if(userEntity == null){
            //用户不存在
            return new ResponseWrapper<>("No such user.", null);
        }
        if (! pswSave.equals(userEntity.getPassWord())){
            //密码不正确
            return new ResponseWrapper<>("Wrong pass word.", null);
        }

        String token = KeyUtil.getKey();
        writeUserCache(token, userEntity);
        LoginResDto dto = new LoginResDto();
        dto.setId(userEntity.getId());
        dto.setToken(token);
        return new ResponseWrapper<>(dto);
    }

    @Override
    public ResponseWrapper<LoginResDto> signUp(LoginDto loginDto){
        String guestName = loginDto.getUserName();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(KeyUtil.getKey());
        userEntity.setUserName(guestName);
        userEntity.setPassWord(passWordTransform(loginDto.getPassWord()));
        try {
            userMapper.insert(userEntity);
        }catch (Exception exception){
            //log.error(exception.getMessage(), exception);
            //用户已存在
            return new ResponseWrapper<>("User name exists.", null);
        }

        String token = KeyUtil.getKey();
        writeUserCache(token, userEntity);
        LoginResDto dto = new LoginResDto();
        dto.setId(userEntity.getId());
        dto.setToken(token);
        return new ResponseWrapper<>(dto);
    }


    private void writeUserCache(String token, UserEntity userEntity){
        RuntimeUserInfo runtimeUserInfo = new RuntimeUserInfo();
        BeanUtils.copyProperties(userEntity, runtimeUserInfo);
        redis.setWithDefaultExpireTime(token, runtimeUserInfo);
    }

    /**
     * 将前端传来的RSA加密的密码用私钥解密之后得到明文
     * 再用用AES加密
     * 加上前缀生成数据库保存的密码
     * @param inputPsw
     * @return
     */
    private String passWordTransform(String inputPsw){
        try {
            //RSA解密得到明文
            byte[] psw = RSAUtil.privateDecrypt(Base64Util.String2Byte(inputPsw), privateKey);
            //AES加密得到数据库保存的密码
            //加上前缀表示编码方式
            return  "Base64_" + AesEncryptUtil.encrypt(psw);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new RuntimeException("密码转换出错 刷新网页获取新的公钥");
        }
    }

}

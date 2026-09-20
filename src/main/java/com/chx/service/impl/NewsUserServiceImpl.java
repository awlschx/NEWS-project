package com.chx.service.impl;

import com.chx.entity.NewsUser;
import com.chx.mapper.NewsUserMapper;
import com.chx.service.NewsUserService;
import com.chx.utils.MD5Util;
import org.springframework.stereotype.Service;

@Service
public class NewsUserServiceImpl implements NewsUserService {

    private final NewsUserMapper userMapper;

    public NewsUserServiceImpl(NewsUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public NewsUser login(String username, String userPwd) {
        NewsUser user = userMapper.findByUsername(username);
        if (user != null && user.getUserPwd().equals(MD5Util.md5(userPwd))) {
            return user;
        }
        return null;
    }

    @Override
    public boolean register(NewsUser user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return false;
        }
        user.setUserPwd(MD5Util.md5(user.getUserPwd()));
        return userMapper.insert(user) > 0;
    }
}

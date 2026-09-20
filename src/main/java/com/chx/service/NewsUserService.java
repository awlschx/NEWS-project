package com.chx.service;

import com.chx.entity.NewsUser;

public interface NewsUserService {

    NewsUser login(String username, String userPwd);

    boolean register(NewsUser user);
}

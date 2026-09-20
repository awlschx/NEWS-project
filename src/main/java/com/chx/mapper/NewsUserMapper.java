package com.chx.mapper;

import com.chx.entity.NewsUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NewsUserMapper {

    @Select("SELECT uid, username, user_pwd AS userPwd, nick_name AS nickName FROM news_user WHERE username = #{username}")
    NewsUser findByUsername(@Param("username") String username);

    @Select("SELECT uid, username, user_pwd AS userPwd, nick_name AS nickName FROM news_user WHERE uid = #{uid}")
    NewsUser findById(@Param("uid") Integer uid);

    @Insert("INSERT INTO news_user(username, user_pwd, nick_name) VALUES(#{username}, #{userPwd}, #{nickName})")
    int insert(NewsUser user);
}

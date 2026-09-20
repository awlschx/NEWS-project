package com.chx.mapper;

import com.chx.entity.NewsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsTypeMapper {

    @Select("SELECT tid, type_name AS typeName FROM news_type ORDER BY tid")
    List<NewsType> findAll();

    @Select("SELECT tid, type_name AS typeName FROM news_type WHERE tid = #{tid}")
    NewsType findById(@Param("tid") Integer tid);
}

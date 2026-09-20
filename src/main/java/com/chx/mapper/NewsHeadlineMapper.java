package com.chx.mapper;

import com.chx.entity.NewsHeadline;
import com.chx.pojo.vo.HeadlineDetailVO;
import com.chx.pojo.vo.HeadlineVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NewsHeadlineMapper {

    @Select({
            "SELECT h.hid, h.title, h.type, t.type_name AS typeName, h.publisher, u.nick_name AS publisherName, h.page_views AS pageViews, h.create_time AS createTime",
            "FROM news_headline h",
            "LEFT JOIN news_type t ON h.type = t.tid",
            "LEFT JOIN news_user u ON h.publisher = u.uid",
            "WHERE (#{type} IS NULL OR h.type = #{type}) AND h.is_deleted = 0",
            "ORDER BY h.create_time DESC LIMIT #{pageSize} OFFSET #{offset}"
    })
    List<HeadlineVO> findPageByType(@Param("type") Integer type, @Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(*) FROM news_headline WHERE (#{type} IS NULL OR type = #{type}) AND is_deleted = 0")
    int countByType(@Param("type") Integer type);

    @Select({
            "SELECT h.hid, h.title, h.type, t.type_name AS typeName, h.publisher, u.nick_name AS publisherName, h.page_views AS pageViews, h.create_time AS createTime",
            "FROM news_headline h",
            "LEFT JOIN news_type t ON h.type = t.tid",
            "LEFT JOIN news_user u ON h.publisher = u.uid",
            "WHERE h.is_deleted = 0 ORDER BY h.page_views DESC LIMIT 10"
    })
    List<HeadlineVO> findHotList();

    @Select({
            "SELECT h.hid, h.title, h.article, h.type, t.type_name AS typeName, h.publisher, u.nick_name AS publisherName, h.page_views AS pageViews, h.create_time AS createTime, h.update_time AS updateTime",
            "FROM news_headline h",
            "LEFT JOIN news_type t ON h.type = t.tid",
            "LEFT JOIN news_user u ON h.publisher = u.uid",
            "WHERE h.hid = #{hid} AND h.is_deleted = 0"
    })
    HeadlineDetailVO findDetailById(@Param("hid") Integer hid);

    @Select("SELECT hid, title, article, type, publisher, page_views AS pageViews, create_time AS createTime, update_time AS updateTime, is_deleted AS isDeleted FROM news_headline WHERE hid = #{hid} AND is_deleted = 0")
    NewsHeadline findById(@Param("hid") Integer hid);

    @Insert({
            "INSERT INTO news_headline(title, article, type, publisher, page_views, create_time, update_time, is_deleted)",
            "VALUES(#{title}, #{article}, #{type}, #{publisher}, 0, NOW(), NOW(), 0)"
    })
    int insert(NewsHeadline headline);

    @Update({
            "UPDATE news_headline",
            "SET title = #{title}, article = #{article}, type = #{type}, update_time = NOW()",
            "WHERE hid = #{hid} AND is_deleted = 0"
    })
    int update(NewsHeadline headline);

    @Delete("UPDATE news_headline SET is_deleted = 1 WHERE hid = #{hid}")
    int deleteById(@Param("hid") Integer hid);

    @Update("UPDATE news_headline SET page_views = page_views + 1 WHERE hid = #{hid}")
    int increaseViews(@Param("hid") Integer hid);
}

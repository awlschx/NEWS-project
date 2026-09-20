package com.chx.pojo.vo;

import java.time.LocalDateTime;

/**
 * 头条详情：在列表基础上增加正文、更新时间
 */
public class HeadlineDetailVO {
    private Integer hid;
    private String title;
    private String article;
    private Integer type;
    private String typeName;
    private Integer publisher;
    private String publisherName;
    private Integer pageViews;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public HeadlineDetailVO() {
    }

    public HeadlineDetailVO(Integer hid, String title, String article, Integer type, String typeName,
                           Integer publisher, String publisherName, Integer pageViews, LocalDateTime createTime,
                           LocalDateTime updateTime) {
        this.hid = hid;
        this.title = title;
        this.article = article;
        this.type = type;
        this.typeName = typeName;
        this.publisher = publisher;
        this.publisherName = publisherName;
        this.pageViews = pageViews;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Integer getPageViews() {
        return pageViews;
    }

    public void setPageViews(Integer pageViews) {
        this.pageViews = pageViews;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}

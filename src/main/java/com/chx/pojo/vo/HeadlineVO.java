package com.chx.pojo.vo;

import java.time.LocalDateTime;

/**
 * 头条列表展示：关联出类型名和发布者昵称
 */
public class HeadlineVO {
    private Integer hid;
    private String title;
    private Integer type;
    private String typeName;
    private Integer publisher;
    private String publisherName;
    private Integer pageViews;
    private LocalDateTime createTime;

    public HeadlineVO() {
    }

    public HeadlineVO(Integer hid, String title, Integer type, String typeName, Integer publisher,
                      String publisherName, Integer pageViews, LocalDateTime createTime) {
        this.hid = hid;
        this.title = title;
        this.type = type;
        this.typeName = typeName;
        this.publisher = publisher;
        this.publisherName = publisherName;
        this.pageViews = pageViews;
        this.createTime = createTime;
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
}

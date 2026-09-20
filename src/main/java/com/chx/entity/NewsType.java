package com.chx.entity;

public class NewsType {
    private Integer tid;
    private String typeName;

    public NewsType() {
    }

    public NewsType(Integer tid, String typeName) {
        this.tid = tid;
        this.typeName = typeName;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

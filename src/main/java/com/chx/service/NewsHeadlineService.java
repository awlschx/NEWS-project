package com.chx.service;

import com.chx.entity.NewsHeadline;
import com.chx.pojo.vo.HeadlineDetailVO;
import com.chx.pojo.vo.HeadlineVO;

import java.util.List;
import java.util.Map;

public interface NewsHeadlineService {

    Map<String, Object> pageByType(Integer type, int page, int pageSize);

    List<HeadlineVO> hotList();

    HeadlineDetailVO detail(Integer hid);

    boolean add(NewsHeadline headline);

    boolean update(NewsHeadline headline);

    boolean delete(Integer hid);
}

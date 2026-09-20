package com.chx.service.impl;

import com.chx.entity.NewsHeadline;
import com.chx.mapper.NewsHeadlineMapper;
import com.chx.pojo.vo.HeadlineDetailVO;
import com.chx.pojo.vo.HeadlineVO;
import com.chx.service.NewsHeadlineService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsHeadlineServiceImpl implements NewsHeadlineService {

    private final NewsHeadlineMapper headlineMapper;

    public NewsHeadlineServiceImpl(NewsHeadlineMapper headlineMapper) {
        this.headlineMapper = headlineMapper;
    }

    @Override
    public Map<String, Object> pageByType(Integer type, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<HeadlineVO> list = headlineMapper.findPageByType(type, offset, pageSize);
        int total = headlineMapper.countByType(type);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    @Override
    public List<HeadlineVO> hotList() {
        return headlineMapper.findHotList();
    }

    @Override
    public HeadlineDetailVO detail(Integer hid) {
        headlineMapper.increaseViews(hid);
        return headlineMapper.findDetailById(hid);
    }

    @Override
    public boolean add(NewsHeadline headline) {
        return headlineMapper.insert(headline) > 0;
    }

    @Override
    public boolean update(NewsHeadline headline) {
        return headlineMapper.update(headline) > 0;
    }

    @Override
    public boolean delete(Integer hid) {
        return headlineMapper.deleteById(hid) > 0;
    }
}

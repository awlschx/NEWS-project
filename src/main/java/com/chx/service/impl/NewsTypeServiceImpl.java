package com.chx.service.impl;

import com.chx.entity.NewsType;
import com.chx.mapper.NewsTypeMapper;
import com.chx.service.NewsTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsTypeServiceImpl implements NewsTypeService {

    private final NewsTypeMapper typeMapper;

    public NewsTypeServiceImpl(NewsTypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }

    @Override
    public List<NewsType> listAll() {
        return typeMapper.findAll();
    }
}

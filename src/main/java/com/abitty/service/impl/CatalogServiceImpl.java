package com.abitty.service.impl;

import com.abitty.dao.TblCatalogMapper;
import com.abitty.entity.TblCatalog;
import com.abitty.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yak on 17/6/12.
 */
@Component
public class CatalogServiceImpl implements CatalogService {

    private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);

    @Autowired
    private TblCatalogMapper tblCatalogMapper;

    public List<TblCatalog> getAllCatalog() {
        return tblCatalogMapper.selectAll();
    }

    public TblCatalog getByPrimaryKey(Integer id) {
        return tblCatalogMapper.selectByPrimaryKey(id);
    }

    public int update(TblCatalog tblCatalog) {
        return tblCatalogMapper.updateByPrimaryKeySelective(tblCatalog);
    }

    public int add(TblCatalog tblCatalog) {
        return tblCatalogMapper.insertSelective(tblCatalog);
    }

    public TblCatalog getByCatalogNo(String catalogNo) {
        return tblCatalogMapper.selectByCatalogNo(catalogNo);
    }
}

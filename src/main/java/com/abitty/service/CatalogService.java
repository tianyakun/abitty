package com.abitty.service;

import com.abitty.entity.TblCatalog;

import java.util.List;

/**
 * Created by yak on 17/6/12.
 */
public interface CatalogService {

    public List<TblCatalog> getAllCatalog();

    public TblCatalog getByPrimaryKey(Integer id);

    public int update(TblCatalog tblCatalog);

    public int add(TblCatalog tblCatalog);

    TblCatalog getByCatalogNo(String catalogNo);
}

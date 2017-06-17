package com.abitty.dao;

import com.abitty.entity.TblCatalog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/12.
 */
public class TblCatalogMapperTest extends BaseDaoTest {

    @Autowired
    private TblCatalogMapper tblCatalogMapper;

    @Test
    public void testDeleteByPrimaryKey() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testInsertSelective() throws Exception {
        TblCatalog tblCatalog = new TblCatalog();
        tblCatalog.setCatalogNo("catalogNo" + System.currentTimeMillis());
        tblCatalog.setName("name" + System.currentTimeMillis());
        tblCatalog.setCreateTime(new Date());
        tblCatalogMapper.insertSelective(tblCatalog);

        TblCatalog tblCatalog2 = new TblCatalog();
        tblCatalog2.setCatalogNo("catalogNo" + System.currentTimeMillis());
        tblCatalog2.setName("name" + System.currentTimeMillis());
        tblCatalog2.setCreateTime(new Date());
        tblCatalog2.setIsDelete(1);
        tblCatalogMapper.insertSelective(tblCatalog2);
    }

    @Test
    public void testSelectByPrimaryKey() throws Exception {
        System.out.println(tblCatalogMapper.selectByPrimaryKey(1));
    }

    @Test
    public void testUpdateByPrimaryKeySelective() throws Exception {

    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {

    }

    @Test
    public void testSelectAll() throws Exception {
        System.out.println(tblCatalogMapper.selectAll());
    }
}
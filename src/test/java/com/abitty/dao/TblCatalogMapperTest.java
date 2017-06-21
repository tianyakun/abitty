package com.abitty.dao;

import com.abitty.BaseTest;
import com.abitty.entity.TblCatalog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by kkk on 17/6/12.
 */
public class TblCatalogMapperTest extends BaseTest {

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
        tblCatalog.setCatalogNo("CN" + System.currentTimeMillis());
        tblCatalog.setName("酸奶");
        tblCatalog.setCreateTime(new Date());
        tblCatalogMapper.insertSelective(tblCatalog);

        TblCatalog tblCatalog2 = new TblCatalog();
        tblCatalog2.setCatalogNo("CN" + System.currentTimeMillis());
        tblCatalog2.setName("抽纸");
        tblCatalog2.setCreateTime(new Date());
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
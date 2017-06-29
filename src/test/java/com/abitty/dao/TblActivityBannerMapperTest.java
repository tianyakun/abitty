package com.abitty.dao;

import com.abitty.BaseTest;
import com.abitty.entity.TblActivityBanner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/29.
 */
public class TblActivityBannerMapperTest extends BaseTest{

    @Autowired
    private TblActivityBannerMapper tblActivityBannerMapper;

    @Test
    public void testDeleteByPrimaryKey() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testInsertSelective() throws Exception {
        TblActivityBanner tblActivityBanner = new TblActivityBanner();
        tblActivityBanner.setActivityNo("test" + System.currentTimeMillis());
        tblActivityBanner.setDescription("test" + System.currentTimeMillis());
        tblActivityBanner.setIcon("test" + System.currentTimeMillis());
        tblActivityBanner.setRedirectUrl("test" + System.currentTimeMillis());
        tblActivityBanner.setCreateTime(new Date());
        tblActivityBannerMapper.insertSelective(tblActivityBanner);

        Thread.sleep(1000);

        TblActivityBanner tblActivityBanner1 = new TblActivityBanner();
        tblActivityBanner1.setActivityNo("test" + System.currentTimeMillis());
        tblActivityBanner1.setDescription("test" + System.currentTimeMillis());
        tblActivityBanner1.setIcon("test" + System.currentTimeMillis());
        tblActivityBanner1.setRedirectUrl("test" + System.currentTimeMillis());
        tblActivityBanner1.setCreateTime(new Date());
        tblActivityBannerMapper.insertSelective(tblActivityBanner1);

        Thread.sleep(1000);

        TblActivityBanner tblActivityBanner2 = new TblActivityBanner();
        tblActivityBanner2.setActivityNo("test" + System.currentTimeMillis());
        tblActivityBanner2.setDescription("test" + System.currentTimeMillis());
        tblActivityBanner2.setIcon("test" + System.currentTimeMillis());
        tblActivityBanner2.setRedirectUrl("test" + System.currentTimeMillis());
        tblActivityBanner2.setCreateTime(new Date());
        tblActivityBanner2.setIsDelete(1);
        tblActivityBannerMapper.insertSelective(tblActivityBanner2);
    }

    @Test
    public void testSelectByPrimaryKey() throws Exception {

    }

    @Test
    public void testUpdateByPrimaryKeySelective() throws Exception {

    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {

    }

    @Test
    public void testSelectAllEffective() throws Exception {
        List<TblActivityBanner> list = tblActivityBannerMapper.selectAllEffective();

        System.out.println(list);
        System.out.println(list.size());
    }
}
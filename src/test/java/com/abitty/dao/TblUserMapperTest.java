package com.abitty.dao;

import com.abitty.entity.TblUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by kkk on 17/5/28.
 */
public class TblUserMapperTest extends BaseDaoTest{

    @Autowired
    private TblUserMapper tblUserMapper;

    @Test
    public void testDeleteByPrimaryKey() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        TblUser tblUser = new TblUser();
        tblUser.setUid("test" + System.currentTimeMillis());
        tblUser.setPhone("test" + System.currentTimeMillis());
        tblUser.setCreateTime(new Date());
        tblUserMapper.insertSelective(tblUser);
    }

    @Test
    public void testInsertSelective() throws Exception {

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

}
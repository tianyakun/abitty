package com.abitty.dao;

import com.abitty.entity.TblAddress;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/17.
 */
public class TblAddressMapperTest extends BaseDaoTest {

    @Autowired
    private TblAddressMapper tblAddressMapper;

    @Test
    public void testDeleteByPrimaryKey() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testInsertSelective() throws Exception {
        TblAddress tblAddress = new TblAddress();
        tblAddress.setProvince("test");
        tblAddress.setRecName("test");
        tblAddressMapper.insertSelective(tblAddress);
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
    public void testSelectAllByUid() throws Exception {

    }
}
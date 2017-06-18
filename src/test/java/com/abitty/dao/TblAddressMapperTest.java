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
        tblAddress.setUid("18812345678");
        tblAddress.setProvince("北京");
        tblAddress.setCity("北京市");
        tblAddress.setArea("昌平区");
        tblAddress.setPcaDetail("北京北京市昌平区");
        tblAddress.setAddressDetail("yyyyyyy");
        tblAddress.setRecName("麦克斯韦");
        tblAddress.setPhone("18812345678");
        int id = tblAddressMapper.insertSelective(tblAddress);
        System.out.println(id);
        System.out.println(tblAddress);
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
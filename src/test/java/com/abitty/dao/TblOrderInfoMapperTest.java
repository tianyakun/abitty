package com.abitty.dao;

import com.abitty.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/28.
 */
public class TblOrderInfoMapperTest extends BaseTest{

    @Autowired
    private TblOrderInfoMapper tblOrderInfoMapper;

    @Test
    public void testSelectSuccessOrderByUid() throws Exception {
        System.out.println(tblOrderInfoMapper.selectSuccessOrderByUid("18610915087"));
    }
}
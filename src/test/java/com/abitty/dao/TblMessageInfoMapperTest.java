package com.abitty.dao;

import com.abitty.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/26.
 */
public class TblMessageInfoMapperTest extends BaseTest{

    @Autowired
    private TblMessageInfoMapper tblMessageInfoMapper;

    @Test
    public void testSelectByMessageAddress() throws Exception {
        System.out.println(tblMessageInfoMapper.selectByMessageAddress("18610915087"));
    }
}
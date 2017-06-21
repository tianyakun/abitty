package com.abitty.dao;

import com.abitty.BaseTest;
import com.abitty.entity.TblProduct;
import com.abitty.constant.AbittyConstants;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by kkk on 17/6/17.
 */
public class TblProductMapperTest extends BaseTest {

    @Autowired
    private TblProductMapper tblProductMapper;

    @Test
    public void testDeleteByPrimaryKey() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testInsertSelective() throws Exception {
        TblProduct tblProduct = new TblProduct();
        tblProduct.setProductNo("PN" + System.currentTimeMillis());
        tblProduct.setCatalogNo("CN1497713023528");
        tblProduct.setName("酸奶1");
        tblProduct.setDescription("酸奶1酸奶1");
        tblProduct.setPrice(new BigDecimal("99"));
        tblProduct.setNowPrice(new BigDecimal("88"));
        tblProduct.setStatus(AbittyConstants.ProductState.ON_SALE);
        tblProduct.setIcon("");
        tblProduct.setDetail("酸奶1酸奶1酸奶1酸奶1");
        tblProduct.setImages("");
        tblProduct.setCreateTime(new Date());
        tblProductMapper.insertSelective(tblProduct);

        TblProduct tblProduct2 = new TblProduct();
        tblProduct2.setProductNo("PN" + System.currentTimeMillis());
        tblProduct2.setCatalogNo("CN1497713023528");
        tblProduct2.setName("酸奶2");
        tblProduct2.setDescription("酸奶2酸奶2");
        tblProduct2.setPrice(new BigDecimal("90"));
        tblProduct2.setNowPrice(new BigDecimal("85"));
        tblProduct2.setStatus(AbittyConstants.ProductState.ON_SALE);
        tblProduct2.setIcon("");
        tblProduct2.setDetail("酸奶2酸奶2酸奶2酸奶2");
        tblProduct2.setImages("");
        tblProduct2.setCreateTime(new Date());
        tblProductMapper.insertSelective(tblProduct2);
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
    public void testSelectAllPublish() throws Exception {

    }

    @Test
    public void testSelectAllPublishByCatalogNo() throws Exception {
        tblProductMapper.selectAllPublishByCatalogNo("CN1497713023528");
    }

    @Test
    public void testSelectByProductNo() throws Exception {

    }
}
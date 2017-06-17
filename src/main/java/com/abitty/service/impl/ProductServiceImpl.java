package com.abitty.service.impl;

import com.abitty.dao.TblProductMapper;
import com.abitty.entity.TblProduct;
import com.abitty.service.ProductService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yak on 17/6/14.
 */
@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private TblProductMapper tblProductMapper;

    public List<TblProduct> getAllPublish(String catalogNo) {
        if (Strings.isNullOrEmpty(catalogNo)) {
            return tblProductMapper.selectAllPublish();
        } else {
            return tblProductMapper.selectAllPublishByCatalogNo(catalogNo);
        }
    }

    public TblProduct getByProductNo(String productNo) {
        return tblProductMapper.selectByProductNo(productNo);
    }
}

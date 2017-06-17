package com.abitty.service;

import com.abitty.entity.TblProduct;

import java.util.List;

/**
 * Created by yak on 17/6/14.
 */
public interface ProductService {
    List<TblProduct> getAllPublish(String catalogNo);

    TblProduct getByProductNo(String productNo);
}

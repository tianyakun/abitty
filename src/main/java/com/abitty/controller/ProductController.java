package com.abitty.controller;

import com.abitty.entity.TblCatalog;
import com.abitty.entity.TblProduct;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.ProductService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by yak on 17/6/12.
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> getProductList(HttpServletRequest request) {
        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            String catalogNo = request.getParameter("catalogNo");

            List<TblProduct> tblProductList = productService.getAllPublish(catalogNo);

            Map<String, Object> data = Maps.newHashMap();
            data.put("list", tblProductList);

            resultMap.put("data", data);
            resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());

            return resultMap;
        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }

    @RequestMapping(value = "/item")
    @ResponseBody
    public Map<String, Object> getCatalogDetail(HttpServletRequest request) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {

            String productNo = request.getParameter("productNo");

            if (Strings.isNullOrEmpty(productNo)) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {
                TblProduct tblProduct = productService.getByProductNo(productNo);

                Map<String, Object> data = Maps.newHashMap();
                data.put("item", tblProduct);

                resultMap.put("data", data);
                resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());

                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }
}

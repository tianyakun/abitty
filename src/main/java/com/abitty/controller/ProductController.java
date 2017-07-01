package com.abitty.controller;

import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblProduct;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.ProductService;
import com.abitty.vo.ProductVo;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yak on 17/6/12.
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final static Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/list/{catalogNo}")
    @ResponseBody
    public ResponseDto getProductList(@PathVariable("catalogNo") final String catalogNo) {
        logger.info("获取商品列表请求 catalogNo={}", catalogNo);

        ResponseDto responseDto = new ResponseDto();

        try {
            if (Strings.isNullOrEmpty(catalogNo)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
                responseDto.setChineseMsg(ExceptionEnum.PARAM_INVALID.getChineseMessage());
            } else {
                List<TblProduct> tblProductList = productService.getAllPublish(catalogNo);

                List<ProductVo> productVoList = buildProductVoList(tblProductList);

                responseDto.addAttribute("list", productVoList);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
                responseDto.setChineseMsg(ExceptionEnum.SUCCESS.getChineseMessage());
            }
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getChineseMessage());
        }

        logger.info("获取商品列表返回: {}", responseDto);
        return responseDto;
    }

    private List<ProductVo> buildProductVoList(List<TblProduct> tblProductList) {
        List<ProductVo> productVoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tblProductList)) {
            productVoList = Lists.transform(tblProductList, new Function<TblProduct, ProductVo>() {
                public ProductVo apply(TblProduct input) {
                    return buildProductVo(input);
                }
            });
        }
        return productVoList;
    }

    private ProductVo buildProductVo(TblProduct input) {
        if (input != null) {
            ProductVo vo = new ProductVo();
            vo.setProductNo(input.getProductNo());
            vo.setName(input.getProductName());
            vo.setCatalogNo(input.getCatalogNo());
            vo.setDescription(input.getDescription());
            vo.setNowPrice(input.getNowPrice());
            vo.setPrice(input.getPrice());
            vo.setDeliveryType(input.getDeliveryType());
            vo.setIcon(input.getIcon());
            vo.setDetail(input.getDetail());
            if (Strings.isNullOrEmpty(input.getImages())) {
                vo.setImages(new ArrayList<String>());
            } else {
//                vo.setImages(input.getImages().split(","));
                vo.setImages(Lists.newArrayList(SPLITTER.split(input.getImages())));
            }
            return vo;
        }
        return null;
    }

    @RequestMapping(value = "/detail/{productNo}")
    @ResponseBody
    public ResponseDto getCatalogDetail(@PathVariable("productNo") final String productNo) {

        logger.info("获取商品详情请求 catalogNo={}", productNo);

        ResponseDto responseDto = new ResponseDto();

        try {
            if (Strings.isNullOrEmpty(productNo)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
                responseDto.setChineseMsg(ExceptionEnum.PARAM_INVALID.getChineseMessage());
            } else {
                TblProduct tblProduct = productService.getByProductNo(productNo);

                ProductVo productVo = buildProductVo(tblProduct);

                responseDto.addAttribute("item", productVo);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
                responseDto.setChineseMsg(ExceptionEnum.SUCCESS.getChineseMessage());
            }
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            responseDto.setChineseMsg(ExceptionEnum.SYSTEM_ERROR.getChineseMessage());
        }

        logger.info("获取商品详情返回: {}", responseDto);
        return responseDto;
    }
}

package com.abitty.controller;

import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblProduct;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.ProductService;
import com.abitty.vo.ProductVo;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseDto getProductList(final String catalogNo) {
        logger.info("获取商品列表请求 catalogNo={}", catalogNo);

        ResponseDto responseDto = new ResponseDto();

        try {
            if (Strings.isNullOrEmpty(catalogNo)) {
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
            } else {
                List<TblProduct> tblProductList = productService.getAllPublish(catalogNo);

                List<ProductVo> productVoList = buildProductVoList(tblProductList);

                responseDto.addAttribute("list", productVoList);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            }
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
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
            vo.setName(input.getName());
            vo.setCatalogNo(input.getCatalogNo());
            vo.setDescription(input.getDescription());
            vo.setNowPrice(input.getNowPrice());
            vo.setPrice(input.getPrice());
            vo.setIcon(input.getIcon());
            vo.setDetail(input.getDetail());
            vo.setImages(input.getImages());
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
            } else {
                TblProduct tblProduct = productService.getByProductNo(productNo);

                ProductVo productVo = buildProductVo(tblProduct);

                responseDto.addAttribute("item", productVo);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            }
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }

        logger.info("获取商品详情返回: {}", responseDto);
        return responseDto;
    }
}

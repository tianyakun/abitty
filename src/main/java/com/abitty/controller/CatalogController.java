package com.abitty.controller;

import com.abitty.dto.ResponseDto;
import com.abitty.entity.TblCatalog;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.CatalogService;
import com.abitty.vo.CatalogVo;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
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
@RequestMapping(value = "/catalog")
public class CatalogController {

    private final static Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    private CatalogService catalogService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public ResponseDto getCatalogList() {
        logger.info("获取商品类别列表请求");

        ResponseDto responseDto = new ResponseDto();

        try {
            List<TblCatalog> catalogList = catalogService.getAllCatalog();

            List<CatalogVo> catalogVoList = buildCatalogVoList(catalogList);

            responseDto.addAttribute("list", catalogVoList);

            responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }

        logger.info("获取商品类别列表返回: {}", responseDto);
        return responseDto;
    }

    private List<CatalogVo> buildCatalogVoList(List<TblCatalog> catalogList) {
        List<CatalogVo> catalogVoList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(catalogList)) {
            catalogVoList = Lists.transform(catalogList, new Function<TblCatalog, CatalogVo>() {
                public CatalogVo apply(TblCatalog input) {
                    return buildCatalogVo(input);
                }
            });
        }

        return catalogVoList;
    }

    @RequestMapping(value = "/item")
    @ResponseBody
    public ResponseDto getCatalogDetail(final String catalogNo) {

        logger.info("获取商品类别请求 catalogNo={}", catalogNo);

        ResponseDto responseDto = new ResponseDto();

        try {
            if (Strings.isNullOrEmpty(catalogNo)) {
                logger.error("参数校验失败");
                responseDto.setRetCode(ExceptionEnum.PARAM_INVALID.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.PARAM_INVALID.getErrorMsg());
            } else {
                TblCatalog tblCatalog = catalogService.getByCatalogNo(catalogNo);

                CatalogVo catalogVo = buildCatalogVo(tblCatalog);

                responseDto.addAttribute("item", catalogVo);

                responseDto.setRetCode(ExceptionEnum.SUCCESS.getErrorCode());
                responseDto.setRetMsg(ExceptionEnum.SUCCESS.getErrorMsg());
            }
        } catch (Exception e) {
            responseDto.setRetCode(ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            responseDto.setRetMsg(ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        }

        logger.info("获取商品类别返回 responseDto={}", responseDto);
        return responseDto;
    }

    private CatalogVo buildCatalogVo(TblCatalog input) {
        if (input == null) {
            return null;
        } else {
            CatalogVo vo = new CatalogVo();
            vo.setCatalogNo(input.getCatalogNo());
            vo.setName(input.getName());
            vo.setIcon(input.getIcon());
            vo.setDescription(input.getDescription());
            return vo;
        }
    }
}

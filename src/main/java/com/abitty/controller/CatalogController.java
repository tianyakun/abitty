package com.abitty.controller;

import com.abitty.entity.TblCatalog;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.CatalogService;
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
@RequestMapping(value = "/catalog")
public class CatalogController {

    private final static Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    private CatalogService catalogService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getCatalogList() {
        String retCode = "000000";
        String retMsg = "SUCCESS";

        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            Map<String, Object> data = Maps.newHashMap();

            List<TblCatalog> catalogList = catalogService.getAllCatalog();

            data.put("list", catalogList);

            resultMap.put("data", data);
        } catch (Exception e) {
            retCode = "999999";
            retMsg = "server error!";
        }

        resultMap.put("retCode", retCode);
        resultMap.put("retMsg", retMsg);
        return resultMap;
    }

    @RequestMapping(value = "/item")
    @ResponseBody
    public Map<String, Object> getCatalogDetail(HttpServletRequest request) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {

            String catalogNo = request.getParameter("catalogNo");

            if (Strings.isNullOrEmpty(catalogNo)) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {
                TblCatalog tblCatalog = catalogService.getByCatalogNo(catalogNo);

                Map<String, Object> data = Maps.newHashMap();
                data.put("item", tblCatalog);

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

//    @RequestMapping(value = "/{id:\\d+}/delete", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> deleteCatalog(@PathVariable("id") int id) {
//
//        String retCode = "000000";
//        String retMsg = "SUCCESS";
//
//        Map<String, Object> resultMap = Maps.newHashMap();
//
//        try {
//            TblCatalog tblCatalog = new TblCatalog();
//            tblCatalog.setId(id);
//            tblCatalog.setIsDelete(1);
//            catalogService.update(tblCatalog);
//        } catch (Exception e) {
//            retCode = "999999";
//            retMsg = "server error!";
//        }
//
//        resultMap.put("retCode", retCode);
//        resultMap.put("retMsg", retMsg);
//        return resultMap;
//    }
//
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addCatalog(TblCatalog tblCatalog) {
//
//        String retCode = "000000";
//        String retMsg = "SUCCESS";
//
//        Map<String, Object> resultMap = Maps.newHashMap();
//
//        try {
//            logger.info(tblCatalog.toString());
//            tblCatalog.setCreateTime(new Date());
//            catalogService.add(tblCatalog);
//        } catch (Exception e) {
//            retCode = "999999";
//            retMsg = "server error!";
//        }
//
//        resultMap.put("retCode", retCode);
//        resultMap.put("retMsg", retMsg);
//        return resultMap;
//    }
//
//    @RequestMapping(value = "/{id:\\d+}/upate", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> updateCatalog(TblCatalog tblCatalog) {
//
//        String retCode = "000000";
//        String retMsg = "SUCCESS";
//
//        Map<String, Object> resultMap = Maps.newHashMap();
//
//        try {
//            catalogService.update(tblCatalog);
//        } catch (Exception e) {
//            retCode = "999999";
//            retMsg = "server error!";
//        }
//
//        resultMap.put("retCode", retCode);
//        resultMap.put("retMsg", retMsg);
//        return resultMap;
//    }
}

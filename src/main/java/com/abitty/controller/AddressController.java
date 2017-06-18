package com.abitty.controller;

import com.abitty.dto.AddressDto;
import com.abitty.entity.TblAddress;
import com.abitty.enums.ExceptionEnum;
import com.abitty.service.AddressService;
import com.abitty.utils.ParamChecker;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yak on 17/6/15.
 */
//@Controller
//@RequestMapping(value = "/my/address")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAddress(final AddressDto addressDto) {

        logger.info("新增地址");

        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            //参数校验
            String constraintMessage = ParamChecker.getConstraintMessage(addressDto);
            if (!Strings.isNullOrEmpty(constraintMessage)) {
                logger.error("参数校验失败:{}", constraintMessage);
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            }

            TblAddress tblAddress = new TblAddress();
            tblAddress.setUid(addressDto.getUid());
            tblAddress.setProvince(addressDto.getProvince());
            tblAddress.setCity(addressDto.getCity());
            tblAddress.setArea(addressDto.getArea());
            tblAddress.setPcaDetail(addressDto.getProvince() + addressDto.getCity() + addressDto.getArea());
            tblAddress.setAddressDetail(addressDto.getAddressDetail());
            tblAddress.setPostcode(addressDto.getPostcode());
            tblAddress.setPhone(addressDto.getPhone());
            tblAddress.setIsDefault(0);
            tblAddress.setIsDelete(0);
            tblAddress.setCreateTime(new Date());

            addressService.add(tblAddress);

            resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());
            return resultMap;
        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> getAddressList(HttpServletRequest request) {
        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            String uid = request.getParameter("uid");

            if (Strings.isNullOrEmpty(uid)) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {
                List<TblAddress> tblAddressesList = addressService.getAllByUid(uid);

                Map<String, Object> data = Maps.newHashMap();
                data.put("list", tblAddressesList);

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

    @RequestMapping(value = "/item")
    @ResponseBody
    public Map<String, Object> getAddressDetail(HttpServletRequest request) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            String addressId = request.getParameter("addressId");

            if (Strings.isNullOrEmpty(addressId) || Ints.tryParse(addressId)==null) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {
                TblAddress tblAddresses = addressService.getAddress(Ints.tryParse(addressId));

                Map<String, Object> data = Maps.newHashMap();
                data.put("item", tblAddresses);

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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateAddress(AddressDto addressDto) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {

            if (addressDto.getAddressId() == null) {
                logger.error("addressId is null");
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            }

            TblAddress tblAddress = addressService.getAddress(addressDto.getAddressId());

            if (tblAddress != null) {
                if (!Strings.isNullOrEmpty(addressDto.getPhone())) {
                    tblAddress.setPhone(addressDto.getPhone());
                }
                if (!Strings.isNullOrEmpty(addressDto.getProvince())) {
                    tblAddress.setProvince(addressDto.getProvince());
                }
                if (!Strings.isNullOrEmpty(addressDto.getCity())) {
                    tblAddress.setCity(addressDto.getCity());
                }
                if (!Strings.isNullOrEmpty(addressDto.getArea())) {
                    tblAddress.setArea(addressDto.getArea());
                }
                if (!Strings.isNullOrEmpty(addressDto.getAddressDetail())) {
                    tblAddress.setAddressDetail(addressDto.getAddressDetail());
                }
                if (!Strings.isNullOrEmpty(addressDto.getRecName())) {
                    tblAddress.setRecName(addressDto.getRecName());
                }
                tblAddress.setPcaDetail(tblAddress.getProvince()+tblAddress.getCity()+tblAddress.getArea());
            }

            addressService.update(tblAddress);

            resultMap.put("retCode", ExceptionEnum.SUCCESS.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SUCCESS.getErrorMsg());
            return resultMap;
        } catch (Exception e) {
            resultMap.put("retCode", ExceptionEnum.SYSTEM_ERROR.getErrorCode());
            resultMap.put("retMsg", ExceptionEnum.SYSTEM_ERROR.getErrorMsg());
            return resultMap;
        }
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String, Object> deleteAddress(HttpServletRequest request) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            String addressId = request.getParameter("addressId");

            if (Strings.isNullOrEmpty(addressId) || Ints.tryParse(addressId)==null) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {
                TblAddress tblAddress = new TblAddress();
                tblAddress.setId(Ints.tryParse(addressId));
                tblAddress.setIsDelete(1);
                addressService.update(tblAddress);

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

    @RequestMapping(value = "/default")
    @ResponseBody
    public Map<String, Object> defaultAddress(HttpServletRequest request) {

        Map<String, Object> resultMap = Maps.newHashMap();

        try {
            String uid = request.getParameter("uid");
            String addressId = request.getParameter("addressId");

            if (Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(addressId) || Ints.tryParse(addressId)==null) {
                resultMap.put("retCode", ExceptionEnum.PARAM_INVALID.getErrorCode());
                resultMap.put("retMsg", ExceptionEnum.PARAM_INVALID.getErrorMsg());
                return resultMap;
            } else {
                List<TblAddress> tblAddressesList = addressService.getAllByUid(uid);
                if (CollectionUtils.isNotEmpty(tblAddressesList)) {
                    for (TblAddress tblAddress : tblAddressesList) {
                        if (tblAddress.getIsDefault() == 1) {
                            tblAddress.setIsDefault(0);
                            addressService.update(tblAddress);
                        }
                    }
                }

                TblAddress tblAddress = new TblAddress();
                tblAddress.setId(Ints.tryParse(addressId));
                tblAddress.setIsDefault(1);
                addressService.update(tblAddress);

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

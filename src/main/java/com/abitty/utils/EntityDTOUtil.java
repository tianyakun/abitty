package com.abitty.utils;

import com.abitty.constant.AbittyConstants;
import com.abitty.dto.OrderConfirmRequestDto;
import com.abitty.dto.SendMessageRequestDto;
import com.abitty.entity.TblAddress;
import com.abitty.entity.TblMessageInfo;
import com.abitty.entity.TblOrderInfo;

import java.util.Date;

/**
 * Created by yak on 17/6/20.
 */
public class EntityDTOUtil {
    public static TblMessageInfo sendMessageRequestDtoToTblMessageInfo(SendMessageRequestDto sendMessageRequestDto) {
        TblMessageInfo tblMessageInfo = new TblMessageInfo();
        tblMessageInfo.setMessageType(sendMessageRequestDto.getMessageType().getCode());
        tblMessageInfo.setMessageChannel(sendMessageRequestDto.getMessageChannel());
        tblMessageInfo.setTemplateCode(sendMessageRequestDto.getTemplateCode());
        tblMessageInfo.setMessageAddress(sendMessageRequestDto.getMessageAddress());
        return tblMessageInfo;
    }

    public static TblOrderInfo OrderConfirmRequestDtoToTblOrderInfo(OrderConfirmRequestDto requestDto) {
        TblOrderInfo tblOrderInfo = new TblOrderInfo();

        tblOrderInfo.setOrderNo("O" + Sequence.next());
        tblOrderInfo.setUid(requestDto.getUid());
        tblOrderInfo.setProductNo(requestDto.getProductNo());
        tblOrderInfo.setServiceAtomCount(requestDto.getServiceAtomCount());
        tblOrderInfo.setTotalQuantity(requestDto.getTotalQuantity());
        tblOrderInfo.setTotalAmount(requestDto.getTotalAmount());
        tblOrderInfo.setPayId(String.valueOf(Sequence.next()));
        tblOrderInfo.setStatus(AbittyConstants.OrderState.INITIAL);
        tblOrderInfo.setDeliveryType(requestDto.getDeliveryType());
        tblOrderInfo.setSubQuantity(requestDto.getSubQuantity());
        tblOrderInfo.setTotalSub(requestDto.getTotalSub());
        tblOrderInfo.setCreateTime(new Date());
        tblOrderInfo.setUserNumber(requestDto.getUserNumber());
        tblOrderInfo.setRemark(requestDto.getRemark());
        tblOrderInfo.setProductBody(requestDto.getProductBody());
        tblOrderInfo.setIp(requestDto.getIp());
        tblOrderInfo.setOpenidCode(requestDto.getOpenidCode());

        tblOrderInfo.setProductBody("一点生活-商品");//todo

        return tblOrderInfo;

    }

    public static TblAddress OrderConfirmRequestDtoToTblAddress(OrderConfirmRequestDto requestDto) {
        TblAddress tblAddress = new TblAddress();
        tblAddress.setUid(requestDto.getUid());
        tblAddress.setProvince(requestDto.getAddressProvince());
        tblAddress.setCity(requestDto.getAddressCity());
        tblAddress.setArea(requestDto.getAddressArea());
        tblAddress.setPcaDetail(tblAddress.getProvince() + tblAddress.getCity() + tblAddress.getArea());
        tblAddress.setAddressDetail(requestDto.getAddressDetail());
        tblAddress.setPostcode(requestDto.getPostcode());
        return tblAddress;
    }
}

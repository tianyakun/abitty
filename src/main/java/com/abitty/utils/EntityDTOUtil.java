package com.abitty.utils;

import com.abitty.dto.SendMessageRequestDto;
import com.abitty.entity.TblMessageInfo;

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
}

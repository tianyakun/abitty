package com.abitty.biz;

import com.abitty.dto.ResponseDto;

/**
 * Created by yak on 17/6/23.
 */
public interface WechatProcessBiz {


    void getTicket(String code, ResponseDto responseDto);
}

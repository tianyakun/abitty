package com.abitty.biz;

import com.abitty.dto.OrderConfirmRequestDto;
import com.abitty.dto.ResponseDto;

/**
 * Created by yak on 17/6/24.
 */
public interface OrderProcessBiz {
    void confirmOrder(OrderConfirmRequestDto requestDto, ResponseDto responseDto);
}

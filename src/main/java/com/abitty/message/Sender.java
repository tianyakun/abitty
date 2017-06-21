package com.abitty.message;

import com.abitty.entity.TblMessageInfo;

/**
 * Created by yak on 17/6/21.
 */
public interface Sender {

    public void send(final TblMessageInfo tblMessageInfo);
}

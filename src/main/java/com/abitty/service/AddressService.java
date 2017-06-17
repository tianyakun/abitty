package com.abitty.service;

import com.abitty.entity.TblAddress;

import java.util.List;

/**
 * Created by yak on 17/6/15.
 */
public interface AddressService {
    List<TblAddress> getAllByUid(String uid);

    TblAddress getAddress(int addressId);

    int add(TblAddress tblAddress);

    int update(TblAddress tblAddress);
}

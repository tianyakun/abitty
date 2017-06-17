package com.abitty.service.impl;

import com.abitty.dao.TblAddressMapper;
import com.abitty.entity.TblAddress;
import com.abitty.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yak on 17/6/15.
 */
@Component
public class AddressServiceImpl implements AddressService {

    @Autowired
    private TblAddressMapper tblAddressMapper;

    public List<TblAddress> getAllByUid(String uid) {
        return tblAddressMapper.selectAllByUid(uid);
    }

    public TblAddress getAddress(int addressId) {
        return tblAddressMapper.selectByPrimaryKey(addressId);
    }

    public int add(TblAddress tblAddress) {
        return tblAddressMapper.insertSelective(tblAddress);
    }

    public int update(TblAddress tblAddress) {
        return tblAddressMapper.updateByPrimaryKeySelective(tblAddress);
    }
}

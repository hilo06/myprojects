package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    public Seller findBySid(int sid);
}

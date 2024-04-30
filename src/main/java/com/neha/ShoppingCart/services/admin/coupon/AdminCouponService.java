package com.neha.ShoppingCart.services.admin.coupon;

import com.neha.ShoppingCart.entity.Coupon;

import java.util.List;

public interface AdminCouponService {

    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();
}

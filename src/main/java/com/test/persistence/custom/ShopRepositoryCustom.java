package com.test.persistence.custom;

import com.test.domain.Shop;

import java.util.List;

public interface ShopRepositoryCustom {

    List<Shop> findShopInfo();

    Shop findByIdShopInfo(Long id);
}

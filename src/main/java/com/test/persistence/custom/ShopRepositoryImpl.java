package com.test.persistence.custom;

import com.querydsl.jpa.impl.JPAQuery;
import com.test.domain.Shop;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.test.domain.QBusinessTime.businessTime;
import static com.test.domain.QHoliday.holiday;
import static com.test.domain.QShop.shop;

@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Shop> findShopInfo() {
        JPAQuery<Shop> query = new JPAQuery(em);
        return query.from(shop)
                .leftJoin(shop.businessTimes, businessTime).fetchJoin()
                .leftJoin(shop.holidays, holiday).fetchJoin()
                .orderBy(shop.level.asc())
                .select(shop)
                .distinct()
                .fetch();
    }

    @Override
    public Shop findByIdShopInfo(Long id) {
        JPAQuery<Shop> query = new JPAQuery(em);
        //LocalDate now = LocalDate.now();
        return query.from(shop)
                .leftJoin(shop.businessTimes, businessTime).fetchJoin()
                .leftJoin(shop.holidays, holiday).fetchJoin()
                .where(shop.id.eq(id))
                //.where(holiday.day.between(now, now.plusDays(2)))
                .fetchOne();
    }
}

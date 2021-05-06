package com.test.service;

import com.test.domain.Shop;
import com.test.dto.ReqShop;
import com.test.dto.ResShop;
import com.test.dto.Week;
import com.test.persistence.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiServiceTest {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ShopRepository shopRepository;

    @Test
    @DisplayName("점포 리스트 조회 테스트")
    void getShopsTest() {
        //given
        createShop();

        //when
        Set<ResShop.SelectList> shops = apiService.getShops();

        //then
        assertNotNull(shops);
    }

    @Test
    @DisplayName("점포 추가 테스트")
    @Transactional
    void createShop() {
        //given
        ReqShop.Create create = new ReqShop.Create();
        ReqShop.Create.BusinessTime businessTime = new ReqShop.Create.BusinessTime();
        create.setName("점포명");
        create.setOwner("대표자명");
        create.setDescription("코멘트");
        create.setLevel(1);
        create.setAddress("서울특별시 강남구 강남동 1-1");
        create.setPhone("010-1234-1234");
        businessTime.setDay(Week.Friday);
        businessTime.setOpen(LocalTime.now());
        businessTime.setClose(LocalTime.now().plusHours(2));
        create.setBusinessTimes(List.of(businessTime));

        //when
        apiService.createShop(create);

        //then
        Set<ResShop.SelectList> shops = apiService.getShops();
        assertNotNull(shops);
    }

    @Test
    @Transactional
    @DisplayName("휴무일 추가 테스트")
    void addBusinessTimeTest() {
        //given
        createShop();
        ReqShop.PostHoliday vo = new ReqShop.PostHoliday();
        vo.setId(1L);
        vo.setHolidays(List.of(LocalDate.now(), LocalDate.now().plusDays(1)));

        //when
        apiService.addHoliday(vo);

        //then
        Shop shop = shopRepository.findById(1L).get();
        assertNotNull(shop);
    }

    @Test
    @Transactional
    @DisplayName("점포 상세 테스트")
    void getShopDetailTest() {
        //given
        createShop();

        //when
        ResShop.SelectDetail shopDetail = apiService.getShopDetail(1L);

        //then
        assertNotNull(shopDetail);
    }

}
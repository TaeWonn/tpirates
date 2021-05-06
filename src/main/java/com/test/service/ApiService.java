package com.test.service;

import com.test.domain.BusinessTime;
import com.test.domain.Holiday;
import com.test.domain.Shop;
import com.test.dto.BusinessStatus;
import com.test.dto.ReqShop;
import com.test.dto.ResShop;
import com.test.dto.Week;
import com.test.exception.custom.NoDataException;
import com.test.persistence.ShopRepository;
import com.test.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final ShopRepository shopRepository;

    public void deleteShop(Long id) {
        shopRepository.deleteById(id);
    }

    @Transactional
    public void createShop(ReqShop.Create vo) {
        Shop shop = Shop.from(
                vo.getName()
                , vo.getOwner()
                , vo.getDescription()
                , vo.getLevel()
                , vo.getAddress()
                , vo.getPhone()
        );

        Set<BusinessTime> businessTimes = shop.getBusinessTimes();

        vo.getBusinessTimes().stream().forEach(businessTime -> {
            BusinessTime from = BusinessTime.from(
                    Week.convert(businessTime.getDay())
                    , businessTime.getOpen()
                    , businessTime.getClose()
            );
            businessTimes.add(from);
        });

        shopRepository.save(shop);
    }

    public void addHoliday(ReqShop.PostHoliday vo) {
        Shop shop = shopRepository.findById(vo.getId())
                .orElseThrow(() -> new NoDataException("존재하지 않거나 삭제된 점포 입니다."));

        Set<Holiday> holidays = shop.getHolidays();
        vo.getHolidays().forEach(holiday -> {
            Holiday from = Holiday.from(holiday);
            holidays.add(from);
        });

        shopRepository.save(shop);
    }

    @Transactional(readOnly = true)
    public Set<ResShop.SelectList> getShops() {
        List<Shop> shopInfo = shopRepository.findShopInfo();
        return getShopResponseList(shopInfo);
    }

    public ResShop.SelectDetail getShopDetail(Long id) {
        Shop shop = shopRepository.findByIdShopInfo(id);

        if (Objects.isNull(shop)) {
            throw new NoDataException("존재하지 않거나 삭제된 점포입니다.");
        }

        Set<Holiday> holidays = shop.getHolidays();
        Set<BusinessTime> businessTimes = shop.getBusinessTimes();
        LocalDate now = LocalDate.now();


        return new ResShop.SelectDetail(
                shop.getId()
                , shop.getName()
                , shop.getDescription()
                , shop.getLevel()
                , shop.getAddress()
                , shop.getPhone()
                , getBusinessDay(now, holidays, businessTimes)
        );
    }

    private Set<ResShop.SelectList> getShopResponseList(List<Shop> shops) {
        if (Objects.isNull(shops) || shops.isEmpty()) return null;

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        Set<ResShop.SelectList> list = new HashSet<>();
        for (Shop shop : shops) {
            BusinessStatus status;

            if (DateUtil.isHoliday( shop.getHolidays(), localDate )) {
                status = BusinessStatus.HOLIDAY;
            } else {
                status = DateUtil.getShopStatus( shop.getBusinessTimes(), localDate, localTime );
            }

            ResShop.SelectList vo = new ResShop.SelectList(
                    shop.getName()
                    , shop.getDescription()
                    , shop.getLevel()
                    , status
            );

            list.add(vo);
        }

        return list;
    }

    private List<ResShop.SelectDetail.BusinessDay> getBusinessDay(LocalDate now, Set<Holiday> holidays
                                                ,Set<BusinessTime> businessTimes) {
        if (Objects.isNull(holidays)) holidays = new HashSet<>();

        List<ResShop.SelectDetail.BusinessDay> result = new ArrayList<>();

        LocalDate oneLaterDay = now.plusDays(1);
        LocalDate twoLaterDay = now.plusDays(2);
        LocalTime localTime = LocalTime.now();

        // 3일치 영업시간 정보
        addBusinessDay(now, localTime, businessTimes, holidays, result);
        addBusinessDay(oneLaterDay, localTime, businessTimes, holidays, result);
        addBusinessDay(twoLaterDay, localTime, businessTimes, holidays, result);

        return result;
    }

    private void addBusinessDay(LocalDate date ,LocalTime time, Set<BusinessTime> businessTimes
                                , Set<Holiday> holidays, List<ResShop.SelectDetail.BusinessDay> result) {
        for (BusinessTime businessTime: businessTimes) {
            if (businessTime.getDay().equals(date.getDayOfWeek()) ) {

                // 휴뮤일
                if (DateUtil.isHoliday(holidays, date)) {

                    ResShop.SelectDetail.BusinessDay businessDay = new ResShop.SelectDetail.BusinessDay(
                            DateUtil.WEEK[date.getDayOfWeek().getValue() -1]
                            , DateUtil.getHourMinuteTimeStr(businessTime.getOpen())
                            , DateUtil.getHourMinuteTimeStr(businessTime.getClose())
                            , BusinessStatus.HOLIDAY
                    );

                    result.add(businessDay);
                } else {
                    // 영업중
                    if (DateUtil.isShopStatusOpen(businessTime.getOpen(), businessTime.getClose(), time)) {
                        ResShop.SelectDetail.BusinessDay businessDay = new ResShop.SelectDetail.BusinessDay(
                                DateUtil.WEEK[date.getDayOfWeek().getValue() -1]
                                , DateUtil.getHourMinuteTimeStr(businessTime.getOpen())
                                , DateUtil.getHourMinuteTimeStr(businessTime.getClose())
                                , BusinessStatus.OPEN
                        );
                        result.add(businessDay);
                    // 비 영업중
                    } else {
                        ResShop.SelectDetail.BusinessDay businessDay = new ResShop.SelectDetail.BusinessDay(
                                DateUtil.WEEK[date.getDayOfWeek().getValue() -1]
                                , DateUtil.getHourMinuteTimeStr(businessTime.getOpen())
                                , DateUtil.getHourMinuteTimeStr(businessTime.getClose())
                                , BusinessStatus.CLOSE
                        );
                        result.add(businessDay);
                    }
                }
            }
        }
    }
}

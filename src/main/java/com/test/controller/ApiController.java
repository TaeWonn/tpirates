package com.test.controller;

import com.google.common.collect.Sets;
import com.test.dto.ReqShop;
import com.test.dto.ResShop;
import com.test.exception.custom.DateEqualsException;
import com.test.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ApiController {

    private final ApiService apiService;

    /* A. 점포 추가 API */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createShop(@RequestBody @Valid ReqShop.Create vo) {
        vo.getBusinessTimes().forEach(businessTime -> {
            if (businessTime.getOpen().equals(businessTime.getClose()))
                throw new DateEqualsException("오픈 시간과 마감 시간을 다르게 해주세요.");
        });

        apiService.createShop(vo);
    }

    /* B. 점포 휴뮤일 등록 API */
    @PostMapping("/businessTime")
    @ResponseStatus(HttpStatus.CREATED)
    public void shopAddBusinessTime(@RequestBody @Valid ReqShop.PostHoliday vo) {
        apiService.addHoliday(vo);
    }

    /* C. 점포 목록 조회 API */
    @GetMapping
    public Set<ResShop.SelectList> getShops() {
        return apiService.getShops();
    }

    /* D. 점포 상세 정보 조회 API */
    @GetMapping("/{id}")
    public ResShop.SelectDetail getShopDetail(@PathVariable Long id) {
        return apiService.getShopDetail(id);
    }

    /* E. 점포 삭제 API */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShop(@PathVariable Long id) {
        apiService.deleteShop(id);
    }

}

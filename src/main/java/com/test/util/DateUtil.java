package com.test.util;

import com.test.domain.BusinessTime;
import com.test.domain.Holiday;
import com.test.dto.BusinessStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

public class DateUtil {

    public static final String[] WEEK = {"Monday", "Tuesday", "Wednesday", "Thursday", "FriDay", "Saturday", "Sunday"};
    private static final DateTimeFormatter HHMM_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalTime minTime = LocalTime.of(0,0);

    public static boolean isShopStatusOpen(LocalTime open, LocalTime close, LocalTime now) {
        if (close.equals(minTime))
            close = LocalTime.MAX;

        if (now.isAfter(open) && now.isBefore(close)) {
            return true;
        }
        return false;
    }

    public static boolean isHoliday(Set<Holiday> holidays, LocalDate now) {

        if (Objects.nonNull(holidays)) {
            for (Holiday holiday: holidays) {
                if (now.equals(holiday.getDay())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static BusinessStatus getShopStatus(Set<BusinessTime> list, LocalDate localDate, LocalTime localTime) {

        for (BusinessTime businessTime: list) {
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if (businessTime.getDay().equals(dayOfWeek)) {
                if (isShopStatusOpen(businessTime.getOpen(), businessTime.getClose(), localTime)) {
                    return BusinessStatus.OPEN;
                } else {
                    return BusinessStatus.CLOSE;
                }

            }
        }
        return BusinessStatus.CLOSE;
    }

    public static String getHourMinuteTimeStr(LocalTime localTime) {
        return localTime.format(HHMM_FORMAT);
    }


}

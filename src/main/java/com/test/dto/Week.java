package com.test.dto;

import com.test.exception.custom.WeekNullException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Week implements Serializable {

    Monday("Monday"),
    Tuesday("Tuesday"),
    Wednesday("Wednesday"),
    Thursday("Thursday"),
    Friday("FriDay"),
    Saturday("Saturday"),
    Sunday("Sunday");


    private static final Long serialVersionUID = 1231244123L;
    private String day;

    public String toString() {
        return day;
    }

    public static DayOfWeek convert(Week day) {
        if (Objects.isNull(day)) {
            throw new WeekNullException("요일을 입력해주세요.");
        }

        if(day.equals(Monday)) {
            return DayOfWeek.MONDAY;
        } else if (day.equals(Tuesday)) {
            return DayOfWeek.TUESDAY;
        } else if (day.equals(Wednesday)) {
            return DayOfWeek.WEDNESDAY;
        } else if (day.equals(Thursday)) {
            return DayOfWeek.THURSDAY;
        } else if (day.equals(Friday)) {
            return DayOfWeek.FRIDAY;
        } else if (day.equals(Saturday)) {
            return DayOfWeek.SATURDAY;
        } else if (day.equals(Sunday)) {
            return DayOfWeek.SUNDAY;
        } else {
            throw new WeekNullException("요일을 입력해주세요.");
        }
    }
}

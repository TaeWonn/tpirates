package com.test.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "business_time")
public class BusinessTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day", nullable = false)
    private DayOfWeek day;

    @Column(name = "open", nullable = false)
    private LocalTime open;

    @Column(name = "close", nullable = false)
    private LocalTime close;

    @ManyToOne
    private Shop shop;

    @Builder
    public BusinessTime(DayOfWeek day, LocalTime open, LocalTime close) {
        this.day = day;
        this.open = open;
        this.close = close;
    }

    public static BusinessTime from(DayOfWeek day, LocalTime open, LocalTime close) {
        return BusinessTime.builder()
                .day(day)
                .open(open)
                .close(close)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof BusinessTime) {
            if (this.id.equals (((BusinessTime) obj).getId()))
                return true;
        }

        return false;
    }
}

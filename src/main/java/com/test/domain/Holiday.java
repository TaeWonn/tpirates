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
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "holiday")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day", nullable = false)
    private LocalDate day;

    @Builder
    public Holiday(LocalDate day) {
        this.day = day;
    }

    public static Holiday from(LocalDate day) {
        return Holiday.builder()
                .day(day)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof Holiday) {
            if (this.id.equals(((Holiday) obj).getId()))
                return true;
        }

        return false;
    }
}

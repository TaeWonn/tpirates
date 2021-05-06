package com.test.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "owner", length = 10,nullable = false)
    private String owner;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "level")
    private Integer level;

    @Column(name = "address", length = 500, nullable = false)
    private String address;

    @Column(name = "phone", length = 13)
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BusinessTime> businessTimes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Holiday> holidays = new HashSet<>();

    @Builder
    public Shop (String name, String owner, String description, Integer level,
                 String address, String phone) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.level = level;
        this.address = address;
        this.phone = phone;
    }

    public static Shop from(String name, String owner, String description, Integer level,
                            String address, String phone) {
        return Shop.builder()
                .name(name)
                .owner(owner)
                .description(description)
                .level(level)
                .address(address)
                .phone(phone)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof Shop) {
            if (this.id.equals(((Shop) obj).getId()))
                return true;
        }

        return false;
    }
}

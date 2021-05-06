package com.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class ResShop {

    private static final String MIN_TIME = "00:00";
    private static final String MAX_TIME = "24:00";

    @Getter
    @AllArgsConstructor
    public static class SelectList {
        private String name;
        private String description;
        private Integer level;
        private BusinessStatus businessStatus;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (obj instanceof SelectList) {
                if (this.name.equals(((SelectList) obj).getName())
                        && this.description.equals(((SelectList) obj).getDescription())
                        && this.level.equals(((SelectList) obj).getLevel())
                        && this.businessStatus.equals(((SelectList) obj).getBusinessStatus()))
                    return true;
            }
            return false;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class SelectDetail {
        private Long id;
        private String name;
        private String description;
        private Integer level;
        private String address;
        private String phone;
        private List<BusinessDay> businessDays;

        @Getter
        public static class BusinessDay {
            private String day;
            private String open;
            private String close;
            private BusinessStatus status;

            public BusinessDay(String day, String open, String close, BusinessStatus status) {
                this.day = day;
                this.open = open;
                this.close = MIN_TIME.equals(close) ? MAX_TIME : close;
                this.status = status;
            }
        }
    }
}

package com.test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReqShop {

    @Valid
    private Create create;
    @Valid
    private ReqShop.PostHoliday postHoliday;

    @Getter @Setter
    public static class Create {
        @NotBlank(message = "점포명을 입력해 주세요.")
        private String name;
        @NotBlank(message = "대표자명을 입력해 주세요.")
        private String owner;
        private String description;
        @NotNull(message = "우선 순위를 지정해주세요.")
        private Integer level;
        @NotBlank(message = "주소를 입력해 주세요.")
        private String address;
        @NotBlank(message = "전화 번호를 입력해 주세요.")
        @Pattern(regexp = "01[0-9]-[0-9]{4}-[0-9]{4}", message = "전화번호를 형식에 맞게 입력해주세요.")
        private String phone;

        @NotEmpty(message = "영업 시간을 추가해 주세요.")
        private List<BusinessTime> businessTimes;

        @Getter @Setter
        public static class BusinessTime {
            //@NotNull
            @Enumerated(EnumType.STRING)
            private Week day;
            @NotNull
            @JsonFormat(pattern = "kk:mm", shape = JsonFormat.Shape.STRING)
            private LocalTime open;
            @NotBlank
            @JsonFormat(pattern = "kk:mm", shape = JsonFormat.Shape.STRING)
            private LocalTime close;
        }
    }

    @Getter
    @Setter
    public static class PostHoliday {
        @NotNull(message = "가게를 선택해 주세요.")
        private Long id;
        @Valid
        @NotEmpty(message = "휴무일을 추가해 주세요.")
        private List<LocalDate> holidays;

    }
}

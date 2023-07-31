package com.yeol.market.point.ui.dto;

import com.yeol.market.point.application.dto.PointChargeDto;
import com.yeol.market.point.application.dto.PointSpendDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PointSpendRequest {

    private Long memberId;
    private Long paymentPrice;

    public PointSpendDto toServiceDto() {
           return new PointSpendDto(memberId, paymentPrice);
       }
}

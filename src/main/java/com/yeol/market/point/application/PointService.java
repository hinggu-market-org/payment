package com.yeol.market.point.application;



import com.yeol.market.point.application.dto.PointChargeDto;
import com.yeol.market.point.application.dto.PointResponse;
import com.yeol.market.point.application.dto.PointSpendDto;
import com.yeol.market.point.application.exception.PointException;
import com.yeol.market.point.domain.Point;
import com.yeol.market.point.domain.exception.NotEnoughBalanceException;
import com.yeol.market.point.domain.repository.PointRepository;
import com.yeol.market.point.exception.NotFoundPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public PointResponse chargePoint(final PointChargeDto pointChargeDto) {
        final Point point = findPointByMemberId(pointChargeDto.getMemberId());

        point.charge(pointChargeDto.getChargePrice());

        return PointResponse.of(point);
    }

    public void spendPoint(final PointSpendDto pointSpendDto) {
        final Point point = findPointByMemberId(pointSpendDto.getMemberId());
        try {
            point.spend(pointSpendDto.getPaymentPrice());
        } catch (NotEnoughBalanceException e) {
            throw new PointException(e.getMessage());
        }
    }

    private Point findPointByMemberId(final Long memberId) {
        return pointRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundPointException("해당 회원의 포인트를 찾을 수 없습니다."));
    }
}

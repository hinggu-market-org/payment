package com.yeol.market.point.utils;


import com.yeol.market.point.application.PointService;
import com.yeol.market.point.config.DatabaseCleaner;
import com.yeol.market.point.domain.Point;
import com.yeol.market.point.domain.repository.PointRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServiceTest {

    @Autowired
    protected PointService pointService;

    @Autowired
    protected PointRepository pointRepository;

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    protected Point 포인트_등록(final Point point) {
        return pointRepository.save(point);
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clear();
    }
}

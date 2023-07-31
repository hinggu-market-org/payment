package com.yeol.market.point.utils;

import com.yeol.market.point.config.DatabaseCleaner;
import com.yeol.market.point.domain.Point;
import com.yeol.market.point.domain.repository.PointRepository;
import com.yeol.market.point.ui.dto.PointChargeRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import static com.yeol.market.point.fixture.PointFixture.*;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public abstract class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    protected PointRepository pointRepository;
    @Autowired
    protected PlatformTransactionManager transactionManager;
    protected TransactionTemplate transactionTemplate;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    protected String 당근_UUID;
    protected String 대파_UUID;
    protected String 감자_UUID;
    protected String 양배추_UUID;
    protected String 양파_UUID;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        당근_UUID = String.valueOf('0');
        대파_UUID = String.valueOf('1');
        감자_UUID = String.valueOf('2');
        양배추_UUID = String.valueOf('3');
        양파_UUID = String.valueOf('4');

        포인트_등록(new Point(멤버1_ID, 100000L));
        포인트_등록(new Point(멤버2_ID, 10000L));
        포인트_등록(new Point(멤버3_ID, 0L));
    }

    protected Point 포인트_등록(final Point point) {
        return pointRepository.save(point);
    }

    protected ExtractableResponse<Response> 포인트_충전_요청(final Long memberId, final Long balance) {
        final var chargeRequest = new PointChargeRequest(memberId, balance);
        return post("/points/charge", chargeRequest);
    }

    protected ExtractableResponse<Response> post(final String uri, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> get(final String uri) {
        return RestAssured.given().log().all()
                .when().get(uri)
                .then().log().all()
                .extract();
    }

    public Long 포인트_잔액_확인(final Long memberId) {
        final Long balance = transactionTemplate.execute(status -> {
            final Point point = pointRepository.findByMemberId(memberId).get();
            return point.getBalance();
        });
        return balance;
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clear();
    }

}

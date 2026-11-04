package hello.aop;

import hello.aop.order.AspectV1;
import hello.aop.order.AspectV2;
import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
//@Import(AspectV1.class)
@Import(AspectV2.class)
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("aopInfo")
    void aopInfo() throws Exception {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    @DisplayName("success")
    void success() throws Exception {
        orderService.orderItem("itemA");
    }

    @Test
    @DisplayName("exception")
    void exception() throws Exception {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex")).isInstanceOf(
                IllegalStateException.class);
    }
}

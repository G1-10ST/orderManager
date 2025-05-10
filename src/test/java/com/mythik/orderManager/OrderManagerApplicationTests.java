package com.mythik.orderManager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class OrderManagerApplicationTests {

    @Test
    void ApplicationMainTest() {
        assertDoesNotThrow(() -> OrderManagerApplication.main());
    }

}

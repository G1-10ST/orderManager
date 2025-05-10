package com.mythik.orderManager.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class CommonConstantsTest {
    @Test
    void testUserConstant() {
        assertEquals("USER", CommonConstants.USER, "USER constant should have the value 'USER'");
    }
    @Test
    void testPrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<CommonConstants> constructor = CommonConstants.class.getDeclaredConstructor();
        assertTrue(constructor.canAccess(null), "Constructor should be accessible");
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance, "Should throw exception when trying to instantiate");
    }

}
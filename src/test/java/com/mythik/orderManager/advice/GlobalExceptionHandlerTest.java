package com.mythik.orderManager.advice;

import com.mythik.orderManager.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @RestController
    @RequestMapping("/test")
    static class MockController {
        @GetMapping("/validation-error")
        public void throwValidationException() throws MethodArgumentNotValidException, NoSuchMethodException {
            BindingResult bindingResult = new BeanPropertyBindingResult(null, "objectName");
            MethodParameter methodParameter = new MethodParameter(
                    MockController.class.getDeclaredMethod("throwValidationException"), -1);
            throw new MethodArgumentNotValidException(methodParameter, bindingResult);
        }

        @GetMapping("/not-found")
        public void throwNotFoundException() {
            throw new ResourceNotFoundException("Resource not found");
        }
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MockController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testHandleMethodArgumentNotValidException() throws Exception {
        mockMvc.perform(get("/test/validation-error")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("OM-400"))
                .andExpect(jsonPath("$.message").value("Validation Failure"));
    }

    @Test
    void testHandleResourceNotFoundException() throws Exception {
        mockMvc.perform(get("/test/not-found")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("OM-401"))
                .andExpect(jsonPath("$.message").value("Resource not found"));
    }
}
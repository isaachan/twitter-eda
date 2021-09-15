package com.daimler.architecture.model;

import com.daimler.architecture.web.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegratedTests {

    @Autowired
    private UserController controller;

    @Test
    public void loadContext() throws Exception {
        assertThat(controller).isNotNull();
    }
}

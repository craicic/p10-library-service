package com.gg.proj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = com.gg.proj.app.ApplicationConfig.class)
public class LibraryServiceApplicationTests {

    @Test
    public void contextLoads() {
    }

}


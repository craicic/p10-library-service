package com.gg.proj;

import com.gg.proj.app.LibraryServiceApplication;
import com.gg.proj.consumer.BookingRepository;
import com.gg.proj.model.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = LibraryServiceApplication.class)
public class LibraryServiceApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void quickTestQuery() {
        UserEntity expected = new UserEntity();
        expected.setMailAddress("rou@gmail.com");
        UserEntity actual = bookingRepository.queryForBorrower(26).get(0);
        Assert.assertEquals(expected.getMailAddress(), actual.getMailAddress());
    }

}


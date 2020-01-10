package com.gg.proj;

import com.gg.proj.app.LibraryServiceApplication;
import com.gg.proj.consumer.BookingRepository;
import com.gg.proj.model.UserEntity;
import com.gg.proj.model.complex.BorrowerModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
        BorrowerModel actual = bookingRepository.queryForBorrower(26).get(0);
        Assert.assertNotEquals(expected.getMailAddress(), actual.getMailAddress());

        bookingRepository.fetchExpiredBookings(LocalDateTime.now());
    }

}


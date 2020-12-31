package com.itgarden.website;

import com.itgarden.website.exam.ripository.ExamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsiteApplicationTests {

    @Autowired
    ExamRepository examRepository;

    @Test
    public void contextLoads() {
        
        

    }

}

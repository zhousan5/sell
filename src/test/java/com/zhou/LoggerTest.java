package com.zhou;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * logback  application.yml  配置相对简单功能也简单
 *
 * logback-spring.xml
 * 如果要区分info和error日志
 * 每天产生一个日志文件则需要
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {
//    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
        String name = "zhou";
        String password = "123456";
        log.debug("debug....");
        log.info("info...");
        log.info("name:{},password:{}",name,password);
        log.error("error...");
    }
}

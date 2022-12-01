package com.jzetech.interfaceinvokelogdemo;

import com.jzetech.aspect.InterfaceInvokeRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class InterfaceInvokeLogDemoApplicationTests {

    @Autowired
    TestCutPoint testCutPoint;

    @Test
    public void contextLoads() {
        testCutPoint.t();
    }

    @InterfaceInvokeRecord
    public void t() {
        System.out.println("休眠 1s ...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("休眠结束！");
    }

}

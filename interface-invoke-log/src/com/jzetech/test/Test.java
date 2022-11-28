package com.jzetech.test;

import com.jzetech.aspect.InterfaceInvokeRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class Test {
    @PostConstruct
    @InterfaceInvokeRecord
    public void test() {
        System.out.println("休眠 1 s");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("休眠结束");
    }
}

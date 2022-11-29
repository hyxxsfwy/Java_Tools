package com.jzetech.interfaceinvokelogdemo;

import com.jzetech.aspect.InterfaceInvokeRecord;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TestCutPoint {

    @InterfaceInvokeRecord
    public void t(){
        System.out.println("休眠 1s ...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("休眠结束！");
    }

}

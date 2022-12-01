package com.jzetech.interfaceinvokelogdemo;

import com.jzetech.aspect.InterfaceInvokeRecord;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TestCutPoint {

    @InterfaceInvokeRecord
    public void t(){
        System.out.println("休眠 1s ...");
        try {
//            TimeUnit.SECONDS.sleep(1);
            String i = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
            i = i + i + i + i + i + i + i + i + i + i + i + i;
            i = i + i + i + i + i + i + i + i + i + i + i + i;
            i = i + i + i + i + i + i + i + i + i + i + i + i;
            i = i + i + i + i + i + i + i + i + i + i + i + i;
            i = i + i + i + i + i + i + i + i + i + i + i + i;
            i = i + i + i + i + i + i + i + i + i + i + i + i;
            long j = GraphLayout.parseInstance(i).totalSize();
            System.out.println(">>>>>> " + j);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("休眠结束！");
    }

}

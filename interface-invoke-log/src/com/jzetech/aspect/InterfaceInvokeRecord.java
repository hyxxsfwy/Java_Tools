package com.jzetech.aspect;

import java.lang.annotation.*;

/**
 * @author WenChao
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterfaceInvokeRecord {
}

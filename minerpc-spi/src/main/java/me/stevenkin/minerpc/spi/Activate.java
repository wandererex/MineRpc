package me.stevenkin.minerpc.spi;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Activate {
    String[] value() default {};

    int order() default 0;
}

package io.github.davidchild.bitter.test.customdatasource.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TargetSource {
    /**
     * support more db datasource,that default MASTER db
     */
    public String value() default "MASTER";
}

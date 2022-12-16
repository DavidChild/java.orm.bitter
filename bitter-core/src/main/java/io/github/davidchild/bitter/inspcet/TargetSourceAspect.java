package io.github.davidchild.bitter.inspcet;

import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.github.davidchild.bitter.annotation.TargetSource;
import io.github.davidchild.bitter.connection.ThreadLocalForDataSource;
import io.github.davidchild.bitter.tools.CoreStringUtils;

@Aspect
@Order(1)
@Component
public class TargetSourceAspect {

    @Pointcut(value = "@annotation(io.github.davidChild.bitter.annotation.TargetSource)")
    public void dsPointCut() {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        TargetSource dataSource = getDataSource(point);
        if (CoreStringUtils.isNotNull(dataSource)) {
            ThreadLocalForDataSource.setDataSourceType(dataSource.value());
        }
        try {
            return point.proceed();
        } finally {
            // Destroy the data source after executing the method
            ThreadLocalForDataSource.clearDataSourceType();
        }
    }

    /**
     * get the switched data source
     */
    public TargetSource getDataSource(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        TargetSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), TargetSource.class);
        if (Objects.nonNull(dataSource)) {
            return dataSource;
        }
        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), TargetSource.class);
    }
}

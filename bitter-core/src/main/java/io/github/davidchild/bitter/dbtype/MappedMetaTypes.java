package io.github.davidchild.bitter.dbtype;

import org.apache.ibatis.type.TypeHandler;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappedMetaTypes {
    /**
     * Returns jdbc types to map {@link TypeHandler}.
     *
     * @return jdbc types
     */
    MetaType[] value();

    /**
     * Returns whether map to jdbc null type.
     *
     * @return {@code true} if map, {@code false} if otherwise
     */
    boolean includeNullJdbcType() default false;
}

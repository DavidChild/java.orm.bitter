package io.github.davidchild.bitter.op.scope;

/**
 * @ClassName ThrowingConsumer * @Description TODO
 * @Author DavidChild
 * @Date 17:16 2022/8/31
 * @Description
 * @Version 1.0
 **/
@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    default void accept(final T e) {
        try {
            accept0(e);
        } catch (Throwable ex) {
            Throwing.sneakyThrow(ex);
        }
    }

    void accept0(T e) throws Throwable;

}
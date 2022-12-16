package io.github.davidchild.bitter.op.scope;

import com.sun.istack.internal.NotNull;

/**
 * @ClassName Throwing * @Description TODO
 * @Author DavidChild
 * @Date 17:17 2022/8/31
 * @Description
 * @Version 1.0
 **/
public class Throwing {
    private Throwing() {}

    @NotNull
    public static <T> Consumer<T> rethrow(@NotNull final ThrowingConsumer<T> consumer) {
        return consumer;
    }

    /**
     * The compiler sees the signature with the throws T inferred to a RuntimeException type, so it allows the unchecked
     * exception to propagate.
     *
     * http://www.baeldung.com/java-sneaky-throws
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public static <E extends Throwable> void sneakyThrow(@NotNull final Throwable ex) throws E {
        throw (E)ex;
    }
}

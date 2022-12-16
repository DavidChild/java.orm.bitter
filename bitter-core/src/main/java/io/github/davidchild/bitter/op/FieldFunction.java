package io.github.davidchild.bitter.op;

import java.io.Serializable;

import io.github.davidchild.bitter.BaseModel;

@FunctionalInterface
public interface FieldFunction<T extends BaseModel> extends Serializable {
    Object get(T source);
}

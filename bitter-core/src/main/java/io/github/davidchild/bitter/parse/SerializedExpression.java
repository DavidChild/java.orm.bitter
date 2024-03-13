package io.github.davidchild.bitter.parse;

import co.streamx.fluent.extree.expression.ExpressionVisitor;
import co.streamx.fluent.extree.expression.LambdaExpression;

import java.io.Serializable;


interface SerializedExpression<R> extends Serializable {

    default R sql(ExpressionVisitor<R> visitor) {
        return (R)LambdaExpression.parse(this).accept(visitor);
    }
}
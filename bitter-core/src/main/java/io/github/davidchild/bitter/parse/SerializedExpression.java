package io.github.davidchild.bitter.parse;

import co.streamx.fluent.extree.expression.ExpressionVisitor;
import co.streamx.fluent.extree.expression.LambdaExpression;

import java.io.Serializable;


interface SerializedExpression<R> extends Serializable {
    // default BitterWrapper sql() {
     //   return LambdaExpression.parse(this).accept(new BitterVisitor());
    //}

    default R sql(ExpressionVisitor<R> visitor) {
        return (R)LambdaExpression.parse(this).accept(visitor);
    }
}

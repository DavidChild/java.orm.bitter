package io.github.davidchild.bitter.parse;

import java.io.Serializable;

import com.trigersoft.jaque.expression.ExpressionVisitor;
import com.trigersoft.jaque.expression.LambdaExpression;

interface SerializedExpression<R> extends Serializable {
    default BitterWrapper sql() {
        return LambdaExpression.parse(this).accept(new BitterVisitor());
    }

    default R sql(ExpressionVisitor<R> visitor) {
        return (R)LambdaExpression.parse(this).accept(visitor);
    }
}

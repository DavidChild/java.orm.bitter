package io.github.davidchild.bitter.parse;

import co.streamx.fluent.extree.expression.*;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.parbag.ExecuteParBag;
import io.github.davidchild.bitter.tools.DateTimeUtils;
import lombok.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BitterVisitor implements ExpressionVisitor<BitterWrapper> {

    private final List<ConstantExpression> PARAMS = new ArrayList<>();
    private final BitterWrapper SQL = new BitterWrapper();
    private final String head;
    private final String tail;
    private Expression body;
    private Expression param;
    private List<DataValue> bitterFields;

    private DataValue keyInfo;

    public BitterVisitor() {
        this("`", "`");
    }

    public BitterVisitor(List<DataValue> fields) {
        this.bitterFields = fields;
        this.head = "`";
        this.tail = "`";

    }

    public BitterVisitor(@NonNull String head, @NonNull String tail) {
        this.head = head;
        this.tail = tail;
    }

    public BitterVisitor(List<DataValue> fields, DataValue keyInfo, @NonNull String head, @NonNull String tail) {
        this.head = head;
        this.tail = tail;
        this.bitterFields = fields;
        this.keyInfo = keyInfo;
    }

    private static String toSqlOp(int expressionType) {
        switch (expressionType) {
            case ExpressionType.BitwiseAnd:
                return "&";
            case ExpressionType.LogicalAnd:
                return "and";
            case ExpressionType.BitwiseOr:
                return "|";
            case ExpressionType.LogicalOr:
                return "or";
            case ExpressionType.LogicalNot:
                return "not";
            case ExpressionType.Equal:
                return "=";
            case ExpressionType.NotEqual:
                return "<>";
            case ExpressionType.GreaterThan:
                return ">";
            case ExpressionType.GreaterThanOrEqual:
                return ">=";
            case ExpressionType.LessThan:
                return "<";
            case ExpressionType.LessThanOrEqual:
                return "<=";
            case ExpressionType.Add:
                return "+";
            case ExpressionType.Subtract:
                return "-";
            case ExpressionType.Multiply:
                return "*";
            case ExpressionType.Divide:
                return "/";
            case ExpressionType.Modulo:
                return "%";
            case ExpressionType.Convert:
                return "";
            default:
                throw new RuntimeException("Unknown ExpressionType '" + expressionType + "'");
        }
    }

    @Override
    public BitterWrapper visit(BinaryExpression e) {
        boolean quote = e.getExpressionType() == ExpressionType.LogicalOr;

        if (quote) {
            SQL.getKey().append('(');
        }
        ; // fix the LogicalOr Bug for sql
        e.getFirst().accept(this);
        if (quote) {
            SQL.getKey().append(')');
        }
        ; // fix the LogicalOr Bug for sql
        SQL.getKey().append(' ').append(toSqlOp(e.getExpressionType())).append(' ');
        if (quote) {
            SQL.getKey().append('(');
        }
        ; // fix the LogicalOr Bug for sql
        e.getSecond().accept(this);
        if (quote) {
            SQL.getKey().append(')');
        } // fix the LogicalOr Bug for sql
        return SQL;

    }

    @Override
    public BitterWrapper visit(ConstantExpression e) {
        final Object value = e.getValue();
        if (value instanceof LambdaExpression) {
            return ((LambdaExpression<?>) value).getBody().accept(this);
        }
        SQL.getKey().append('?');
        if (value instanceof Date) {
            Object ret = DateTimeUtils.DATE_TIME.formatDate((Date) value);
            SQL.getValue().add(ret);
            return SQL;
        }
        if (value instanceof LocalTime) {
            Object ret = DateTimeUtils.TIME.format((TemporalAccessor) value);
            SQL.getValue().add(ret);
            return SQL;
        }
        if (value instanceof LocalDate) {
            Object ret = DateTimeUtils.DATE.format((TemporalAccessor) value);
            SQL.getValue().add(ret);
            return SQL;
        }
        if (value instanceof LocalDateTime) {
            Object ret = DateTimeUtils.DATE_TIME.format((TemporalAccessor) value);
            SQL.getValue().add(ret);
            return SQL;
        }
        Object ret = value.toString();
        SQL.getValue().add(ret);
        return SQL;
    }

    @Override
    public BitterWrapper visit(InvocationExpression e) {
        final Expression target = e.getTarget();
        if (target instanceof LambdaExpression<?>) {
            e.getArguments().stream().filter(x -> x instanceof ConstantExpression)
                    .forEach(x -> PARAMS.add((ConstantExpression) x));
        }
        if (target.getExpressionType() == ExpressionType.MethodAccess) {
            e.getArguments().stream().findFirst().ifPresent(x -> {
                param = x;
            });
        }
        return e.getTarget().accept(this);
    }

    @Override
    public BitterWrapper visit(LambdaExpression<?> e) {
        if (null == body && e.getBody() instanceof BinaryExpression) {
            this.body = e.getBody();
        }
        return e.getBody().accept(this);
    }

    @Override
    public BitterWrapper visit(DelegateExpression e) {
        return e.getDelegate().accept(this);
    }

    @Override
    public BitterWrapper visit(MemberExpression e) {
        final Member member = e.getMember();
        final Expression instance = e.getInstance();
        if (instance == null) {
            return invoke(member, e);
        }
        if (instance instanceof ParameterExpression) {
            String name = member.getName();
            name = name.replaceAll("^(get)", "");
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
            name = ExecuteParBag.getDbNameByInnerName(bitterFields, keyInfo, name);
            SQL.getKey().append(head).append(name).append(tail);
        }
        if (instance instanceof ConstantExpression) {
            param.accept(this);
            return invoke(member, instance);
        }
        if (instance instanceof InvocationExpression) {
            ((InvocationExpression) instance).getTarget().accept(this);
            return invoke(member, param);
        }
        return SQL;
    }

    @Override
    public BitterWrapper visit(ParameterExpression e) {
        PARAMS.get(e.getIndex()).accept(this);
        return SQL;
    }

    @Override
    public BitterWrapper visit(UnaryExpression e) {
        SQL.getKey().append(toSqlOp(e.getExpressionType())).append(' ');
        return e.getFirst().accept(this);
    }

    @Override
    public BitterWrapper visit(BlockExpression blockExpression) {
        return null;
    }

    @Override
    public BitterWrapper visit(NewArrayInitExpression newArrayInitExpression) {
        return null;
    }

    public void clear() {
        SQL.clear();
    }

    // --------------------------------------------------------------------------------------------

    private BitterWrapper invoke(final Member member, final Expression expression) {
        if (member instanceof Constructor<?>) {
            return doCtorOp(member, expression);
        } else if (member instanceof Method) {
            final Class<?> clz = member.getDeclaringClass();
            if (Date.class.isAssignableFrom(clz)) {
                return doDateOp(member, expression);
            } else if (TemporalAccessor.class.isAssignableFrom(clz)) {
                return doDateOp(member, expression);
            } else if (String.class.isAssignableFrom(clz)) {
                return doStrOp(member, expression);
            } else {
                return doMethodOp(member, expression);
            }
        } else {
            throw new RuntimeException("The parameter '" + expression.toString() + "' not supported.");
        }
    }

    private BitterWrapper doCtorOp(final Member member, final Expression expression) {
        final Class<?> clz = member.getDeclaringClass();
        try {
            Object value = clz.newInstance();
            return Expression.constant(value, clz).accept(this);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex.getMessage(), ex.getCause());
        }
    }

    private BitterWrapper doStrOp(final Member member, final Expression expression) {
        final String method = member.getName();
        switch (method) {
            case "equals"/* = '{0}' */:
                SQL.getKey().append(" = ");
                expression.accept(this);
                return SQL;
            case "startsWith"/* like '{0}%' */:
                SQL.getKey().append(" like ").append("concat(");
                expression.accept(this);
                SQL.getKey().append(",'%')");
                return SQL;
            case "endsWith"/* like '%{0}' */:
                SQL.getKey().append(" like ").append("concat(").append("'%',");
                expression.accept(this);
                SQL.getKey().append(")");
                return SQL;
            case "contains"/* like '%{0}%' */:
                SQL.getKey().append(" like ").append("concat(").append("'%',");
                expression.accept(this);
                SQL.getKey().append(",'%')");
                return SQL;
            default:
                return doMethodOp(member, expression);
        }
    }

    private BitterWrapper doDateOp(final Member member, final Expression expression) {
        final String method = member.getName();
        switch (method) {
            case "equals"/* = '{0}' */:
                SQL.getKey().append(" = ");
                expression.accept(this);
                return SQL;
            case "after"/* > '{0}' */:
            case "isAfter":
                SQL.getKey().append(" > ");
                expression.accept(this);
                return SQL;
            case "before"/* < '{0}' */:
            case "isBefore":
                SQL.getKey().append(" < ");
                expression.accept(this);
                return SQL;
            default:
                return doMethodOp(member, expression);
        }
    }

    private BitterWrapper doMethodOp(final Member member, final Expression expression) {
        int mod = member.getModifiers();
        if (Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
            try {
                final Class<?> clz = member.getDeclaringClass();
                Object value = clz.getMethod(member.getName()).invoke(null);
                return Expression.constant(value, clz).accept(this);
            } catch (ReflectiveOperationException ex) {
                throw new RuntimeException(ex.getMessage(), ex.getCause());
            }
        }
        throw new RuntimeException("The parameter '" + expression.toString() + "' not supported.");
    }

}

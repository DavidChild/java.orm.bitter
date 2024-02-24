package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.FieldFunction;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class SubWhereStatement {

    private String statement = "";

    private BaseQuery bq;

    private String field;

    private String op;

    private List<Object> args;

    private  Object defaultArg;

    private LinkedHashMap<String, Object> dynamics = new LinkedHashMap<String, Object>();

    private  SubWhereStatementEnum SubWhereStatementEnum;


    public SubWhereStatement(BaseQuery bq) {
        this.setBq(bq);
    }

    public SubWhereStatement eq(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.eq,defaultArg,arg);
    }

    public SubWhereStatement lt(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.lt,defaultArg,arg);
    }

    public SubWhereStatement leq(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.leq,defaultArg,arg);
    }

    public SubWhereStatement gt(String field,  Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.gt,arg,defaultArg);
    }
    public SubWhereStatement geq(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.geq,arg,defaultArg);
    }

    public SubWhereStatement  allLike(String field, Object defaultArg, Object arg) {
        toField(field);
        return join(SubWhereStatementEnum.allLike,arg,defaultArg);
    }

    public SubWhereStatement  preLike(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.preLike,arg,defaultArg);

    }

    public SubWhereStatement in(String field, List arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.in,arg,defaultArg);
    }
    public SubWhereStatement  endLike(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.endLike,arg,defaultArg);
    }
    public SubWhereStatement custom(String condition, Object ...args) {
        toField(field);
        this.setStatement(condition);
        List<Object> arg= Arrays.stream(args).collect(Collectors.toList());
        return join(SubWhereStatementEnum.custom,arg,null);
    }

    



    public <T extends BaseModel> SubWhereStatement eq(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.eq,defaultArg,arg);
    }

    public <T extends BaseModel> SubWhereStatement lt(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.lt,defaultArg,arg);
    }

    public <T extends BaseModel> SubWhereStatement leq(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.leq,defaultArg,arg);
    }

    public <T extends BaseModel> SubWhereStatement gt(FieldFunction<T> field,  Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.gt,arg,defaultArg);
    }
    public <T extends BaseModel> SubWhereStatement geq(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.geq,arg,defaultArg);
    }

    public <T extends BaseModel> SubWhereStatement  allLike(FieldFunction<T> field, Object defaultArg, Object arg) {
        toField(field);
        return join(SubWhereStatementEnum.allLike,arg,defaultArg);
    }

    public <T extends BaseModel> SubWhereStatement  preLike(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.preLike,arg,defaultArg);

    }

    public <T extends BaseModel> SubWhereStatement in(FieldFunction<T> field, List arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.in,arg,defaultArg);
    }
    public <T extends BaseModel> SubWhereStatement  endLike(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.endLike,arg,defaultArg);
    }

    private SubWhereStatement join(SubWhereStatementEnum SubWhereStatementEnum,  Object args,Object defaultArg) {
        if (SubWhereStatementEnum == SubWhereStatementEnum.custom) {
            this.setSubWhereStatementEnum(SubWhereStatementEnum);
            this.setDefaultArg(defaultArg);
            List list = (List) args;
            this.setArgs(list);

        } else if (SubWhereStatementEnum == SubWhereStatementEnum.in) {
            List list = (List) args;
            if ((list == null || list.size() < 1) && defaultArg == null) {
                this.setSubWhereStatement("");
            } else {
                this.setSubWhereStatementEnum(SubWhereStatementEnum);
                this.setDefaultArg(defaultArg);
                this.setArgs(list);
            }
        } else {
            this.setSubWhereStatementEnum(SubWhereStatementEnum);
            this.setDefaultArg(defaultArg);
            List<Object> list = new ArrayList<>();
            list.add(args);
            this.setArgs(list);
        }
        return this;
    }

    private SubWhereStatement toField(String field) {
        this.setField(field);
        return this;
    }

    private  <T extends BaseModel> SubWhereStatement toField(FieldFunction<T> field) {
        String filedName = this.getBq().getExecuteParBag().getDbFieldByInnerClassFiled(field);
        return toField(filedName);
    }
    public void setSubWhereStatement(String SubWhereStatement) {
        this.setStatement(SubWhereStatement);
    }
}

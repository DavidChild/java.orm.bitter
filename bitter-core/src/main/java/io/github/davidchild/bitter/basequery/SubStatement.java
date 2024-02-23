package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.FieldFunction;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class SubStatement {

    private String statement = "";

    private BaseQuery bq;

    private String field;

    private String op;

    private List<Object> args;

    private  Object defaultArg;

    private LinkedHashMap<String, Object> dynamics = new LinkedHashMap<String, Object>();

    private  SubStatementEnum subStatementEnum;


    public SubStatement(BaseQuery bq) {
        this.setBq(bq);
    }

    public SubStatement eq(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.eq,defaultArg,arg);
    }

    public SubStatement lt(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.lt,defaultArg,arg);
    }

    public SubStatement leq(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.leq,defaultArg,arg);
    }

    public SubStatement gt(String field,  Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.gt,arg,defaultArg);
    }
    public SubStatement geq(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.geq,arg,defaultArg);
    }

    public SubStatement  allLike(String field, Object defaultArg, Object arg) {
        toField(field);
        return join(SubStatementEnum.allLike,arg,defaultArg);
    }

    public SubStatement  preLike(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.preLike,arg,defaultArg);

    }

    public SubStatement in(String field, List arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.in,arg,defaultArg);
    }
    public SubStatement  endLike(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.endLike,arg,defaultArg);
    }
    public SubStatement custom(String condition, Object ...args) {
        toField(field);
        this.setStatement(condition);
        List<Object> arg= Arrays.stream(args).collect(Collectors.toList());
        return join(SubStatementEnum.custom,arg,null);
    }



    public <T extends BaseModel> SubStatement eq(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.eq,defaultArg,arg);
    }

    public <T extends BaseModel> SubStatement lt(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.lt,defaultArg,arg);
    }

    public <T extends BaseModel> SubStatement leq(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.leq,defaultArg,arg);
    }

    public <T extends BaseModel> SubStatement gt(FieldFunction<T> field,  Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.gt,arg,defaultArg);
    }
    public <T extends BaseModel> SubStatement geq(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.geq,arg,defaultArg);
    }

    public <T extends BaseModel> SubStatement  allLike(FieldFunction<T> field, Object defaultArg, Object arg) {
        toField(field);
        return join(SubStatementEnum.allLike,arg,defaultArg);
    }

    public <T extends BaseModel> SubStatement  preLike(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.preLike,arg,defaultArg);

    }

    public <T extends BaseModel> SubStatement in(FieldFunction<T> field, List arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.in,arg,defaultArg);
    }
    public <T extends BaseModel> SubStatement  endLike(FieldFunction<T> field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubStatementEnum.endLike,arg,defaultArg);
    }

    private SubStatement join(SubStatementEnum subStatementEnum,  Object args,Object defaultArg) {
        if (subStatementEnum == SubStatementEnum.custom) {
            this.setSubStatementEnum(subStatementEnum);
            this.setDefaultArg(defaultArg);
            List list = (List) args;
            this.setArgs(list);

        } else if (subStatementEnum == SubStatementEnum.in) {
            List list = (List) args;
            if ((list == null || list.size() < 1) && defaultArg == null) {
                this.setSubStatement("");
            } else {
                this.setSubStatementEnum(subStatementEnum);
                this.setDefaultArg(defaultArg);
                this.setArgs(list);
            }
        } else {
            this.setSubStatementEnum(subStatementEnum);
            this.setDefaultArg(defaultArg);
            List<Object> list = new ArrayList<>();
            list.add(args);
            this.setArgs(list);
        }
        return this;
    }

    private SubStatement toField(String field) {
        this.setField(field);
        return this;
    }

    private  <T extends BaseModel> SubStatement toField(FieldFunction<T> field) {
        String filedName = this.getBq().getExecuteParBag().getDbFieldByInnerClassFiled(field);
        return toField(filedName);
    }
    public void setSubStatement(String subStatement) {
        this.setStatement(subStatement);
    }
}

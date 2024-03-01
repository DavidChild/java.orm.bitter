package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SubWhereStatement {

    private String statement = "";

    private String field;

    private String op;

    private List<Object> args;

    private  Object defaultArg;

    private LinkedHashMap<String, Object> dynamics = new LinkedHashMap<String, Object>();

    private  SubWhereStatementEnum SubWhereStatementEnum;


    public SubWhereStatement eq(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.eq,arg,defaultArg);
    }

    public SubWhereStatement lt(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.lt,arg,defaultArg);
    }

    public SubWhereStatement leq(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.leq,arg,defaultArg);
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
        List<Object> arg;
        if(args != null && args.length>0){
            arg = Arrays.stream(args).collect(Collectors.toList());
        }else {
            arg = new ArrayList<>();
        }
        return join(SubWhereStatementEnum.custom, arg, null);


    }


    public <T extends BaseModel> SubWhereStatement neq(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.neq,arg,defaultArg);
    }


    public <T extends BaseModel> SubWhereStatement notIn(String field, Object arg,Object defaultArg) {
        toField(field);
        return join(SubWhereStatementEnum.notIn,arg,defaultArg);
    }


    private SubWhereStatement join(SubWhereStatementEnum SubWhereStatementEnum,  Object args,Object defaultArg) {
        if (SubWhereStatementEnum == SubWhereStatementEnum.custom) {
            this.setSubWhereStatementEnum(SubWhereStatementEnum);
            this.setDefaultArg(defaultArg);
            List list = (List) args;
            this.setArgs(list);

        } else if (SubWhereStatementEnum == SubWhereStatementEnum.in || SubWhereStatementEnum == SubWhereStatementEnum.notIn) {
            List list = (List) args;
            if ((list == null || list.size() < 1) && defaultArg == null) {
                this.setSubWhereStatement("");
                this.setSubWhereStatementEnum(SubWhereStatementEnum);
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

    public void setSubWhereStatement(String SubWhereStatement) {
        this.setStatement(SubWhereStatement);
    }
}

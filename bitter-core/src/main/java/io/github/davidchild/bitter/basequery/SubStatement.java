package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.parbag.ExecuteParBagPage;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubStatement {
    private String subStatement = "";

    private BaseQuery bq;

    private String field;

    private String op;


    public SubStatement(BaseQuery bq) {
        this.setBq(bq);
    }

    public SubStatement join(SubStatementEnum subStatementEnum, String defaultArg, String arg) {
        return joinForList(subStatementEnum, defaultArg, arg);
    }

    public SubStatement join(SubStatementEnum subStatementEnum, String defaultArg, Long arg) {
        return joinForList(subStatementEnum, defaultArg, arg);
    }

    public SubStatement join(SubStatementEnum subStatementEnum, String defaultArg, Integer arg) {
        return joinForList(subStatementEnum, defaultArg, arg);
    }


    private SubStatement joinForList(SubStatementEnum subStatementEnum, String defaultArg, Object arg) {
        List<Object> list = new ArrayList<>();
        if (arg != null) {
            list.add(arg);
        }
        return join(subStatementEnum, defaultArg, list);
    }

    public SubStatement join(SubStatementEnum subStatementEnum, String defaultArg, List args) {
        if ((args == null || args.size() <= 0) && defaultArg == null) {
            this.setSubStatement("");
            return this;
        }
        switch (subStatementEnum) {
            case IN:
                in(defaultArg, args);
                break;
            case ALLLIKE:
            case PRELIKE:
            case ENDLIKE:
                like(subStatementEnum, defaultArg, args);
                break;
            default:
                break;
        }
        return this;
    }


    private void like(SubStatementEnum subStatementEnum, String defaultArg, List<Object> args) {

        String uuid = UUID.randomUUID().toString();
        this.setOp("like");
        switch (subStatementEnum) {

            case ALLLIKE:
                this.setSubStatement("concat('%',?,'%')");
                break;
            case PRELIKE:
                this.setSubStatement("concat('',?,'%')");
                break;
            case ENDLIKE:
                this.setSubStatement("concat('%',?,'')");
                break;
            default:
                break;
        }
        ((ExecuteParBagPage) getBq().executeParBag).dynamics.put(uuid, args == null || args.size() == 0 || args.get(0) == null ? defaultArg : args.get(0));
    }

    public SubStatement toField(String field) {
        this.setField(field);
        return this;
    }

    private void in(String defaultArg, List<Object> args) {
        if ((args != null && args.size() <= 1) || ((args == null || args.size() == 0) && defaultArg != null)) {
            String uuid = UUID.randomUUID().toString();
            this.setOp("=");
            this.setSubStatement("?");
            ((ExecuteParBagPage) getBq().executeParBag).dynamics.put(uuid, (args != null && args.size() == 1) ? args.get(0) : defaultArg);
        } else {
            this.setOp("in");
            joinForIn("(", ")", ",", defaultArg, args);
        }
    }

    private SubStatement joinForIn(String prefix, String suffix, String split, String defaultSubStatement, List<Object> args) {
        String subStr = "";
        if (CoreStringUtils.isNotEmpty(prefix)) subStr = prefix;

        for (int i = 0; i < args.size(); i++) {
            String uuid = UUID.randomUUID().toString();
            if (i == args.size() - 1) {
                subStr += "?";
            } else {
                subStr += "?" + split;
            }
            ((ExecuteParBagPage) getBq().executeParBag).dynamics.put(uuid, args.get(i));
        }
        if (CoreStringUtils.isNotEmpty(suffix)) subStr += suffix;
        this.setSubStatement(subStr);
        return this;
    }


    private SubStatement join(String prefix, String suffix, String split, String defaultSubStatement, List<Object> args) {
        String subStr = "";
        if (CoreStringUtils.isNotEmpty(prefix)) subStr = prefix;
        if (args == null || args.size() <= 0) {
            if (CoreStringUtils.isNotEmpty(defaultSubStatement)) {
                String uuid = UUID.randomUUID().toString();
                subStr = prefix;
                subStr += "?";
                ((ExecuteParBagPage) getBq().executeParBag).dynamics.put(uuid, defaultSubStatement);

            }
        } else {
            for (int i = 0; i < args.size(); i++) {
                String uuid = UUID.randomUUID().toString();
                if (i == args.size() - 1) {
                    subStr += "?";
                } else {
                    subStr += "?" + split;
                }
                ((ExecuteParBagPage) getBq().executeParBag).dynamics.put(uuid, args.get(i));
            }
        }
        this.setSubStatement(subStr);
        return this;
    }

    public String getSubStatement() {
        return subStatement;
    }

    public void setSubStatement(String subStatement) {
        this.subStatement = subStatement;
    }

    private BaseQuery getBq() {
        return bq;
    }

    private void setBq(BaseQuery bq) {
        this.bq = bq;
    }

    private void setField(String field) {
        this.field = field;
    }

    public String getField() {
        return this.field;
    }

    public String getOp() {
        return op;
    }

    private void setOp(String op) {
        this.op = op;
    }
}

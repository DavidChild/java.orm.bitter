package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.parbag.*;
import io.github.davidchild.bitter.tools.StatementHandeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MySqlQuery extends BaseQuery {

    @Override
    protected void insertCommandText(boolean isOutIdentity) {
        super.insertCommandText(isOutIdentity);
        ExecuteParBagInsert bagPar = (ExecuteParBagInsert) this.getExecuteParBag();
        String fields = "";
        String values = "";
        for (DataValue filed : bagPar.getProperties()) {
            if (!filed.getIsIdentity()) {
                if (filed.getValue() != null) {
                    fields += filed.getDbName() + ',';
                    values += "?,";
                    StatementHandeUtil.setParamInBagContainer(this,filed.getValue());
                } else {
                    fields += filed.getDbName() + ',';
                    values += "null,";
                }
            }
        }
        bagPar.RemoveProperties();
        this.setCommandText(String.format("INSERT INTO %s (%s) VALUES (%s);", bagPar.getTableName(),
                fields.substring(0, fields.length() - 1), values.substring(0, values.length() - 1)));
    }

    @Override
    protected void bachInsert() {
        super.bachInsert();
        List<Insert> list = ((ExecuteParBachInsert) this.getExecuteParBag()).getList();
        if (list == null && list.size() <= 0)
            return;
        ExecuteParBagInsert bagPar = (ExecuteParBagInsert) list.get(0).getExecuteParBag();
        String fields = "";
        for (DataValue filed : bagPar.getProperties()) {
            if (!filed.getIsIdentity()) {
                fields += filed.getDbName() + ',';
            }

        }
        StringBuilder values = new StringBuilder(" ");
        for (BaseQuery q : list) {
            String value = "(";
            ExecuteParBagInsert bag = (ExecuteParBagInsert) q.getExecuteParBag();
            for (DataValue f : bag.getProperties())
                if (!f.getIsIdentity()) {
                    if (f.getValue() == null) {
                        value += "null,";
                    } else {
                        value += "?,";
                        StatementHandeUtil.setParamInBagContainer(this,f.getValue());
                    }

                }
            value = value.substring(0, value.length() - 1) + "),";
            values.append("\n");
            values.append(value);
            bag.RemoveProperties();
        }
        String command = values.toString();
        this.setCommandText(String.format("INSERT INTO %s (%s) VALUES %s;", bagPar.getTableName(),
                fields.substring(0, fields.length() - 1), command.substring(0, command.length() - 1)));

    }

    // create delete sql
    @Override
    protected void deleteCommandText() {
        super.deleteCommandText();
        ExecuteParBagDelete bagPar = (ExecuteParBagDelete) this.getExecuteParBag();
        StringBuilder whereSQL = new StringBuilder(); // delete condition
        IBagWhere whereBag = ((IBagWhere)this.getExecuteParBag());
        String where = WhereHandler.getWhere( this);
        this.setCommandText(String.format("%s%s;", "DELETE FROM " + bagPar.getTableName(),where));
        bagPar.RemoveProperties();

    }

    @Override
    protected void updateCommandText() {
        super.updateCommandText();
        ExecuteParBagUpdate bagPar = (ExecuteParBagUpdate) this.getExecuteParBag();
        StringBuilder updateSQL = new StringBuilder(); // update语句
        StringBuilder whereSQL = new StringBuilder(); // update条件语句
        updateSQL.append(" update  ").append(bagPar.getTableName()).append(" set");
        String update = UpdateColumnHandler.getUpdateColumnSet(this);
        String where = WhereHandler.getWhere( this);
        this.setCommandText(String.format("%s%s%s;", updateSQL,update, where));
        bagPar.RemoveProperties();
    }
    @Override
    protected void selectCommandText() {
        super.selectCommandText();
        ExecuteParBagSelect bagPar = (ExecuteParBagSelect) this.getExecuteParBag();
        StringBuilder selectSQL = new StringBuilder(); // 语句
        StringBuilder whereSQL = new StringBuilder(); // 条件语句
        StringBuilder orderSQL = new StringBuilder(); // 条件语句
        selectSQL.append("SELECT ");
        String columns = SelectColumnHandler.getColumn(this);
        selectSQL.append(columns);
        selectSQL.append(" FROM  " + bagPar.getTableName());
        String where = WhereHandler.getWhere(this);
        whereSQL.append(where);
        String order = OrderHandler.getOrder(this);
        orderSQL.append(order);
        if (bagPar.getTopSize() != null && bagPar.getTopSize() > 0) {
            this.setCommandText(String.format("%s%s%s%s", selectSQL, whereSQL, order, " LIMIT 0," + bagPar.getTopSize() + " "));
        } else {
            this.setCommandText(String.format("%s%s%s", selectSQL, whereSQL, order));
        }
        bagPar.RemoveProperties();
    }

    @Override
    protected void countCommandText() {
        super.selectCommandText();
        ExecuteParBagCount bagPar = (ExecuteParBagCount) this.getExecuteParBag();
        StringBuilder whereSQL = new StringBuilder(); // 条件语句
        String selectSQL = "SELECT COUNT(1) " + "FROM  " + bagPar.getTableName() + " ";
        String where = WhereHandler.getWhere(this);
        whereSQL.append(where);
        bagPar.RemoveProperties();
        this.setCommandText(String.format("%s%s;", selectSQL, whereSQL));
    }

    @Override
    protected void createScope() {
        super.createScope();
        this.setScopeCommands(new ArrayList<>());
        this.setScopeParams(new ArrayList<>());
        this.setScopeSuccess(((ExecuteParScope) this.getExecuteParBag()).getScopeResult());
        if (!((ExecuteParScope) this.getExecuteParBag()).getScopeResult()) {
            return;
        }
        List<BaseQuery> list = ((ExecuteParScope) this.getExecuteParBag()).getList();
        if (list == null && list.size() <= 0)
            return;
        for (BaseQuery q : list) {
            q.convert();
            this.getScopeCommands().add(q.getCommandText());
            if (q.getParameters() != null || q.getParameters().size() > 0) {
                this.getScopeParams().add(q.getParameters());
            } else {
                this.getScopeParams().add(new LinkedHashMap<>());
            }
        }
    }

    // create execute sql
    @Override
    protected void executeCommandText() {
        super.executeCommandText();
        ExecuteParBagExecute bagPar = (ExecuteParBagExecute) this.getExecuteParBag();
        this.setCommandText(String.format("%s;", bagPar.getCommandText()));
        bagPar.RemoveProperties();

    }

    @Override
    protected void querySelectCommandText() {
        super.executeCommandText();
        ExecuteParBagQuerySelect bagPar = (ExecuteParBagQuerySelect) this.getExecuteParBag();
        String where = WhereHandler.getWhere(this);
        String order = OrderHandler.getOrder(this);
        this.setCommandText(String.format("%s%s%s;", bagPar.getCommandText(), where,order));
        bagPar.RemoveProperties();
    }
    @Override
    protected void pageCommandText() {
        MyPageManage.selectPageData(this);
    }
    @Override
    protected void pageCountText() {

        MyPageManage.selectPageDataCount(this);
    }

}

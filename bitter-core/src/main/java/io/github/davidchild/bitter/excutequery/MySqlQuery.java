package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.parbag.*;
import io.github.davidchild.bitter.tools.StatementHandeUtil;

import java.util.ArrayList;
import java.util.List;

public class MySqlQuery extends BaseQuery {

    @Override
    protected void insertCommandText(boolean isOutIdentity) {
        super.insertCommandText(isOutIdentity);
        ExecuteParBagInsert bagPar = (ExecuteParBagInsert) this.executeParBag;
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
        this.commandText = String.format("INSERT INTO %s (%s) VALUES (%s);", bagPar.getTableName(),
                fields.substring(0, fields.length() - 1), values.substring(0, values.length() - 1));
    }

    @Override
    protected void bachInsert() {
        super.bachInsert();
        List<Insert> list = ((ExecuteParBachInsert) this.executeParBag).getList();
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
        this.commandText = String.format("INSERT INTO %s (%s) VALUES %s;", bagPar.getTableName(),
                fields.substring(0, fields.length() - 1), command.substring(0, command.length() - 1));

    }

    // create delete sql
    @Override
    protected void deleteCommandText() {
        super.deleteCommandText();
        ExecuteParBagDelete bagPar = (ExecuteParBagDelete) this.executeParBag;
        StringBuilder whereSQL = new StringBuilder(); // delete condition
        IBagWhere whereBag = ((IBagWhere)this.getExecuteParBag());
        String where = WhereHandler.getWhere( this);
        bagPar.RemoveProperties();
        this.commandText = String.format("%s%s;", "DELETE FROM " + bagPar.getTableName(),where);

    }

    @Override
    protected void updateCommandText() {
        super.updateCommandText();
        ExecuteParBagUpdate bagPar = (ExecuteParBagUpdate) this.executeParBag;
        StringBuilder updateSQL = new StringBuilder(); // update语句
        StringBuilder whereSQL = new StringBuilder(); // update条件语句
        updateSQL.append(" update  ").append(bagPar.getTableName()).append(" set ");
        String update = UpdateColumnHandler.getUpdateColumnSet(this);
        String where = WhereHandler.getWhere( this);
        bagPar.RemoveProperties();
        this.commandText = String.format("%s%s%;", updateSQL,update, where);
    }
    @Override
    protected void selectCommandText() {
        super.selectCommandText();
        ExecuteParBagSelect bagPar = (ExecuteParBagSelect) this.executeParBag;
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
            this.commandText = String.format("%s%s%s%s", selectSQL, whereSQL, order, " LIMIT 0," + bagPar.getTopSize() + " ");
        } else {
            this.commandText = String.format("%s%s%s", selectSQL, whereSQL, order);
        }
        bagPar.RemoveProperties();
    }

    @Override
    protected void countCommandText() {
        super.selectCommandText();
        ExecuteParBagCount bagPar = (ExecuteParBagCount) this.executeParBag;
        StringBuilder whereSQL = new StringBuilder(); // 条件语句
        String selectSQL = "SELECT COUNT(1) " + "FROM  " + bagPar.getTableName() + " ";
        String where = WhereHandler.getWhere(this);
        whereSQL.append(where);
        bagPar.RemoveProperties();
        this.commandText = String.format("%s%s;", selectSQL, whereSQL);
    }

    @Override
    protected void createScope() {
        super.createScope();
        this.scopeCommands = new ArrayList<>();
        this.scopeParams = new ArrayList<>();
        this.scopeSuccess = ((ExecuteParScope) this.executeParBag).getScopeResult();
        if (!((ExecuteParScope) this.executeParBag).getScopeResult()) {
            return;
        }
        List<BaseQuery> list = ((ExecuteParScope) this.executeParBag).getList();
        if (list == null && list.size() <= 0)
            return;
        for (BaseQuery q : list) {
            q.convert();
            this.getScopeCommands().add(q.getCommandText());
            if (q.getParameters() != null || q.getParameters().size() > 0) {
                this.getScopeParams().add(q.getParameters());
            } else {
                this.getScopeParams().add(new ArrayList<>());
            }
        }
    }

    // create execute sql
    @Override
    protected void executeCommandText() {
        super.executeCommandText();
        StringBuilder whereSQL = new StringBuilder();
        ExecuteParBagExecute bagPar = (ExecuteParBagExecute) this.executeParBag;
        String where = WhereHandler.getWhere(this);
        whereSQL.append(where);
        this.commandText = String.format("%s%s;", bagPar.getCommandText(), whereSQL);
        bagPar.RemoveProperties();

    }
    @Override
    protected void pageCommandText() {
        String where = WhereHandler.getWhere(this);
        String order = OrderHandler.getOrder(this);
        MyPageManage.selectPageData(this,where,order);
    }
    @Override
    protected void pageCountText() {
        String where = WhereHandler.getWhere(this);
        String order = OrderHandler.getOrder(this);
        MyPageManage.selectPageDataCount(this,where,order);
    }

}

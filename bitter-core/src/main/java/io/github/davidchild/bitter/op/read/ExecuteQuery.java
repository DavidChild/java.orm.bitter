package io.github.davidchild.bitter.op.read;

import java.sql.SQLException;
import java.util.*;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.bitterlist.BMap;
import io.github.davidchild.bitter.dbtype.KeyInfo;
import io.github.davidchild.bitter.parbag.ExecuteParBagExecute;

public class ExecuteQuery extends BaseQuery {

    private ExecuteParBagExecute bag;

    public ExecuteQuery(String commandText, Object... params) {
        executeParBag = new ExecuteParBagExecute();
        executeParBag.setExecuteEnum(ExecuteEnum.ExecuteQuery);
        ((ExecuteParBagExecute)executeParBag).setCommandText(commandText);
        ((ExecuteParBagExecute)executeParBag).setParaMap(new LinkedHashMap<>());
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach((v) -> {
                ((ExecuteParBagExecute)executeParBag).getParaMap().put(UUID.randomUUID().toString(), v);
            });
        }

    }

    protected <T extends BaseModel> ExecuteQuery(Class<? extends BaseModel> clazz) {
        executeParBag = new ExecuteParBagExecute();
        executeParBag.setExecuteEnum(ExecuteEnum.ExecuteQuery);
        ((ExecuteParBagExecute)executeParBag).setCommandText(commandText);
        ((ExecuteParBagExecute)executeParBag).setType(clazz);
        ((ExecuteParBagExecute)executeParBag).setParaMap(new LinkedHashMap<>());
    }

    /// <summary>
    /// 追加 where 1=1
    /// </summary>
    /// <returns></returns>
    public ExecuteQuery beginWhere(String beginWhere, Object... params) {
        StringBuilder strBuild = new StringBuilder(((ExecuteParBagExecute)executeParBag).getCommandText());
        strBuild.append(" where  " + beginWhere);
        ((ExecuteParBagExecute)executeParBag).setCommandText(strBuild.toString());
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach((v) -> {
                ((ExecuteParBagExecute)executeParBag).getParaMap().put(UUID.randomUUID().toString(), v);
            });
        }
        return this;
    }

    public ExecuteQuery andWhere(String andWhere, Object... params) {
        StringBuilder strBuild = new StringBuilder(((ExecuteParBagExecute)executeParBag).getCommandText());
        strBuild.append(" and (" + andWhere + ") ");
        ((ExecuteParBagExecute)executeParBag).setCommandText(strBuild.toString());
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach((v) -> {
                ((ExecuteParBagExecute)executeParBag).getParaMap().put(UUID.randomUUID().toString(), v);
            });
        }
        return this;
    }

    public ExecuteQuery addParams(Object... params) {
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach((v) -> {
                ((ExecuteParBagExecute)executeParBag).getParaMap().put(UUID.randomUUID().toString(), v);
            });
        }
        return this;
    }

    public BMap find() throws Exception {
        this.convert();
        List<Map<String, Object>> dt = this.getData();
        if (dt != null) {
            BMap blist = new BMap();
            dt.forEach(item -> {
                blist.add(item);
            });
            return blist;
        }
        return new BMap();
    }

    protected <T extends BaseModel> T queryById(Object id)
        throws SQLException, InstantiationException, IllegalAccessException {

        T data;
        String tableName = ((ExecuteParBagExecute)executeParBag).getTableName();
        KeyInfo keyInfo = ((ExecuteParBagExecute)executeParBag).getKeyInfo();
        ((ExecuteParBagExecute)executeParBag).getParaMap().put(UUID.randomUUID().toString(), id);
        String sql = String.format("select * from %s where %s = ?", tableName, keyInfo.getDbFieldName());
        ((ExecuteParBagExecute)executeParBag).setCommandText(sql);
        List<T> st = this.getDataList();
        if (st != null && st.size() > 0) {
            data = st.get(0);
        } else {
            data = (T)((ExecuteParBagExecute)executeParBag).getType().newInstance();
        }
        return data;

    }

}

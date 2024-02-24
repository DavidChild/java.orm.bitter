package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.StatementHandeUtil;
import io.github.davidchild.bitter.basequery.SubUpdateColumnStatement;
import io.github.davidchild.bitter.parbag.IBagUpdateColumn;

import java.util.ArrayList;
import java.util.List;
public class UpdateColumnHandler {
    private   static String getUpdateColumn(List<SubUpdateColumnStatement> updateColumnStatements,BaseQuery  query) {
        StringBuilder columns = new StringBuilder("SET ");
        if(!((IBagUpdateColumn)query.getExecuteParBag()).CheckedHaveColumn()) return  columns.append("1=1").toString();
        List<SubUpdateColumnStatement> subColumnStatements = ((IBagUpdateColumn)query.getExecuteParBag()).getUpdateColumnContain();
        if(subColumnStatements != null && subColumnStatements.size()>0){
            for(int i=0;i<subColumnStatements.size(); i++){
                String columnName = subColumnStatements.get(i).getColumnName();
                Object value = subColumnStatements.get(i).getColumnValue();
                if(subColumnStatements.get(i).getColumnName() != null && subColumnStatements.get(i).getColumnName()!= ""){
                    if(value != null){
                        columns.append(String.format("%s=?", (i!=subColumnStatements.size())? ( columnName +","):columnName));
                        StatementHandeUtil.setParamInBagContainer(query, value);
                    }else {
                        columns.append(String.format("%s=%s", columnName,(i!=subColumnStatements.size()) ? ("null"+","):"null"));
                    }
                }
            }
        }
        return columns.toString();
    }

    private static   <T extends BaseModel> String getUpdateColumnIns(BaseQuery  query) {
        T data = (T)query.getExecuteParBag().getInsData();
        List<SubUpdateColumnStatement> subUpdateColumnStatementList = new ArrayList<>();
        if (data != null) {
            query.getExecuteParBag().getProperties().forEach(field -> {
                if ((!field.getIsIdentity() || !field.getIsKey())) {
                    SubUpdateColumnStatement updateColumnStatement = new SubUpdateColumnStatement(query);
                    updateColumnStatement.column(field.getDbName(),field.getValue());
                    subUpdateColumnStatementList.add(updateColumnStatement);
                }
            });
        }
        return  getUpdateColumn(subUpdateColumnStatementList,query);
    }

    public  static  <T extends BaseModel> String getUpdateColumnSet(BaseQuery  query){
        IBagUpdateColumn bagUpdateColumn =  ((IBagUpdateColumn)query.getExecuteParBag());
        if(query.getExecuteParBag().getInsData() == null){
            return getUpdateColumn(bagUpdateColumn.getUpdateColumnContain(),query);
        }
        return getUpdateColumnIns(query);
    }



}

package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.StatementHandeUtil;
import io.github.davidchild.bitter.basequery.SubUpdateColumnStatement;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.parbag.IBagUpdateColumn;

import java.util.ArrayList;
import java.util.List;
public class UpdateColumnHandler {
    private   static <T extends  BaseModel> RunnerParam getUpdateColumn(List<SubUpdateColumnStatement> subColumnStatements) {
        RunnerParam runnerParam = new RunnerParam();
        StringBuilder columns = new StringBuilder(" ");
        if(subColumnStatements != null && subColumnStatements.size()>0){
            for(int i=0;i<subColumnStatements.size(); i++){
                String columnName = subColumnStatements.get(i).getColumnName();
                Object value = subColumnStatements.get(i).getColumnValue();
                if(subColumnStatements.get(i).getColumnName() != null && subColumnStatements.get(i).getColumnName()!= ""){
                    if(value != null){
                        columns.append(String.format("%s=%s",   columnName, ((i+1)!=subColumnStatements.size())?"?, ":"? "));
                        StatementHandeUtil.setRunnerParamContainer(runnerParam, value);
                    }else {
                        columns.append(String.format("%s=%s", columnName,((i+1)!=subColumnStatements.size()) ? ("null, "):"null "));
                    }
                }
            }
        }

        runnerParam.setCommand( columns.toString());
        return  runnerParam;
    }

    private static   <T extends BaseModel> RunnerParam getUpdateColumnIns(T data,List<DataValue> filedLists) {
        List<SubUpdateColumnStatement> subUpdateColumnStatementList = new ArrayList<>();
        if (data != null) {
            filedLists.forEach(field -> {
                if ((!field.getIsIdentity() && !field.getIsKey())) {
                    SubUpdateColumnStatement updateColumnStatement = new SubUpdateColumnStatement();
                    updateColumnStatement.column(field.getDbName(),field.getValue());
                    subUpdateColumnStatementList.add(updateColumnStatement);
                }
            });
        }
        return  getUpdateColumn(subUpdateColumnStatementList);
    }

    public  static  <T extends BaseModel> RunnerParam getUpdateColumnSet(T data,IBagUpdateColumn updateColumnBag,List<DataValue> filedLists){
        RunnerParam runnerParam = new RunnerParam();
        if(data == null){
            if(!(updateColumnBag.CheckedHaveColumn())) {
                runnerParam.setCommand("1=1");
              return  runnerParam;
            }
            return getUpdateColumn(updateColumnBag.getUpdateColumnContain());
        }
        return getUpdateColumnIns(data,filedLists);
    }



}

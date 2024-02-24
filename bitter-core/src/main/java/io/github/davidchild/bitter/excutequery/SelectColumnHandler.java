package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.SubColumnStatement;
import io.github.davidchild.bitter.parbag.IBagColumn;

import java.util.List;

public class SelectColumnHandler {
    public static  <T extends BaseModel> String getColumn(BaseQuery  query) {
        StringBuilder columns = new StringBuilder(" ");
        if(!((IBagColumn)query.getExecuteParBag()).CheckedHaveColumn()) return  columns.append(" * ").toString();
        List<SubColumnStatement> subColumnStatements = ((IBagColumn)query.getExecuteParBag()).getColumnContain();
        if(subColumnStatements != null && subColumnStatements.size()>0){
            for(int i=0;i<subColumnStatements.size(); i++){
                if(subColumnStatements.get(i).getColumnName() != null && subColumnStatements.get(i).getColumnName()!= ""){
                    columns.append(String.format("%s", (i!=subColumnStatements.size())? (subColumnStatements.get(i).getColumnName()+","):subColumnStatements.get(i).getColumnName()));
                }
            }
        }
        return columns.toString();
    }

}

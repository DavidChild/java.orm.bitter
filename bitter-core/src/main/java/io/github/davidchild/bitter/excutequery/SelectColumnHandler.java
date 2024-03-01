package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.SubColumnStatement;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.parbag.IBagColumn;

import java.util.List;

public class SelectColumnHandler {
    public static <T extends BaseModel> RunnerParam getColumn(IBagColumn bagColumn) {
        RunnerParam runnerParam = new RunnerParam();
        StringBuilder columns = new StringBuilder(" ");
        if (!(bagColumn).CheckedHaveColumn()) {
            runnerParam.setCommand(columns.append(" * ").toString());
            return runnerParam;
        }

        List<SubColumnStatement> subColumnStatements = bagColumn.getColumnContain();
        if (subColumnStatements != null && subColumnStatements.size() > 0) {
            for (int i = 0; i < subColumnStatements.size(); i++) {
                if (subColumnStatements.get(i).getColumnName() != null && subColumnStatements.get(i).getColumnName() != "") {
                    columns.append(String.format("%s", (i != subColumnStatements.size()) ? (subColumnStatements.get(i).getColumnName() + ",") : subColumnStatements.get(i).getColumnName()));
                }
            }
        }
        runnerParam.setCommand(columns.toString());
        return runnerParam;
    }

}

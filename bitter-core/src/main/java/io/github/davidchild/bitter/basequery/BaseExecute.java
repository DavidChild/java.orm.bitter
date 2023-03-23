package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.connection.DatabaseType;
import io.github.davidchild.bitter.parbag.ExecuteParBag;

import java.util.ArrayList;
import java.util.List;

public class BaseExecute {

    protected ExecuteParBag executeParBag;
    protected boolean scopeSuccess;
    // sql command text
    protected String commandText;
    protected List<String> scopeCommands;
    protected List<List<Object>> scopeParams;
    // sql parameters
    protected List<Object> parameters = new ArrayList<>();
    private DatabaseType dbType = DatabaseType.MySQL;

  
    public BaseExecute() {

    }

    public DatabaseType getDbType() {
        if (dbType == null)
            return DatabaseType.MySQL;
        return dbType;
    }

    public void setDbType(DatabaseType dbType) {
        this.dbType = dbType;
    }

    public List<String> getScopeCommands() {
        return this.scopeCommands;
    }

    public void setScopeCommands(List<String> commands) {
        this.scopeCommands = commands;
    }

    public void clearParameters() {
        if (this.parameters != null && this.parameters.size() > 0) {
            this.parameters.clear();
        }
    }

    public ExecuteParBag getExecuteParBag() {
        return executeParBag;
    }

    public void setExecuteParBag(ExecuteParBag executeParBag) {
        this.executeParBag = executeParBag;
    }

    public List<List<Object>> getScopeParams() {
        return scopeParams;
    }

    public void setScopeParams(List<List<Object>> scopeParams) {
        this.scopeParams = scopeParams;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public List<Object> getParameters() {
        return parameters;
    }

}

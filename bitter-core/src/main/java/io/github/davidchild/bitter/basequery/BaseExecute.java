package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.connection.DatabaseType;
import io.github.davidchild.bitter.connection.SessionDbType;
import io.github.davidchild.bitter.parbag.ExecuteParBag;

import java.util.LinkedHashMap;
import java.util.List;


public class BaseExecute  {

    private ExecuteParBag executeParBag = new ExecuteParBag();

    public ExecuteParBag getExecuteParBag() {
        return executeParBag;
    }

    public void setExecuteParBag(ExecuteParBag executeParBag) {
        this.executeParBag = executeParBag;
    }

    protected void setDbType(DatabaseType dbType) {
        this.dbType  = dbType;
    }

    private boolean scopeSuccess;
    protected boolean isScopeSuccess() {
        return scopeSuccess;
    }

    protected void setScopeSuccess(boolean scopeSuccess) {
        this.scopeSuccess = scopeSuccess;
    }
    private String commandText;

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }
    private List<String> scopeCommands;

    public List<String> getScopeCommands() {
        return this.scopeCommands;
    }

    protected void setScopeCommands(List<String> commands) {
        this.scopeCommands = commands;
    }
    private List<LinkedHashMap<String, Object>> scopeParams;
    public List<LinkedHashMap<String, Object>> getScopeParams() {
        return scopeParams;
    }

    protected void setScopeParams(List<LinkedHashMap<String, Object>> scopeParams) {
        this.scopeParams = scopeParams;
    }
    protected  DatabaseType dbType;

    public void setParameters(LinkedHashMap<String, Object>  parames){
          this.getExecuteParBag().setDynamics(parames);
    }
    public LinkedHashMap<String, Object> getParameters(){
      return this.getExecuteParBag().getDynamics();
    }




    protected BaseExecute() {

    }

    public DatabaseType getDbType() {
        if (dbType == null)
            return SessionDbType.getSessionDbType();
        return dbType;
    }

    public  void  resetParameters(LinkedHashMap<String, Object> params){
        this.getExecuteParBag().removeTheDynamics();
        this.getExecuteParBag().setDynamics(params);


    }



    public void clearParameters() {
        if (this.getExecuteParBag().getDynamics() != null && this.getExecuteParBag().getDynamics().size() > 0) {
            this.getExecuteParBag().getDynamics().clear();
        }
    }

    protected void dispose(){
        this.getExecuteParBag().dispose();
        this.getExecuteParBag().setDynamics(null);
        this.setScopeCommands(null);
        this.setScopeParams(null);

    }

    protected void disposeForData(){
    }



}

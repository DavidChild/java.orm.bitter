package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.connection.DatabaseType;
import io.github.davidchild.bitter.connection.SessionDbType;
import io.github.davidchild.bitter.parbag.ExecuteParBag;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class BaseExecute  {

    protected ExecuteParBag executeParBag;
    protected boolean scopeSuccess;
    protected String commandText;
    protected List<String> scopeCommands;
    protected List<List<Object>> scopeParams;
    private   DatabaseType dbType;

    // sql parameters
    protected   List<Object> parameters;
    public List<Object>  getParameters(){
        if(parameters == null) {
            parameters = new ArrayList<>();
            if(this.getExecuteParBag().getDynamics() !=null&&this.getExecuteParBag().getDynamics().size()>0 ){
                this.getExecuteParBag().getDynamics().forEach((k,v)->{
                    parameters.add(v);
                });
            }
        }
        return  parameters;
    }

    public BaseExecute() {

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
        if (this.getExecuteParBag().getDynamics() != null && this.getExecuteParBag().getDynamics().size() > 0) {
            this.getExecuteParBag().getDynamics().clear();
        }
    }
}

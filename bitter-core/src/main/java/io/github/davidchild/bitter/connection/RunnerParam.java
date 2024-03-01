package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.basequery.ExecuteMode;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class RunnerParam {
    public  String command = "";
    public LinkedHashMap<String, Object>  objectParams = new LinkedHashMap<>();
    public boolean isOutIdentity = false;
    public ExecuteMode mode  = ExecuteMode.Cached;

    public void append(RunnerParam runnerParam){
        if(runnerParam != null){
            this.command  += runnerParam.getCommand();
            if(runnerParam.getObjectParams() != null && runnerParam.getObjectParams().size() >0){
                this.objectParams.putAll(runnerParam.getObjectParams());
            }
        }
    }

    public void appendOnlyParams(RunnerParam runnerParam){
        if(runnerParam != null&& runnerParam.getObjectParams() != null && runnerParam.getObjectParams().size() >0){
            this.objectParams.putAll(runnerParam.getObjectParams());
        }
    }

    public void appendOnlyParams(LinkedHashMap<String, Object> params){
        if(params != null && params.size() >0){
            this.objectParams.putAll(params);
        }
    }

}

package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.basequery.ExecuteMode;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class RunnerParam {
    public  String command;
    public LinkedHashMap<String, Object>  objectParams;
    public boolean isIdentity;
    public ExecuteMode mode;
}

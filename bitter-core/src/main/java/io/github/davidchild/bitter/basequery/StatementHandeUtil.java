package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.connection.RunnerParam;

import java.util.UUID;

public class StatementHandeUtil {
    public static void setRunnerParamContainer(RunnerParam runnerParam, Object value){
        runnerParam.getObjectParams().put(UUID.randomUUID().toString(),value);
    }
}

package io.github.davidchild.bitter.connection;

import java.util.List;

public interface IDbStatement {

    Object Query(RunnerParam runnerParam,DataResultHandlerBase resultHandler);

    long Insert(RunnerParam runnerParam);

    long update(RunnerParam runnerParam);

    long execute(RunnerParam runnerParam);

    long executeBach(RunnerParam runnerParam);

    long executeScope(List<RunnerParam> runnerParamList);

}
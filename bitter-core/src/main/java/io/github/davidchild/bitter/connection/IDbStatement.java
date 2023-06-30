package io.github.davidchild.bitter.connection;

import java.util.List;

public interface IDbStatement {

    Object Query(String commandTest, List<Object> params,DataResultHandlerBase resultHandler);

    long Insert(String commandTest, List<Object> params, boolean isIdentity);

    long update(String commandTest, List<Object> params);

    long execute(String commandTest, List<Object> params);

    long executeBach(String commandTest, List<Object> params);

    long executeScope(List<String> commandTests, List<List<Object>> params);

}
package io.github.davidchild.bitter.connection;

import java.util.LinkedHashMap;
import java.util.List;

public interface IDbStatement {

    Object Query(String commandTest, LinkedHashMap<String, Object> params,DataResultHandlerBase resultHandler);

    long Insert(String commandTest, LinkedHashMap<String, Object> params, boolean isIdentity);

    long update(String commandTest, LinkedHashMap<String, Object> params);

    long execute(String commandTest, LinkedHashMap<String, Object> params);

    long executeBach(String commandTest, LinkedHashMap<String, Object> params);

    long executeScope(List<String> commandTests, List<LinkedHashMap<String, Object>> params);

}
package io.github.davidchild.bitter.connection.Impl;

import java.util.List;
import java.util.Map;

public interface IDbStatement {

    List<Map<String, Object>> queryMapFromDbData(String commandTest, List<Object> params, IIETyeConvert convert);

    long Insert(String commandTest, List<Object> params, boolean isIdentity);

    long update(String commandTest, List<Object> params);

    long execute(String commandTest, List<Object> params);

    long executeBach(String commandTest, List<Object> params);

    long executeScope(List<String> commandTests, List<List<Object>> params);

}
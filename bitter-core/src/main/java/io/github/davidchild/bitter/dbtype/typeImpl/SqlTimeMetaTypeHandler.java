/*
 *    Copyright 2009-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package io.github.davidchild.bitter.dbtype.typeImpl;

import io.github.davidchild.bitter.dbtype.MetaType;
import io.github.davidchild.bitter.dbtype.TypeHanderDealBase;

import java.sql.*;



public class SqlTimeMetaTypeHandler extends TypeHanderDealBase<Time> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Time parameter, MetaType jdbcType) throws SQLException {
    ps.setTime(i, parameter);
  }

  @Override
  public Time getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return rs.getTime(columnName);
  }

  @Override
  public Time getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return rs.getTime(columnIndex);
  }

  @Override
  public Time getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return cs.getTime(columnIndex);
  }
}

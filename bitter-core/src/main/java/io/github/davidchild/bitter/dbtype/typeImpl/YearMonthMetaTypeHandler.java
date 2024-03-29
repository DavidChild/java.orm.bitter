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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;


public class YearMonthMetaTypeHandler extends TypeHanderDealBase<YearMonth> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, YearMonth yearMonth, MetaType jt) throws SQLException {
    ps.setString(i, yearMonth.toString());
  }

  @Override
  public YearMonth getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String value = rs.getString(columnName);
    return value == null ? null : YearMonth.parse(value);
  }

  @Override
  public YearMonth getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String value = rs.getString(columnIndex);
    return value == null ? null : YearMonth.parse(value);
  }

  @Override
  public YearMonth getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String value = cs.getString(columnIndex);
    return value == null ? null : YearMonth.parse(value);
  }

}

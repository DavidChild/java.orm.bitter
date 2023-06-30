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
import java.time.Instant;

public class InstantMetaTypeHandler extends TypeHanderDealBase<Instant> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Instant parameter, MetaType jdbcType)
      throws SQLException {
    ps.setTimestamp(i, Timestamp.from(parameter));
  }

  @Override
  public Instant getNullableResult(ResultSet rs, String columnName) throws SQLException {
    Timestamp timestamp = rs.getTimestamp(columnName);
    return getInstant(timestamp);
  }

  @Override
  public Instant getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    Timestamp timestamp = rs.getTimestamp(columnIndex);
    return getInstant(timestamp);
  }

  @Override
  public Instant getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    Timestamp timestamp = cs.getTimestamp(columnIndex);
    return getInstant(timestamp);
  }

  private static Instant getInstant(Timestamp timestamp) {
    if (timestamp != null) {
      return timestamp.toInstant();
    }
    return null;
  }
}

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

import java.io.Reader;
import java.sql.*;


public class ClobReaderMetaTypeHandler extends TypeHanderDealBase<Reader> {


  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Reader parameter, MetaType jdbcType)
      throws SQLException {
    ps.setClob(i, parameter);
  }


  @Override
  public Reader getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return toReader(rs.getClob(columnName));
  }


  @Override
  public Reader getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return toReader(rs.getClob(columnIndex));
  }


  @Override
  public Reader getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return toReader(cs.getClob(columnIndex));
  }

  private Reader toReader(Clob clob) throws SQLException {
    if (clob == null) {
      return null;
    }
    return clob.getCharacterStream();
  }

}

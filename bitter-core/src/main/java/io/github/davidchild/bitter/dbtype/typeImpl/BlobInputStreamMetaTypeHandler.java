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

import java.io.InputStream;
import java.sql.*;

public class BlobInputStreamMetaTypeHandler extends TypeHanderDealBase<InputStream> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, InputStream parameter, MetaType jdbcType)
      throws SQLException {ps.setBlob(i, parameter);
  }


  @Override
  public InputStream getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return toInputStream(rs.getBlob(columnName));
  }


  @Override
  public InputStream getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return toInputStream(rs.getBlob(columnIndex));
  }


  @Override
  public InputStream getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return toInputStream(cs.getBlob(columnIndex));
  }

  private InputStream toInputStream(Blob blob) throws SQLException {
    if (blob == null) {
      return null;
    }
    return blob.getBinaryStream();
  }

}

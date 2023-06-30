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


public class ByteObjectArrayMetaTypeHandler extends TypeHanderDealBase<Byte[]> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Byte[] parameter, MetaType jdbcType)
      throws SQLException {
    ps.setBytes(i, MetaByteArrayUtils.convertToPrimitiveArray(parameter));
  }

  @Override
  public Byte[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
    byte[] bytes = rs.getBytes(columnName);
    return getBytes(bytes);
  }

  @Override
  public Byte[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    byte[] bytes = rs.getBytes(columnIndex);
    return getBytes(bytes);
  }

  @Override
  public Byte[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    byte[] bytes = cs.getBytes(columnIndex);
    return getBytes(bytes);
  }

  private Byte[] getBytes(byte[] bytes) {
    Byte[] returnValue = null;
    if (bytes != null) {
      returnValue = MetaByteArrayUtils.convertToObjectArray(bytes);
    }
    return returnValue;
  }

}

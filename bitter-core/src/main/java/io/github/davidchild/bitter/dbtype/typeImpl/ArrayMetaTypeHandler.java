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
import io.github.davidchild.bitter.dbtype.MetaTypeException;
import io.github.davidchild.bitter.dbtype.TypeHanderDealBase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

public class ArrayMetaTypeHandler extends TypeHanderDealBase<Object> {

  private static final ConcurrentHashMap<Class<?>, String> STANDARD_MAPPING;

  static {
    STANDARD_MAPPING = new ConcurrentHashMap<>();
    STANDARD_MAPPING.put(BigDecimal.class, MetaType.NUMERIC.name());
    STANDARD_MAPPING.put(BigInteger.class, MetaType.BIGINT.name());
    STANDARD_MAPPING.put(boolean.class, MetaType.BOOLEAN.name());
    STANDARD_MAPPING.put(Boolean.class, MetaType.BOOLEAN.name());
    STANDARD_MAPPING.put(byte[].class, MetaType.VARBINARY.name());
    STANDARD_MAPPING.put(byte.class, MetaType.TINYINT.name());
    STANDARD_MAPPING.put(Byte.class, MetaType.TINYINT.name());
    STANDARD_MAPPING.put(Calendar.class, MetaType.TIMESTAMP.name());
    STANDARD_MAPPING.put(java.sql.Date.class, MetaType.DATE.name());
    STANDARD_MAPPING.put(java.util.Date.class, MetaType.TIMESTAMP.name());
    STANDARD_MAPPING.put(double.class, MetaType.DOUBLE.name());
    STANDARD_MAPPING.put(Double.class, MetaType.DOUBLE.name());
    STANDARD_MAPPING.put(float.class, MetaType.REAL.name());
    STANDARD_MAPPING.put(Float.class, MetaType.REAL.name());
    STANDARD_MAPPING.put(int.class, MetaType.INTEGER.name());
    STANDARD_MAPPING.put(Integer.class, MetaType.INTEGER.name());
    STANDARD_MAPPING.put(LocalDate.class, MetaType.DATE.name());
    STANDARD_MAPPING.put(LocalDateTime.class, MetaType.TIMESTAMP.name());
    STANDARD_MAPPING.put(LocalTime.class, MetaType.TIME.name());
    STANDARD_MAPPING.put(long.class, MetaType.BIGINT.name());
    STANDARD_MAPPING.put(Long.class, MetaType.BIGINT.name());
    STANDARD_MAPPING.put(OffsetDateTime.class, MetaType.TIMESTAMP_WITH_TIMEZONE.name());
    STANDARD_MAPPING.put(OffsetTime.class, MetaType.TIME_WITH_TIMEZONE.name());
    STANDARD_MAPPING.put(Short.class, MetaType.SMALLINT.name());
    STANDARD_MAPPING.put(String.class, MetaType.VARCHAR.name());
    STANDARD_MAPPING.put(Time.class, MetaType.TIME.name());
    STANDARD_MAPPING.put(Timestamp.class, MetaType.TIMESTAMP.name());
    STANDARD_MAPPING.put(URL.class, MetaType.DATALINK.name());
  }

  public ArrayMetaTypeHandler() {
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, MetaType jdbcType)
      throws SQLException {
    if (parameter instanceof Array) {
      // it's the user's responsibility to properly free() the Array instance
      ps.setArray(i, (Array) parameter);
    } else {
      if (!parameter.getClass().isArray()) {
        throw new MetaTypeException(
            "ArrayType Handler requires SQL array or java array parameter and does not support type "
                + parameter.getClass());
      }
      Class<?> componentType = parameter.getClass().getComponentType();
      String arrayTypeName = resolveTypeName(componentType);
      Array array = ps.getConnection().createArrayOf(arrayTypeName, (Object[]) parameter);
      ps.setArray(i, array);
      array.free();
    }
  }

  protected String resolveTypeName(Class<?> type) {
    return STANDARD_MAPPING.getOrDefault(type, MetaType.JAVA_OBJECT.name());
  }

  @Override
  public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return extractArray(rs.getArray(columnName));
  }

  @Override
  public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return extractArray(rs.getArray(columnIndex));
  }

  @Override
  public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return extractArray(cs.getArray(columnIndex));
  }

  protected Object extractArray(Array array) throws SQLException {
    if (array == null) {
      return null;
    }
    Object result = array.getArray();
    array.free();
    return result;
  }

}

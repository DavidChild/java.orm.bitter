package io.github.davidchild.bitter.connection.Impl;

import java.lang.reflect.Field;
import java.time.*;
import java.util.Date;

public class MySqlTypeConvert implements IIETyeConvert {
    @Override
    public Object convertDbToJavaTypeValue(Field field, Object dbValue) {
        String typeName = field.getType().getName();
        if (typeName.toLowerCase().contains("java.lang.long")) {
            if (dbValue instanceof Integer) {
                if (dbValue != null)
                    return Long.parseLong(dbValue.toString());
            }
            if (dbValue instanceof Long) {
                if (dbValue != null)
                    return Long.parseLong(dbValue.toString());
            }
        }

        // deal data
        if (typeName.toLowerCase().contains("java.util.date")) {
            if (dbValue instanceof LocalDateTime) {
                Instant instant = ((LocalDateTime)dbValue).toLocalDate().atTime(LocalTime.MIDNIGHT)
                    .atZone(ZoneId.systemDefault()).toInstant();
                return Date.from(instant);
            } else if (dbValue instanceof LocalDate) {
                Instant instant =
                    ((LocalDate)dbValue).atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant();
                return Date.from(instant);
            } else if (dbValue instanceof String) {
                LocalDate localDate = LocalDate.parse((String)dbValue);
                Instant instant =
                    ((LocalDate)dbValue).atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant();
                return Date.from(instant);
            } else if (dbValue instanceof Long) {
                // todo
            }

        }
        return dbValue;
    }

    @Override
    public Object convertJavaToDbTypeValue(Field field, Object javaTypeValue) {
        return javaTypeValue;
    }
}

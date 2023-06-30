package io.github.davidchild.bitter.dbtype;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface TypeHandlerBase<T> {

    void setParameter(PreparedStatement ps, int i, T parameter, MetaType jdbcType) throws SQLException;

    default void setObjectParameter(PreparedStatement ps, int i, Object parameter, MetaType jdbcType) throws SQLException{
        setParameter(ps,i,(T)parameter,jdbcType);
    }

    T getResult(ResultSet rs, String columnName) throws SQLException;

    T getResult(ResultSet rs, int columnIndex) throws SQLException;

    T getResult(CallableStatement cs, int columnIndex) throws SQLException;

}


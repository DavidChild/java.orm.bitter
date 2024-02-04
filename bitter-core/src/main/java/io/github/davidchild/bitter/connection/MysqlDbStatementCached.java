package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.dbtype.MetaTypeCt;
import io.github.davidchild.bitter.exception.DataSourceException;
import io.github.davidchild.bitter.exception.DbException;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import io.github.davidchild.bitter.tools.CoreStringUtils;
import io.github.davidchild.bitter.tools.JsonUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MysqlDbStatementCached implements IDbStatement {


    @Override
    public Object Query(String commandTest, List<Object> params,DataResultHandlerBase resultHandler) {
        BitterLogUtil.logWriteSql(commandTest, params);
         Object result = null;
        try (DbConnection db = new DbConnection()) {
            try (PreparedStatement stmt = db.connection.prepareStatement(commandTest)) {
                if (CoreStringUtils.isNotEmpty(params)) {
                    for (int i = 0; i < params.size(); i++) {
                        MetaTypeCt.getTypeHandler(params.get(i).getClass()).setObjectParameter(stmt,i+1,params.get(i),null);
                       //stmt.setObject(i + 1, params.get(i),null);
                    }
                }
                if (!stmt.execute())
                    return result;
                try{
                    try (ResultSet rs = stmt.getResultSet()) {
                        if (rs != null) {
                             result = resultHandler.GetResult(rs);
                            }
                        }
                    } catch (SQLException e) {
                        BitterLogUtil.logWriteError(e, params);
                    }
                catch (Exception ex){
                    BitterLogUtil.logWriteError(ex,params);
                }
            } catch (DbException | SQLException e) {
                BitterLogUtil.logWriteError(e, params);
            }
          } catch (DataSourceException e) {
            BitterLogUtil.logWriteError(e, params);
        }
        return result;
    }

    public long Insert(String commandTest, List<Object> params, boolean isIdentity) {
        BitterLogUtil.logWriteSql(commandTest, params);
        long id = -1L;
        try (DbConnection db = new DbConnection()) {
            if (isIdentity) {
                try (PreparedStatement stmt =
                             db.connection.prepareStatement(commandTest, Statement.RETURN_GENERATED_KEYS)) {
                    if (CoreStringUtils.isNotEmpty(params)) {
                        for (int i = 0; i < params.size(); i++) {
                            MetaTypeCt.getTypeHandler(params.get(i).getClass()).setObjectParameter(stmt,i+1,params.get(i),null);
                            //stmt.setObject(i + 1, params.get(i));
                        }
                    }
                    if (stmt.executeUpdate() > 0) {
                        try (ResultSet rs = stmt.getGeneratedKeys()) {
                            if (rs != null) {
                                while (rs.next()) {
                                    id = rs.getInt(1);
                                }
                            }

                        } catch (SQLException e) {
                            BitterLogUtil.logWriteError(e, params);
                        }
                    }

                } catch (DbException | SQLException e) {
                    BitterLogUtil.logWriteError(e, params);
                }
            } else {
                try (PreparedStatement stmt = db.connection.prepareStatement(commandTest)) {
                    if (CoreStringUtils.isNotEmpty(params)) {
                        for (int i = 0; i < params.size(); i++) {
                            stmt.setObject(i + 1, params.get(i));
                        }
                    }
                    int aff = stmt.executeUpdate();
                    if (aff > 0)
                        id = 1L;
                } catch (DbException | SQLException e) {
                    BitterLogUtil.logWriteError(e, params);
                }
            }
        } catch (DataSourceException e) {
            BitterLogUtil.logWriteError(e, params);
        }
        return id;
    }

    // create bach insert sql statement
    @Override
    public long executeBach(String commandTest, List<Object> params) {
        BitterLogUtil.logWriteSql(commandTest, params);
        long aff = 1L;
        try (DbConnection db = new DbConnection()) {
            {
                try (PreparedStatement stmt = db.connection.prepareStatement(commandTest)) {
                    db.connection.setAutoCommit(false);
                    for (int i = 0; i < params.size(); i++) {
                        MetaTypeCt.getTypeHandler(params.get(i).getClass()).setObjectParameter(stmt,i+1,params.get(i),null);
//                        stmt.setObject(i + 1, params.get(i));
                    }
                    stmt.addBatch();
                    db.connection.commit();
                } catch (DataSourceException e) {
                    db.connection.rollback();
                    BitterLogUtil.logWriteError(e, params);
                    aff = -1L;
                }
            }

        } catch (Exception e) {
            aff = -1L;
            BitterLogUtil.logWriteError(e, params);
        }
        return aff;
    }

    @Override
    public long executeScope(List<String> commandTests, List<List<Object>> params) {
        BitterLogUtil.logWriteSql(JsonUtil.object2String(commandTests), JsonUtil.object2String(params));
        long aff = 1L;
        try (DbConnection db = new DbConnection()) {
            try {
                db.connection.setAutoCommit(false);
                int k = 0;
                for (String command : commandTests) {

                    try (PreparedStatement stmt = db.connection.prepareStatement(command)) {
                        List<Object> list = params.get(k);
                        for (int i = 0; i < list.size(); i++) {
                            MetaTypeCt.getTypeHandler(params.get(i).getClass()).setObjectParameter(stmt,i+1,params.get(i),null);
                           // stmt.setObject(i + 1, list.get(i));
                        }
                        stmt.executeUpdate();
                    }
                    k++;
                }
                db.connection.commit();
            } catch (Exception e) {
                try {
                    if (!db.connection.isClosed()) {
                        db.connection.rollback();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                aff = -1L;
                BitterLogUtil.logWriteError(e, JsonUtil.object2String(params));
            }
        } catch (DataSourceException e) {
            aff = -1L;
            BitterLogUtil.logWriteError(e, JsonUtil.object2String(params));
        }
        return aff;
    }

    public long update(String commandTest, List<Object> params) {
        BitterLogUtil.logWriteSql(commandTest, params);
        long aff = -1L;
        try (DbConnection db = new DbConnection()) {
            try (PreparedStatement stmt = db.connection.prepareStatement(commandTest)) {
                if (CoreStringUtils.isNotEmpty(params)) {
                    for (int i = 0; i < params.size(); i++) {
                        MetaTypeCt.getTypeHandler(params.get(i).getClass()).setObjectParameter(stmt,i+1,params.get(i),null);
                        //stmt.setObject(i + 1, params.get(i));
                    }
                }
                int affTemp = stmt.executeUpdate();
                aff = Long.parseLong(Integer.valueOf(affTemp).toString());

            } catch (DbException | SQLException e) {
                BitterLogUtil.logWriteError(e, params);
            }
        } catch (DataSourceException e) {
            BitterLogUtil.logWriteError(e, params);
        }
        return aff;
    }

    public long execute(String commandTest, List<Object> params) {
        BitterLogUtil.logWriteSql(commandTest, params);
        long aff = -1L;
        try (DbConnection db = new DbConnection()) {
            try (PreparedStatement stmt = db.connection.prepareStatement(commandTest)) {
                if (CoreStringUtils.isNotEmpty(params)) {
                    for (int i = 0; i < params.size(); i++) {
                        MetaTypeCt.getTypeHandler(params.get(i).getClass()).setObjectParameter(stmt,i+1,params.get(i),null);
                      //  stmt.setObject(i + 1, params.get(i));
                    }
                }
                stmt.executeUpdate();
                aff = 1L;
            } catch (DbException | SQLException e) {
                BitterLogUtil.logWriteError(e, params);
            }
        } catch (DataSourceException e) {
            BitterLogUtil.logWriteError(e, params);
        }
        return aff;
    }

}

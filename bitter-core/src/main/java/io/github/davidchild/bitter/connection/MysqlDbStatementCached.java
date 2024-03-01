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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MysqlDbStatementCached implements IDbStatement {


    @Override
    public Object Query(RunnerParam runnerParam,DataResultHandlerBase resultHandler) {
        BitterLogUtil.logWriteSql(runnerParam.getCommand(), runnerParam.getObjectParams());
         Object result = null;
        try (DbConnection db = new DbConnection()) {
            try (PreparedStatement stmt = db.connection.prepareStatement(runnerParam.getCommand())) {
                if (CoreStringUtils.isNotEmpty(runnerParam.getObjectParams())) {
                    int i = 0;
                    for (Map.Entry<String,Object> entry : runnerParam.getObjectParams().entrySet()) {
                        MetaTypeCt.getTypeHandler(entry.getValue().getClass()).setObjectParameter(stmt,i+1,entry.getValue(),null);
                        i++;
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
                        BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
                    throw  new DbException(e.getMessage());
                    }
                catch (Exception ex){
                    BitterLogUtil.logWriteError(ex,runnerParam.getObjectParams());
                    throw  new DbException(ex.getMessage());
                }
            } catch (DbException | SQLException e) {
                BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
                throw  new DbException(e.getMessage());
            }
          } catch (DataSourceException e) {
            BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
            throw  new DbException(e.getMessage());
        }
        return result;
    }



    public long Insert(RunnerParam runnerParam) {
        BitterLogUtil.logWriteSql(runnerParam.getCommand(), runnerParam.getObjectParams());
        long id = -1L;
        try (DbConnection db = new DbConnection()) {
            if (runnerParam.isOutIdentity) {
                try (PreparedStatement stmt =
                             db.connection.prepareStatement(runnerParam.getCommand(), Statement.RETURN_GENERATED_KEYS)) {
                    if (CoreStringUtils.isNotEmpty(runnerParam.getObjectParams())) {
                        int i = 0;
                        for (Map.Entry<String,Object> entry : runnerParam.getObjectParams().entrySet()) {
                            MetaTypeCt.getTypeHandler(entry.getValue().getClass()).setObjectParameter(stmt,i+1,entry.getValue(),null);
                            i++;
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
                            BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
                        }
                    }

                } catch (DbException | SQLException e) {
                    BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
                }
            } else {
                try (PreparedStatement stmt = db.connection.prepareStatement(runnerParam.getCommand())) {
                    if (CoreStringUtils.isNotEmpty(runnerParam.getObjectParams())) {
                        int i = 0;
                        for (Map.Entry<String,Object> entry : runnerParam.getObjectParams().entrySet()) {
                            MetaTypeCt.getTypeHandler(entry.getValue().getClass()).setObjectParameter(stmt,i+1,entry.getValue(),null);
                            i++;
                        }
                    }
                    int aff = stmt.executeUpdate();
                    if (aff > 0)
                        id = 1L;
                } catch (DbException | SQLException e) {
                    BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
                }
            }
        } catch (DataSourceException e) {
            BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
        }
        return id;
    }

    // create bach insert sql statement
    @Override
    public long executeBach(RunnerParam runnerParam) {
        BitterLogUtil.logWriteSql(runnerParam.getCommand(), runnerParam.getObjectParams());
        long aff = 1L;
        try (DbConnection db = new DbConnection()) {
            {
                try (PreparedStatement stmt = db.connection.prepareStatement(runnerParam.getCommand())) {
                    db.connection.setAutoCommit(false);
                    int i = 0;
                    for (Map.Entry<String,Object> entry : runnerParam.getObjectParams().entrySet()) {
                        MetaTypeCt.getTypeHandler(entry.getValue().getClass()).setObjectParameter(stmt,i+1,entry.getValue(),null);
                        i++;
                    }
                    stmt.addBatch();
                    db.connection.commit();
                } catch (DataSourceException e) {
                    db.connection.rollback();
                    BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
                    aff = -1L;
                }
            }

        } catch (Exception e) {
            aff = -1L;
            BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
        }
        return aff;
    }

    @Override
    public long executeScope(List<RunnerParam> runnerParamList) {
        BitterLogUtil.logWriteSql(JsonUtil.object2String(runnerParamList));
        long aff = 1L;
        try (DbConnection db = new DbConnection()) {
            try {
                db.connection.setAutoCommit(false);
                int k = 0;
                for (RunnerParam runnerParam : runnerParamList) {

                    try (PreparedStatement stmt = db.connection.prepareStatement(runnerParam.getCommand())) {
                        LinkedHashMap<String, Object> list = runnerParam.getObjectParams();
                        int i = 0;
                        for (Map.Entry<String,Object> entry : list.entrySet()) {
                            MetaTypeCt.getTypeHandler(entry.getValue().getClass()).setObjectParameter(stmt,i+1,entry.getValue(),null);
                            i++;
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
                BitterLogUtil.logWriteError(e, JsonUtil.object2String(runnerParamList));
            }
        } catch (DataSourceException e) {
            aff = -1L;
            BitterLogUtil.logWriteError(e, JsonUtil.object2String(runnerParamList));
        }
        return aff;
    }

    public long update(RunnerParam runnerParam) {
        BitterLogUtil.logWriteSql(runnerParam.getCommand(), runnerParam.getObjectParams());
        long aff = -1L;
        try (DbConnection db = new DbConnection()) {
            try (PreparedStatement stmt = db.connection.prepareStatement(runnerParam.getCommand())) {
                if (CoreStringUtils.isNotEmpty(runnerParam.getObjectParams())) {
                    int i = 0;
                    for (Map.Entry<String,Object> entry : runnerParam.getObjectParams().entrySet()) {
                        MetaTypeCt.getTypeHandler(entry.getValue().getClass()).setObjectParameter(stmt,i+1,entry.getValue(),null);
                        i++;
                    }
                }
                int affTemp = stmt.executeUpdate();
                aff = Long.parseLong(Integer.valueOf(affTemp).toString());

            } catch (DbException | SQLException e) {
                BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
            }
        } catch (DataSourceException e) {
            BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
        }
        return aff;
    }

    public long execute(RunnerParam runnerParam) {
        BitterLogUtil.logWriteSql(runnerParam.getCommand(), runnerParam.getObjectParams());
        long aff = -1L;
        try (DbConnection db = new DbConnection()) {
            try (PreparedStatement stmt = db.connection.prepareStatement(runnerParam.getCommand())) {
                if (CoreStringUtils.isNotEmpty(runnerParam.getObjectParams())) {
                    int i = 0;
                    for (Map.Entry<String,Object> entry : runnerParam.getObjectParams().entrySet()) {
                        MetaTypeCt.getTypeHandler(entry.getValue().getClass()).setObjectParameter(stmt,i+1,entry.getValue(),null);
                        i++;
                    }
                }
                stmt.executeUpdate();
                aff = 1L;
            } catch (DbException | SQLException e) {
                BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
            }
        } catch (DataSourceException e) {
            BitterLogUtil.logWriteError(e, runnerParam.getObjectParams());
        }
        return aff;
    }

}

package io.github.davidchild.bitter.parbag;

import com.baomidou.mybatisplus.annotation.IdType;
import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.connection.DatabaseType;
import io.github.davidchild.bitter.connection.SessionDbType;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.exception.DbException;
import io.github.davidchild.bitter.op.FieldFunction;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import io.github.davidchild.bitter.tools.CoreUtils;
import io.github.davidchild.bitter.tools.FieldLamdUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface  IBagOp {

    ExecuteParBag getParBag();

    public default DatabaseType getDbType() {
        return SessionDbType.getSessionDbType();
    }

    public default ExecuteEnum getBehaviorEnum() {
        return this.getParBag().getExecuteEnum();
    }

    public default void setExecuteEnum(ExecuteEnum executeEnum) {
        this.getParBag().setExecuteEnum(executeEnum);
    }

    public default ExecuteMode getExecuteMode() {
        if (this.getParBag().getModeExecute() == null)
            this.getParBag().setModeExecute(ExecuteMode.Cached);
        return this.getParBag().getModeExecute();
    }

    public default void setExecuteMode(ExecuteMode executeMode) {
        this.getParBag().setModeExecute(executeMode);
    }

    public default void setModelType(Class<?> type) {
        this.getParBag().setCurrentModelType((Class<? extends BaseModel>) type);
    }

    public default Class<?> getModelType() {
        if (this.getParBag().getInsData() == null && this.getParBag().getCurrentModelType() == null) {
            return null;
        }
        if (this.getParBag().getInsData() != null) {
            this.getParBag().setCurrentModelType(this.getParBag().getInsData().getClass());
        }
        return this.getParBag().getCurrentModelType();
    }

    public default String getTableName() {
        if (this.getModelType() != null) {
            if (this.getParBag().getExecuteTableName() == null || this.getParBag().getExecuteTableName().isEmpty()) {
                this.getParBag().setExecuteTableName(CoreUtils.getTableNameByType(this.getParBag().getCurrentModelType()));
            }
        }
        if (this.getParBag().getExecuteTableName() != null) {
            return this.getParBag().getExecuteTableName().trim();
        }
        return null;
    }

    public default LinkedHashMap<String, Object> getDynamicParams() {
        return this.getParBag().getDynamics();
    }

    public default void setTableName(String tableName) throws DbException {
        if (tableName == null || tableName.isEmpty()) {
            throw new DbException("table name will be not null.");
        }
        this.getParBag().setExecuteTableName(tableName);
    }

    public default <T extends BaseModel> String getDbFiled(FieldFunction<T> innerNameFn) {
        try {
            String innerName = FieldLamdUtils.convertToFieldName(innerNameFn);
            return CoreUtils.getDbNameByInnerName(this.getProperties(), this.getKeyInfo(), innerName);

        } catch (Exception ex) {
            BitterLogUtil.getInstance().error("FieldFunction<T> enter error,pls enter filed  same as : TStudent::getName; pls  check your enter the parm.", ex);
            return null;
        }

    }

    public default boolean getIsIdentity() {
        if (getKeyInfo() != null && getKeyInfo().getDbIdType() == IdType.AUTO)
            return true;
        else
            return false;
    }


    public default DataValue getKeyInfo() {
        if (this.getModelType() != null) {
            if (this.getParBag().getExecuteKeyInfo() == null) {
                DataValue keyInfo = CoreUtils.getTypeKey(this.getModelType(), this.getData());
                this.getParBag().setExecuteKeyInfo(keyInfo);
            }
        }
        return this.getParBag().getExecuteKeyInfo();
    }

    public default List<DataValue> getProperties() {

        if (this.getModelType() != null) {
            if (this.getParBag().getDvs() == null) {
                this.getParBag().setDvs(CoreUtils.getTypeRelationData(this.getModelType(), this.getData()));
            }
            return this.getParBag().getDvs();
        }
        return new ArrayList<>();
    }


    public default boolean getIsOutIdentity() {
        if (getKeyInfo() != null && this.getKeyInfo().getDbIdType() == IdType.AUTO)
            return true;
        else
            return false;
    }

    public default void dispose() {
        this.getParBag().dispose();
    }

    public default BaseModel getData() {
        return this.getParBag().getInsData();
    }

    public default void setData(BaseModel data) {
        this.getParBag().setInsData(data);
    }
}

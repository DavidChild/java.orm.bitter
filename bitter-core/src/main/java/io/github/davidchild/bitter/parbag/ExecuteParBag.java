package io.github.davidchild.bitter.parbag;

import com.baomidou.mybatisplus.annotation.IdType;
import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.exception.DbException;
import io.github.davidchild.bitter.op.FieldFunction;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import io.github.davidchild.bitter.tools.CoreStringUtils;
import io.github.davidchild.bitter.tools.CoreUtils;
import io.github.davidchild.bitter.tools.FieldLamdUtils;

import java.util.ArrayList;
import java.util.List;

public class ExecuteParBag {

    public ExecuteMode executeMode;
    List<DataValue> properties;
    List<String> identityFields;
    private ExecuteEnum executeEnum;
    private String tableName;
    private Class<? extends BaseModel> modelType;
    private DataValue keyInfo;
    private boolean isIdentityModel;
    private BaseModel data;

    public static String getDbNameByInnerName(List<DataValue> list, DataValue keyInfo, String innerFieldName) {

        if (keyInfo != null && CoreStringUtils.isNotEmpty(keyInfo.getDbName())
                && keyInfo.getClassInnerName().toLowerCase().equals(innerFieldName)) {
            return keyInfo.getDbName();
        }
        String dBFieldName = "";
        if (list != null && list.size() > 0) {
            for (DataValue item : list) {
                if (item.getClassInnerName().toLowerCase().equals(innerFieldName.toLowerCase())) {
                    dBFieldName = item.getDbName();
                    break;
                }
            }
        }
        return dBFieldName;
    }

    public ExecuteEnum getExecuteEnum() {
        return executeEnum;
    }

    public void setExecuteEnum(ExecuteEnum executeEnum) {
        this.executeEnum = executeEnum;
    }

    public ExecuteMode getExecuteMode() {
        if (executeMode == null)
            return ExecuteMode.Cached;
        return executeMode;
    }

    public void setExecuteMode(ExecuteMode executeMode) {
        this.executeMode = executeMode;
    }

    public Class<?> getType() {
        return modelType;
    }

    public void setType(Class<?> type) {
        this.modelType = (Class<? extends BaseModel>) type;
    }

    public Class<?> getModelType() {
        if (data == null && modelType == null) {
            return null;
        }
        if (data != null) {
            modelType = data.getClass();

        }
        return modelType;
    }

    public String getTableName() {
        if (this.getModelType() != null) {
            if (this.tableName == null || this.tableName.isEmpty()) {
                tableName = CoreUtils.getTableNameByType(modelType);
            }
        }
        return tableName.trim();
    }

    public void setTableName(String tableName) throws DbException {
        if (tableName == null || tableName.isEmpty()) {
            throw new DbException("table name will be not null.");
        }
        this.tableName = tableName;
    }

    public <T extends BaseModel> String getDbFieldByInnerClassFiled(FieldFunction<T> innerNameFn) {
        try {
            String innerName = FieldLamdUtils.convertToFieldName(innerNameFn);
            return getDbNameByInnerName(this.getProperties(), this.getKeyInfo(), innerName);

        } catch (Exception ex) {
            BitterLogUtil.getInstance().error("FieldFunction<T> enter error,pls enter filed  same as : TStudent::getName; pls  check your enter the parm.", ex);
            return null;
        }

    }

    public boolean getIsIdentity() {
        if (getKeyInfo() != null && getKeyInfo().getDbIdType() == IdType.AUTO)
            return true;
        else
            return false;
    }

    public DataValue getKeyInfo() {
        if (this.getModelType() != null) {
            if (keyInfo == null) {
                keyInfo = CoreUtils.getTypeKey(this.getModelType(), this.data);
            }
        }
        return keyInfo;
    }

    public List<DataValue> getProperties() {

        if (this.getModelType() != null) {
            if (properties == null) {
                properties = CoreUtils.getTypeRelationData(this.modelType, this.data);
            }
            return properties;
        }
        return new ArrayList<>();
    }

    public void RemoveProperties() {
        if (this.properties != null) {
            this.properties = null; // 优化内存,及时把 bag 中的PROPERTIES 对象设置为null.
        }
    }

    public BaseModel getData() {
        return data;
    }

    public void setData(BaseModel data) {
        this.data = data;
    }

}

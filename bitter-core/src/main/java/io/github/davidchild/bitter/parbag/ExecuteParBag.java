package io.github.davidchild.bitter.parbag;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.dbtype.FieldProperty;
import io.github.davidchild.bitter.dbtype.KeyInfo;
import io.github.davidchild.bitter.exception.DbException;
import io.github.davidchild.bitter.op.FieldFunction;
import io.github.davidchild.bitter.tools.CoreStringUtils;
import io.github.davidchild.bitter.tools.CoreUtils;
import io.github.davidchild.bitter.tools.FieldLamdUtils;

public class ExecuteParBag {

    public ExecuteMode executeMode;
    List<FieldProperty> properties;
    List<String> identityFields;
    private ExecuteEnum executeEnum;
    private String tableName;
    private Class<? extends BaseModel> modelType;
    private KeyInfo keyInfo;
    private boolean isIdentityModel;
    private BaseModel data;

    public static String getDbNameByInnerName(List<FieldProperty> list, KeyInfo keyInfo, String innerFieldName) {

        if (keyInfo != null && CoreStringUtils.isNotEmpty(keyInfo.getFieldName())
            && keyInfo.getFieldName().toLowerCase().equals(innerFieldName)) {
            return keyInfo.getDbFieldName();
        }
        String dBFieldName = "";
        if (list != null && list.size() > 0) {
            for (FieldProperty item : list) {
                if (item.getClassInnerFieldName().toLowerCase().equals(innerFieldName.toLowerCase())) {
                    dBFieldName = item.getDbFieldName();
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

        this.modelType = (Class<? extends BaseModel>)type;
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
        String innerName = FieldLamdUtils.convertToFieldName(innerNameFn);
        return getDbNameByInnerName(this.getProperties(), this.getKeyInfo(), innerName);
    }

    public boolean getIsIdentity() {
        if (getKeyInfo() != null && getKeyInfo().getDbIdType() == IdType.AUTO)
            return true;
        else
            return false;
    }

    public KeyInfo getKeyInfo() {
        if (this.getModelType() != null) {
            if (keyInfo == null) {
                keyInfo = CoreUtils.getPrimaryField(this.getModelType(), this.data);
            }
        }
        return keyInfo;
    }

    public List<FieldProperty> getProperties() {

        if (this.getModelType() != null) {
            if (properties == null) {
                properties = CoreUtils.getAllFields(this.modelType, this.data);
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

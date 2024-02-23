package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.dbtype.DataValue;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;



@Data
public class ExecuteParBag {

    public  ExecuteMode modeExecute;
    private List<DataValue> dvs;
    private List<String> identityFields;
    private ExecuteEnum executeEnum;
    private String executeTableName;
    private Class<? extends BaseModel> currentModelType;
    private DataValue executeKeyInfo;
    private boolean isIdentityModel;
    private BaseModel insData;

    /** 共享封装参数 ***/
    //动态参数
    private LinkedHashMap<String, Object> dynamics = new LinkedHashMap<String, Object>();

    public void  dispose(){
        this.dynamics = null;
        this.dvs = null;
        this.identityFields = null;
        this.currentModelType = null;
        this.executeEnum = null;
    }
}

package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.ISingleQuery;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.parbag.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Data
public class BuildParams {


    /** update/delete/select **/
    private BaseModel data;
    private List<DataValue> filedProperties;
    private List<List<DataValue>> batchData;
    private DataValue keyInfo;
    private String tableName;
    private IBagOp bagOp;
    private int topSize;
    private  String executeCommand;
    private LinkedHashMap<String,Object> objectParams;

    /** SCOPE**/
    private List<BaseQuery> scopeList = new ArrayList<>();
    private Boolean scopeResult = false;

    /** page **/
    private int pageIndex;
    private int pageSize;
    private String pageTable;
    private String pageColumn;
    private String preWith;
    private String pageCommand;
    private Boolean isPage = false;

    public  BuildParams(ISingleQuery query) {
        if (query.getBagOp().getBehaviorEnum() == ExecuteEnum.Scope) {
            this.scopeList = ((IScopeBag) query.getBagOp()).getScopeList();
            this.scopeResult = ((IScopeBag) query.getBagOp()).getScopeResult();

        } else {
            this.bagOp =  query.getBagOp();
            this.filedProperties = bagOp.getProperties();
            if (bagOp.getBehaviorEnum() == ExecuteEnum.BachInsert) {
                batchData = ((IBachInsertBag) (query.getBagOp())).getBachData();
            } else if (bagOp.getBehaviorEnum() == ExecuteEnum.Select) {
                topSize = ((ITopSizeBag) bagOp).getTopSize();
            }
            else if (bagOp.getBehaviorEnum() == ExecuteEnum.ExecuteQuerySelect || bagOp.getBehaviorEnum() == ExecuteEnum.ExecuteQuery) {
                this.executeCommand = ((ICommandBag)bagOp).getCommandText();
            }
            else if (bagOp.getBehaviorEnum() == ExecuteEnum.PageQuery) {
                this.pageTable = ((IPageBag) (bagOp)).getPageTable();
                this.pageColumn = ((IPageBag) (bagOp)).getPageColumn();
                this.pageIndex = ((IPageBag) (bagOp)).getPageIndex();
                this.pageSize =((IPageBag) (bagOp)).getPageSize();
                this.pageCommand = ((ICommandBag) (bagOp)).getCommandText();
                this.isPage =((IPageBag) (bagOp)).getIsPage();
            }
            this.objectParams = bagOp.getDynamicParams();
            this.data = bagOp.getData();
            this.keyInfo = bagOp.getKeyInfo();
            if (bagOp.getBehaviorEnum() == ExecuteEnum.PageQuery||bagOp.getBehaviorEnum() == ExecuteEnum.PageCount) {
                this.tableName = ((IPageBag)bagOp).getPageTable();
            }else{
                this.tableName = bagOp.getTableName();
            }
        }
    }
}

package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.basequery.SubColumnStatement;
import io.github.davidchild.bitter.basequery.SubOrderStatement;
import io.github.davidchild.bitter.tools.CoreStringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ExecuteParBagPage  extends ExecuteParBag implements IBagWhere,IBagOrder,IPageBag,ICommandBag {
    private String preWith;
    private String commandText = "";
    private Integer pageIndex = 0;
    private Integer pageSize = 10;
    private String pageColumns;
    private Boolean isPage = false;
    private String lowerCommandText;

    private WhereContainer whereCondition = new WhereContainer();
    private List<SubOrderStatement> orders = new ArrayList<>();
    private List<SubColumnStatement> selectColumns = new ArrayList<>();

    @Override
    public ExecuteParBag getParBag() {
        return this;
    }


    private String getLowerCommandText() {
        if (CoreStringUtils.isEmpty(lowerCommandText)) {
            lowerCommandText = this.getCommandText().toLowerCase();
        }
        return lowerCommandText;
    }


    @Override
    public WhereContainer getWhereContainer() {
        return this.getWhereCondition();
    }

    @Override
    public List<SubOrderStatement> getOrderContain() {
        return this.getOrders();
    }

    @Override
    public String getPageTable() {
        if (getLowerCommandText().indexOf("]") > -1) {
            int indexFrom = getLowerCommandText().indexOf(" from", getLowerCommandText().indexOf("]"));
            return getCommandText().substring(indexFrom + 5);
        } else {
            return getCommandText()
                    .substring((getCommandText().toLowerCase().indexOf(" from") + 5));
        }
    }

    @Override
    public String getPageColumn() {
        if (getLowerCommandText().indexOf("]") > -1) {
            int indexFrom = getLowerCommandText().indexOf(" from", getLowerCommandText().indexOf("]"));
            return getCommandText().substring(
                    getLowerCommandText().indexOf("select") + 6, indexFrom - getLowerCommandText().indexOf("select") - 5);
        } else {
            return getCommandText()
                    .substring(getLowerCommandText().indexOf("select") + 6, (getLowerCommandText().indexOf("select") + 6)
                            + getLowerCommandText().indexOf(" from") - getLowerCommandText().indexOf("select") - 5);
        }
    }

}

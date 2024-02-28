package io.github.davidchild.bitter.parbag;

import lombok.Data;


@Data
public class ExecuteParBagPage extends ExecuteParBagSelect {
    private String preWith;
    private String commandText = "";
    private Integer pageIndex = 0;
    private Integer pageSize = 10;
    private String pageColumns;
    private Boolean isPage = false;
}

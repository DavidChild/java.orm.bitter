package io.github.davidchild.bitter.parbag;

public interface IPageBag {
    public Integer getPageIndex();
    public Integer getPageSize();
    public String getPageTable();
    public String getPageColumn();
    public String getPreWith();

    public Boolean getIsPage();
}

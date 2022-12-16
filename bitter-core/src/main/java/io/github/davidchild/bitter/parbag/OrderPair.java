package io.github.davidchild.bitter.parbag;

public class OrderPair {

    private OrderBy orderBy;
    private String orderName;

    public OrderPair(OrderBy orderBy, String orderName) {
        this.orderBy = orderBy;
        this.orderName = orderName;

    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}

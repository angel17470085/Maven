package PojoEcommerce;

import java.util.List;

public class Orders
{
    public List<OrderDetail> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetail> orders) {
        this.orders = orders;
    }

    private List<OrderDetail> orders;


}

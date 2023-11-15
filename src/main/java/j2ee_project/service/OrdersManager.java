package j2ee_project.service;

import j2ee_project.model.order.OrderStatus;
import j2ee_project.model.order.Orders;
import j2ee_project.model.user.Customer;
import jakarta.servlet.http.HttpServletResponse;

public class OrdersManager {
    public static String checkOrder(Orders order, Customer customer) {
        if(customer == null) {
            return "You need to be logged in with a customer account";
        }

        if(!order.getCustomer().equals(customer)) {
            return "You can't pay the order of another customer";
        }

        if(order.getOrderStatus() != OrderStatus.WAITING_PAYMENT) {
            return "This order has already been paid";
        }

        return null;
    }
}

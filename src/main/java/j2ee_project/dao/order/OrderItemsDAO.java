package j2ee_project.dao.order;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.Discount;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.order.OrderItem;
import j2ee_project.model.order.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.HashSet;
import java.util.Set;

public class OrderItemsDAO {
    public static void addFromCart(Cart cart, Orders order) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Cart cartDBObj = entityManager.find(Cart.class, cart.getId());
            Orders orderDBObj = entityManager.find(Orders.class, order.getId());

            System.out.println(cartDBObj);

            if (cartDBObj == null || orderDBObj == null) {
                //TODO: throw error
                return;
            }


            float cartPricePercentage = 1;
            if (cart.getDiscount() != null) {
                cartPricePercentage = (1 - ((float) cart.getDiscount().getDiscountPercentage() / 100));
            }



            for (CartItem item : cartDBObj.getCartItems()) {
                OrderItem newItem = new OrderItem();
                newItem.setProduct(item.getProduct());
                newItem.setQuantity(item.getQuantity());
                newItem.setOrder(orderDBObj);

                float itemPrice = item.getQuantity() * item.getProduct().getUnitPrice();
                Discount categoryDiscount = item.getProduct().getCategory().getDiscount();
                if (categoryDiscount != null) {
                    itemPrice *= (1 - ((float) categoryDiscount.getDiscountPercentage() / 100));
                }
                itemPrice *= cartPricePercentage;

                newItem.setTotal(itemPrice);
                orderDBObj.getOrderItems().add(newItem);
            }
            transaction.commit();
        } catch(Exception e) {
            System.err.println("OrdersItem could not be added from the cart "+e);
        } finally {
            entityManager.close();
        }
    }
}

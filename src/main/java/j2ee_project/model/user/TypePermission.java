package j2ee_project.model.user;

public enum TypePermission {
    CAN_MANAGE_CUSTOMER("Can manage customers","manage-customers-permission"),
    CAN_MANAGE_MODERATOR("Can manage moderators","manage-moderators-permission"),
    CAN_MANAGE_PRODUCT("Can manage products","manage-products-permission"),
    CAN_MANAGE_CATEGORY("Can manage categories","manage-categories-permission"),
    CAN_MANAGE_ORDER("Can manage orders","manage-orders-permission"),
    CAN_MANAGE_DISCOUNT("Can manage discounts","manage-discounts-permission"),
    CAN_MANAGE_LOYALTY("Can manage loyalties","manage-loyalties-permission");

    private final String name;
    private final String id;

    TypePermission(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }
}

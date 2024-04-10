export interface Props {
    id: number;
}

export interface DishesOrder {
    dishId: number;
    dishName: string;
    quantity: number;
    orderId: number;
}

export interface Order {
    id: number;
    clientName: string;
    description: string;
    clientPhone: number;
    status: string;
    orderTime: string;
    branchAddress: string;
    address: string;
    color: string;
    dishesOrders: DishesOrder[];
}

export interface OrderReview {
    id: number;
    clientName: string;
    description: string;
    clientPhone: number;
    status: string;
    orderTime: string;
    branchAddress: string;
    address: string;
    color: string;
    tag_color: string;
    tag_label: string;
    dishesOrders: DishesOrder[];
}

export interface OrderCooking {
    id: number;
    clientName: string;
    description: string;
    clientPhone: number;
    status: string;
    orderTime: string;
    branchAddress: string;
    address: string;
    orderCookingTime: string;
    branchId: number;
    color: string;
    tag_color: string;
    tag_label: string;
    dishesOrders: DishesOrder[];
}

export interface OrderCooked {
    id: number;
    clientName: string;
    description: string;
    clientPhone: number;
    status: string;
    orderTime: string;
    branchAddress: string;
    address: string;
    orderCookingTime: string;
    orderCookedTime: string;
    branchId: number;
    color: string;
    tag_color: string;
    tag_label: string;
    dishesOrders: DishesOrder[];
}
export interface Props {
    id: number;
}

export interface BranchOffice {
    id: number,
    address: string,
    status: string,
    nameCity: string
}

export interface User {
    email: string,
    firstName: string,
    idBranchOffice: BranchOffice,
    lastName: string,
    phoneNumber: string,
    status: string,
    username: string,
}

export interface NewDataUser {
    email: string,
    phoneNumber: string,
    idBranchOffice: string,
    firstName: string,
    lastName: string,
}

export interface AuthData {
    access_token: string,
    expires_in: number,
    not_before_policy: number
    refresh_expires_in: number,
    refresh_token: string,
    scope: string,
    session_state: string,
    token_type: string
}

export interface Category {
    id: number;
    category: string;
}

export interface Dish {
    id: number;
    name: string;
    description: string;
    urlImage: string;
    category: Category;
    price: number;
    weight: number;
}

export interface DishesOrder {
    dishId: number;
    dishName: string;
    quantity: number;
    orderId: number;
}

export interface UpdateDish {
    name: string;
    newName: string;
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
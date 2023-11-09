import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface dishesOrder {
    id: number;
    dish_id: number;
    dish_name: number;
    quantity: number;
    order_id: number;
}

interface Order {
    id: number;
    clientName: string;
    description: string;
    clientPhone: number;
    status: string;
    orderTime: string;
    branchAddress: string;
    address: string;
    dishesOrders: dishesOrder[];
}

interface OrderState {
    allOrders: Order[];
}

const initialState: OrderState = {
    allOrders: [],
};

const orderSlice = createSlice({
    name: 'order',
    initialState,
    reducers: {
        setAllOrders: (state, action: PayloadAction<Order[]>) => {
            state.allOrders = action.payload;
        },
    },
});

export const { setAllOrders
} = orderSlice.actions;

export default orderSlice.reducer;
import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface Order {
    id: number | null;
    clientName: string | null;
    description: string | null;
    clientPhone: number | null;
    status: string | null;
    orderTime: string | null;
    branchAddress: string | null;
    dishesOrders: any[];
}

interface OrderState {
    currentOrder: Order | null;
    allOrders: Order[];
}

const initialState: OrderState = {
    currentOrder: null,
    allOrders: [],
};

const orderSlice = createSlice({
    name: 'order',
    initialState,
    reducers: {
        setCurrentOrder: (state, action: PayloadAction<Order | null>) => {
            state.currentOrder = action.payload;
        },
        setAllOrders: (state, action: PayloadAction<Order[]>) => {
            state.allOrders = action.payload;
        },
    },
});

export const { setCurrentOrder,
    setAllOrders
} = orderSlice.actions;

export default orderSlice.reducer;
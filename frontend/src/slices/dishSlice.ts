import {createSlice, PayloadAction} from '@reduxjs/toolkit';

interface Category {
    id: number;
    category: string;
}

interface Dish {
    id: number;
    name: string;
    description: string;
    urlImage: string;
    category: Category;
    price: number;
    weight: number;
}

interface DishState {
    allBranchDishes: Dish[];
    allDishes: Dish[];
}

const initialState: DishState = {
    allBranchDishes: [],
    allDishes: [],
};

const dishSlice = createSlice({
    name: 'dish',
    initialState,
    reducers: {
        setAllBranchDishes: (state, action: PayloadAction<Dish[]>) => {
            state.allBranchDishes = action.payload;
        },setAllDish: (state, action: PayloadAction<Dish[]>) => {
            state.allDishes = action.payload;
        },
    },
});

export const {
    setAllBranchDishes,
    setAllDish
} = dishSlice.actions;

export default dishSlice.reducer;
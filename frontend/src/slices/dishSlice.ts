import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {DishData, DishTableData} from "../types/dishType";

interface DishState {
    allBranchDishes: DishData[];
    allDishes: DishData[];
    dish: DishTableData;
    // setDishId: number;
}

const initialState: DishState = {
    allBranchDishes: [],
    allDishes: [],
    dish: {
        key: -1,
        id: -1,
        name: 'None',
        description: 'None',
        urlImage: '',
        category: {id: -1, category: 'None'},
        price: -1,
        weight: -1,
    },
    // setDishId: -1,
};

const dishSlice = createSlice({
    name: 'dish',
    initialState,
    reducers: {
        // setDishId: (state, action: PayloadAction<number>) => {
        //     state.setDishId = action.payload;
        // },
        setAllBranchDishes: (state, action: PayloadAction<DishData[]>) => {
            state.allBranchDishes = action.payload;
            // if (state.setDishId !== -1){
            //     state.dish = {
            //         ...action.payload[state.allBranchDishes.findIndex(item => state.setDishId === item.id)],
            //         key: -1
            //     };
            //
            // }
        },
        setAllDish: (state, action: PayloadAction<DishData[]>) => {
            state.allDishes = action.payload;
        },
    },
});

export const {
    setAllBranchDishes,
    setAllDish,
    // setDishId,
} = dishSlice.actions;

export default dishSlice.reducer;
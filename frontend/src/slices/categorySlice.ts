import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {DishCategory} from "../types/dishType";


interface CategoryState {
    category: DishCategory[];
}

const initialState: CategoryState = {
    category: []
};

const categorySlice = createSlice({
    name: 'category',
    initialState,
    reducers: {
        setCategoryList: (state, action: PayloadAction<DishCategory[]>) => {
            state.category = action.payload;
        }
    },
});

export const {
    setCategoryList
} = categorySlice.actions;

export default categorySlice.reducer;
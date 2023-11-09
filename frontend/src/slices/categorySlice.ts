import {createSlice, PayloadAction} from '@reduxjs/toolkit';

interface Category {
    id: number;
    category: string;
}

interface CategoryState {
    category: Category[];
}

const initialState: CategoryState = {
    category: []
};

const categorySlice = createSlice({
    name: 'category',
    initialState,
    reducers: {
        setListCategory: (state, action: PayloadAction<Category[]>) => {
            state.category = action.payload;
        }
    },
});

export const {
    setListCategory
} = categorySlice.actions;

export default categorySlice.reducer;
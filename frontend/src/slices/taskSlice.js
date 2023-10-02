import {createSlice} from "@reduxjs/toolkit";

export const tasksSlice = createSlice({
    name: 'tasks',
    initialState: {
        tasks: [],
    },
    reducers: {
        set: (state, action) => {
            state.tasks = action.payload;
        }
    },
})

export const {
    set,
} = tasksSlice.actions;

export default tasksSlice.reducer;
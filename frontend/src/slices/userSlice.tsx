import {createSlice, PayloadAction} from "@reduxjs/toolkit";

interface UserState {
    user: User;
}

interface User {
    // Define user properties here
}

const initialState: UserState = {
    user: {}
};

const usersSlice = createSlice({
    name: "user",
    initialState,
    reducers: {
        set: (state, action: PayloadAction<User>) => {
            state.user = action.payload;
        },
    },
});

export const {
    set,
} = usersSlice.actions;

export default usersSlice.reducer;
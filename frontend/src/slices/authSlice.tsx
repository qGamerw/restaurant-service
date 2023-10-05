import {createSlice, PayloadAction} from "@reduxjs/toolkit";

interface UserState {
    isLoggedIn: boolean;
    user: User | null;
}

interface User {
    // Define user properties here
}

// @ts-ignore
const user = JSON.parse(localStorage.getItem("user"));

const initialState: UserState = user
    ? { isLoggedIn: true, user }
    : { isLoggedIn: false, user: null };

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        login: (state, action: PayloadAction<User>) => {
            state.isLoggedIn = true;
            state.user = action.payload;
        },
        logout: (state) => {
            state.isLoggedIn = false;
            state.user = null;
        }
    },
});

export const {
    login,
    logout,
} = authSlice.actions;

export default authSlice.reducer;
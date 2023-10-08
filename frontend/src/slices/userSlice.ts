import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface UserState {
    user: any | null;
    isAuth: boolean;
}
const getUserFromLocalStorage = (): any | null => {
    const userString = localStorage.getItem('user');
    if (userString) {
        return JSON.parse(userString);
    }
    return null;
};

const initialState: UserState = {
    user: getUserFromLocalStorage(),
    isAuth: false,
};

const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        set: (state, action: PayloadAction<any>) => {
            state.user = action.payload;
        },
        loginUser: (state, action: PayloadAction<any>) => {
            state.isAuth = true;
            state.user = action.payload;

            if (action.payload.accessToken) {
                localStorage.setItem('user', JSON.stringify(action.payload));
            }
        },
        logoutUser: (state) => {
            state.isAuth = false;
            state.user = null;

            localStorage.removeItem('user');
        },
    },
});

export const { set,
    loginUser,
    logoutUser
} = userSlice.actions;

export default userSlice.reducer;
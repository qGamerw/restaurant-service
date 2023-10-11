import {configureStore} from "@reduxjs/toolkit";
import userReducer from "./slices/userSlice";
import orderReducer from "./slices/orderSlice";

export default configureStore({
    reducer: {
        users: userReducer,
        order: orderReducer,
    },
});
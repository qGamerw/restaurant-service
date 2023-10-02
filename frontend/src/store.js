import {configureStore} from '@reduxjs/toolkit'
import authReducer from "./slices/authSlice";
import tasksSliceReducer from "./slices/taskSlice";

export default configureStore({
    reducer: {
        auth: authReducer,
        tasks: tasksSliceReducer,
    },
});
import axios from "axios";
import {set} from "../slices/userSlice";
import authHeader from "./auth-header";

const getUser = (id, dispatch) => {
    return axios.get(`users/${id}`, {headers: authHeader()}).then(
        (response) => {
            dispatch(set(response.data));
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });
};

const userService = {
    getUser,
};

export default userService
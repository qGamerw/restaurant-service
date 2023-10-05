import {Dispatch} from "react";
import axios from "axios";
const getUser = (id: string, dispatch: Dispatch) => {
    return axios.get('users/${id'}, { headers: authHeader() }).then(
        (response) => {
            dispatch(set(response.data));
        },
        (error) => {
            const content = (error.response && error.response.data)
                ? error.response.data
                : error.message || error.toString();

            console.error(content);

            dispatch(set());
        }
    );
};

const userService = {
    getUser,
};

export default userService;
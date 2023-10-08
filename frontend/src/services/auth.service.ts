import axios from "axios";
import { loginUser, logoutUser } from "../slices/userSlice";
import { Dispatch } from "redux";

interface Registration {
    username: string;
    email: string;
    password: string;
}

interface Login {
    username: string;
    password: string;
}

interface User {
    accessToken: string;
}

const API_URL = "http://localhost:8080/api/auth/"

const register = (registration: Registration) => {
    const { username, email, password } = registration;
    return axios.post(API_URL + "signup", {
        username,
        email,
        password,
    });
};

const login = async (login: Login, dispatch: Dispatch): Promise<User> => {
    const {username, password} = login;

    let response = await axios
        .post<User>(API_URL + "signin", {
            username,
            password,
        });
    console.log(response);
    dispatch(loginUser(response.data));
    return response.data;
};

const logout = (dispatch: Dispatch): void => {
    console.log("logout");
    dispatch(logoutUser());
};

const authService = {
    register,
    login,
    logout,
};

export default authService;
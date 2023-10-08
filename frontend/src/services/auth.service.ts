import axios from "axios";
import { loginUser, logoutUser } from "../slices/userSlice";
import { Dispatch } from "redux";

interface Registration {
    employeeName: string;
    email: string;
    password: string;
    branchOffice: string;
}

interface Login {
    employeeName: string;
    password: string;
}

interface User {
    accessToken: string;
}

const API_URL = "http://localhost:8080/api/auth/"

const register = (registration: Registration) => {
    const { employeeName, email, password , branchOffice} = registration;
    return axios.post(API_URL + "signup", {
        employeeName: employeeName,
        email,
        password,
        branchOffice
    });
};

const login = async (login: Login, dispatch: Dispatch): Promise<User> => {
    const {employeeName, password} = login;

    let response = await axios
        .post<User>(API_URL + "signin", {
            employeeName: employeeName,
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
import axios from "axios";
import {Login, Registration, User} from "../types/types";

const API_URL = "/api/auth/"

function saveLocalStore(user: User) {
    if (user.accessToken) {
        localStorage.setItem('user', JSON.stringify(user));
    }
}

async function register(registration: Registration): Promise<User> {
    const {employeeName, email, password, branchOffice} = registration;
    const response = await axios.post<User>(API_URL + "signup", {
        employeeName: employeeName,
        email,
        password,
        branchOffice
    });

    console.log(response);
    saveLocalStore(response.data);
    return response.data;
}

async function login(login: Login): Promise<User> {
    const {employeeName, password} = login;

    const response = await axios
        .post<User>(API_URL + "signin", {
            employeeName: employeeName,
            password,
        });
    console.log(response);
    saveLocalStore(response.data);
    return response.data;
}

function logout(): void {
    console.log("logout");
    localStorage.removeItem('user');
}

const authService = {
    register,
    login,
    logout,
};

export default authService;
import axios from "axios";
import {AuthData, Dish, Login, Registration, User, UserRegistration} from "../types/types";
import {Dispatch} from "redux";
import authHeader from "./auth-header";

const API_URL = "/api/auth"

function saveLocalStore(authData: AuthData) {
    if (authData.access_token) {
        sessionStorage.setItem('auth-date', JSON.stringify(authData));
    }
}

function saveLocalStoreUser(user: User) {
    if (user.username) {
        sessionStorage.setItem('user', JSON.stringify(user));
    }
}


async function register(registration: UserRegistration) {
    const {username, email, phoneNumber, password, idBranchOffice, firstName, lastName} = registration;
    const response = await axios.post<UserRegistration>(`${API_URL}/signup`, {
        username,
        email,
        phoneNumber,
        password,
        idBranchOffice,
        firstName,
        lastName
    });
    return response.data;
}

async function login(login: Login): Promise<User> {
    const {employeeName, password} = login;

    const responseAuthData = await axios
        .post<AuthData>(`${API_URL}/signin`, {
            username: employeeName,
            password,
        });
    saveLocalStore(responseAuthData.data);


    const headers = authHeader();
    const responseUser = await axios.get<User>(API_URL, {headers});
    saveLocalStoreUser(responseUser.data);

    return responseUser.data;
}

async function refresh(refresh_token: string, dispatch: Dispatch): Promise<User> {
    const responseAuthData = await axios
        .post<AuthData>(`${API_URL}/refresh`, {refresh_token: refresh_token});
    saveLocalStore(responseAuthData.data);


    const headers = authHeader();
    const responseUser = await axios.get<User>(API_URL, {headers});
    saveLocalStoreUser(responseUser.data);

    return responseUser.data;
}

function logout() {
    const headers = authHeader();

    if (headers.Authorization !== ''){
        try {
            sessionStorage.removeItem('auth-date');
            sessionStorage.removeItem('user');
            return axios.put(`${API_URL}/logout`, {},{headers});

        } catch (error) {
            console.error("Ошибка выхода:", error);
            throw error;
        }
    }
}

const authService = {
    register,
    login,
    logout,
    refresh
};

export default authService;
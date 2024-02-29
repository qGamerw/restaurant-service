import axios from "axios";
import {AuthData} from "../types/types";
import authHeader from "./auth-header";
import {AccountUser, userDatesSessionStorage} from "../types/accountType";
import {
    authAPIPath,
    AuthDataUser,
    authDatesSessionStorage,
    AuthLogin,
    AuthRegistration,
    AuthResetPassword
} from "../types/authType";
import {message} from "antd";

function saveLocalStore(authData: AuthData) {
    if (authData.access_token) {
        sessionStorage.setItem(authDatesSessionStorage, JSON.stringify(authData));
    }
}

function saveLocalStoreUser(user: AccountUser) {
    if (user.username) {
        sessionStorage.setItem(userDatesSessionStorage, JSON.stringify(user));
    }
}

async function authLogin(login: AuthLogin): Promise<AccountUser> {
    const responseAuthData = await axios
        .post<AuthDataUser>(`${authAPIPath}/signin`, {
            username: login.username,
            password: login.password,
        });
    saveLocalStore(responseAuthData.data);

    const headers = authHeader();
    const responseUser = await axios.get<AccountUser>(authAPIPath, {headers});
    saveLocalStoreUser(responseUser.data);
    return responseUser.data;
}

async function authLogOut() {
    const headers = authHeader();

    if (headers.Authorization !== '') {
        try {
            sessionStorage.removeItem(authDatesSessionStorage);
            sessionStorage.removeItem(userDatesSessionStorage);
            await axios.put(`${authAPIPath}/logout`, {}, {headers});

        } catch (error) {
            console.error("Ошибка выхода:", error);
            throw error;
        }
    }
}

async function authGetPasswordToken(email: string) {
    try {
        await axios.post(`${authAPIPath}/reset-password/token`, { email: email })
        message.success(`Письмо отправили на почту: ${email}!`);
    } catch (error) {
        message.error("Неизвестная ошибка при отправлении письма!");
    }
}

async function authResetPassword(value: AuthResetPassword) {
    console.log(value);
    try {
        await axios.put(`${authAPIPath}/reset-password`, {
            email: value.email,
            password: value.password,
            token: value.token,
        })
        message.success("Успешная смена пароля.");
    } catch (error) {
        message.error("Не удалось сменить пароль.");
    }
}

async function authRegisterUser(registration: AuthRegistration) {
    const {username, email,password, idBranchOffice} = registration;
    try {
        await axios.post<AuthRegistration>(`${authAPIPath}/signup`, {
            username,
            email,
            phoneNumber: '',
            password,
            idBranchOffice,
            firstName: '',
            lastName: ''
        });
        message.success("Успешная регистрации пользователя!");
    } catch (error) {
        message.error("Не удалось зарегистрироваться.");
    }
}

async function authRefreshToken(refresh_token: string) {
    try {
        const responseAuthData = await axios
            .post<AuthData>(`${authAPIPath}/refresh`, {refresh_token: refresh_token});

        saveLocalStore(responseAuthData.data);
        message.success("Сессия продлена!");
    } catch (error) {
        message.error("Не удалось продлить сессию!")
    }
}

const authService = {
    authLogin,
    authLogOut,
    authGetPasswordToken,
    authResetPassword,
    authRegisterUser,
    authRefreshToken
};

export default authService;
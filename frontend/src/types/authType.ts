export const authLoginPath = '/';
export const authRegistrationPath = '/auth/registration';
 export const authResetPasswordPath = '/auth/reset_password';
 export const authAPIPath = '/api/auth';
 export const authDatesSessionStorage = 'authDate';

export interface AuthLogin {
    username: string;
    password: string;
}

export interface AuthRegistration {
    username: string,
    email: string,
    phoneNumber: string,
    password: string,
    idBranchOffice: string,
    firstName: string,
    lastName: string,
}

export interface AuthResetPassword {
    email: string;
    password: string;
    token: string;
}

export interface AuthDataUser {
    access_token: string,
    expires_in: number,
    not_before_policy: number
    refresh_expires_in: number,
    refresh_token: string,
    scope: string,
    session_state: string,
    token_type: string
}
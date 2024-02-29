export const userDatesSessionStorage = 'user';
export const analyticAPIPath = '/analytic';

export interface AccountBranchOffice {
    id: number,
    address: string,
    status: string,
    location: string
}

export interface AccountUser {
    email: string,
    username: string,
    firstName: string,
    lastName: string,
    phoneNumber: string,
    status: string,
    idBranchOffice: AccountBranchOffice,
}

export interface AccountUserUpdate {
    email: string,
    firstName: string,
    lastName: string,
    phoneNumber: string,
    idBranchOffice: string,
}

import {authDatesSessionStorage} from "../types/authType";

export default function authHeader() {
    const authDataStore = sessionStorage.getItem(authDatesSessionStorage);
    let authData = null;
    if (authDataStore) {
        authData = JSON.parse(authDataStore);
    }

    if (authData && authData.access_token) {
        return {Authorization: 'Bearer ' + authData.access_token};
    } else {
        return {Authorization: ''};
    }
}
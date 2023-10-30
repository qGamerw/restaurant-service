import axios from "axios";

interface BranchOffice {
    id: number,
    address: string,
    status: string,
    nameCity: string
}

interface Position{
    id: number,
    position: string
}

interface User {
    accessToken: string,
    branchOffice: BranchOffice,
    email: string
    employeeName: string,
    id: number,
    position: Position,
    type: string
}

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

const API_URL = "/api/auth/"

const saveLocalStore = (user: User) =>{
    if (user.accessToken) {
        localStorage.setItem('user', JSON.stringify(user));
    }
}
const register = async (registration: Registration): Promise<User> => {
    const { employeeName, email, password , branchOffice} = registration;
    const response = await axios.post<User>(API_URL + "signup", {
        employeeName: employeeName,
        email,
        password,
        branchOffice
    });

    console.log(response);
    saveLocalStore(response.data);
    return response.data;
};

const login = async (login: Login): Promise<User> => {
    const {employeeName, password} = login;

    const response = await axios
        .post<User>(API_URL + "signin", {
            employeeName: employeeName,
            password,
        });
    console.log(response);
    saveLocalStore(response.data);
    return response.data;
};

const logout = (): void => {
    console.log("logout");
    localStorage.removeItem('user');
};

const authService = {
    register,
    login,
    logout,
};

export default authService;
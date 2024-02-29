import axios from "axios";
import authHeader from "./auth-header";
import {AccountUserUpdate} from "../types/accountType";
import {authAPIPath} from "../types/authType";
import {message} from "antd";



async function accountUserUpdate(value: AccountUserUpdate) {
    try {
        const headers = authHeader();
        await axios.put(`${authAPIPath}`, {
            email: value.email,
            phoneNumber: value.phoneNumber,
            idBranchOffice: value.idBranchOffice,
            firstName: value.firstName,
            lastName: value.lastName
        }, {headers});
        message.success('Данные успешно обновлены!');
    } catch (error) {
        message.error('Ошибка обновления!');
    }
}

// async function getCountOrderFromEmployeeRestaurant(): Promise<number> {
//     const headers = authHeader();
//
//     try {
//         const response = await axios.get(`${analyticAPIPath}/employee`, {headers});
//
//         return response.data;
//     } catch (error) {
//         console.error("Ошибка получения количества заказов у работника:", error);
//         throw error;
//     }
// }
//
// async function getOrderPerMonth(year: number|null, month: number|null): Promise<number> {
//     const headers = authHeader();
//
//     try {
//         if (year && month){
//             const response = await axios.get(`${analyticAPIPath}/orders/per/month?month=${month}&year=${year}`, {headers});
//             return response.data;
//         } else {
//             const response = await axios.get(`${analyticAPIPath}/orders/per/month?month=`, {headers});
//             return response.data;
//         }
//     } catch (error) {
//         console.error("Ошибка получения количества заказов за месяц: ", error);
//         throw error;
//     }
// }

const accountUserService = {
    accountUserUpdate,
};

export default accountUserService;
import axios from "axios";
import {Dispatch} from "redux";
import authHeader from "./auth-header";
import {setAllOrders, setCurrentOrder} from "../slices/orderSlice";

interface User {
    accessToken: string;
}

const API_URL_ORDER = "orders"

const updateOrderStatusById = async (id: number, status: string, dispatch: Dispatch) => {
    const headers = authHeader();

    try {
        const response = await axios.put(API_URL_ORDER + `/${id}`,
            {status: status},
            {headers});

        const updateOrder = response.data;
        dispatch(setCurrentOrder(updateOrder));
        return updateOrder;

    } catch (error) {
        console.error("Ошибка обновления заказа:", error);
        throw error;
    }
};

const cancelOrderById = async (id: number, message: string, dispatch: Dispatch): Promise<User> => {
    const headers = authHeader();

    try {
        const response = await axios.put(API_URL_ORDER + `/${id}/cancel`,
            {message: message},
            {headers});

        const updateOrder = response.data;
        dispatch(setCurrentOrder(updateOrder));
        return updateOrder;

    } catch (error) {
        console.error("Ошибка отмены заказа:", error);
        throw error;
    }
};

const getListOrders = async (dispatch: Dispatch) => {
    const headers = authHeader();

    try {
        const response = await axios.get(API_URL_ORDER, { headers });

        const awaitingDeliveryOrders = response.data;

        dispatch(setAllOrders(awaitingDeliveryOrders));

        return awaitingDeliveryOrders;
    } catch (error) {
        console.error("Ошибка получения заказов:", error);
        throw error;
    }
};

const orderService = {
    updateOrderStatusById,
    cancelOrderById,
    getListOrders,
};

export default orderService;
import axios from "axios";
import {Dispatch} from "redux";
import authHeader from "./auth-header";
import {setAllBranchDishes, setAllDish} from "../slices/dishSlice";

const API_URL_DISHES = "dishes"

interface Category {
    id: number | null;
    category: string | null;
}

interface Dish {
    id: number | null;
    name: string | null;
    description: string | null;
    urlImage: string | null;
    category: Category;
    price: number | null;
    weight: number | null;
}

const getListDishByBranchId = async (dispatch: Dispatch) => {
    const headers = authHeader();

    try {
        const response = await axios.get(`${API_URL_DISHES}/all`, {headers});
        const dishes = response.data;
        dispatch(setAllBranchDishes(dishes));
        return dishes;

    } catch (error) {
        console.error("Ошибка получения всех блюд у филиала:", error);
        throw error;
    }
};

const getListDishAll = async (page: number, size: number, dispatch: Dispatch) => {
    const headers = authHeader();

    try {
        const response = await axios.get(`${API_URL_DISHES}/any?page=${page}&size=${size}`, {headers});
        const dishes = response.data.content;
        dispatch(setAllDish(dishes));
        return dishes;

    } catch (error) {
        console.error("Ошибка получения всех блюд:", error);
        throw error;
    }
};

const updateDish = async (dish: Dish, dispatch: Dispatch) => {
    const headers = authHeader();

    try {
        return (await axios.put(`${API_URL_DISHES}/`, dish, {headers})).data;

    } catch (error) {
        console.error("Ошибка обновления блюда:", error);
        throw error;
    }
};

const dishService = {
    getListDishByBranch: getListDishByBranchId,
    getListDish: getListDishAll,
    updateDish: updateDish,
};

export default dishService;
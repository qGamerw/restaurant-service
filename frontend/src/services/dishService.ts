import axios from "axios";
import {Dispatch} from "redux";
import authHeader from "./auth-header";
import {setAllBranchDishes, setAllDish} from "../slices/dishSlice";
import {categoryAPIPath, dishAPIPath, DishCategory, DishData, DishNewData} from "../types/dishType";
import {message} from "antd";
import {setCategoryList} from "../slices/categorySlice";

async function dishGetByBranch(dispatch: Dispatch) {
    try {
        const headers = authHeader();

        const dishes: DishData[] = (await axios.get(`${dishAPIPath}/all`, {headers})).data;
        dispatch(setAllBranchDishes(dishes));
    } catch (error) {
        message.error("Ошибка получения всех блюд у филиала!");
    }
}

async function dishGetListByPage(page: number, size: number, dispatch: Dispatch) {
    try {
        const headers = authHeader();

        const dishes: DishData[] = (await axios.get(`${dishAPIPath}/customer/any?page=${page}&size=${size}`, {headers})).data.content;
        dispatch(setAllDish(dishes));

    } catch (error) {
        message.error("Ошибка получения всех блюд:");
    }
}

async function dishGetCategoryList(dispatch: Dispatch) {
    try {
        const headers = authHeader();

        const categoryList: DishCategory[] = (await axios.get(categoryAPIPath, {headers})).data;
        dispatch(setCategoryList(categoryList));

    } catch (error) {
        message.error("Ошибка получения всех блюд:");
    }
}

async function dishDataUpdate(dish: DishData, dispatch: Dispatch) {
    const headers = authHeader();

    try {
        await axios.put(dishAPIPath, dish, {headers});
    } catch (error) {
        message.error('Ошибка обновления данных блюда.')
        message.error("Недостаточно прав!");
    }
}

async function dishAddNewDishOnBranch(values: DishNewData, dispatch: Dispatch) {
    const headers = authHeader();

    try {
        await axios.post(`${dishAPIPath}/`, values, {headers});
        await dishGetByBranch(dispatch);
        message.info("Блюдо было добавлено в филиал.")
    } catch (error) {
        message.error('Ошибка добавления нового блюда.')
        message.error("Недостаточно прав!");
    }
}

const dishService = {
    dishGetByBranch,
    dishGetListByPage,
    dishGetCategoryList,
    dishAddNewDishOnBranch,
    dishDataUpdate,
};

export default dishService;
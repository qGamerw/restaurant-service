import axios from "axios";
import {set} from "../slices/taskSlice";
import authHeader from "./auth-header";

const getAllTask = (dispatch) => {
    return axios.get(`/tasks/user`, { headers: authHeader() }).then(
        (response) => {
            dispatch(set(response.data));
            console.log(response.data);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });
};

const getTaskByCategoryId = (categoryId, dispatch) => {
    return axios.get(`/tasks/${categoryId}`, { headers: authHeader() }).then(
        (response) => {
            dispatch(set(response.data));
            console.log(response.data);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });
};

const getTaskByArchive = (dispatch) => {
    return axios.get(`/tasks/archive`, { headers: authHeader() }).then(
        (response) => {
            dispatch(set(response.data));
            console.log(response.data);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });
};

const createTaskInCategory = (task, dispatch) => {
    console.log(task);

    return axios.post('/tasks', task, {headers: authHeader()}).then(
        (response) => {
            getTaskByCategoryId(task.category.id, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateTask = (task, dispatch) => {
    return axios.put('/tasks', task, {headers: authHeader()}).then(
        (response) => {
            getAllTask(dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteTask = (id, dispatch) => {
    return axios.delete('/tasks/'+id, {headers: authHeader()}).then(
        (response) => {
            getAllTask(dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const tasksService = {
    getAllTask,
    getTaskByCategoryId,
    updateTask,
    deleteTask,
    createTaskInCategory,
    getTaskByArchive,
};

export default tasksService
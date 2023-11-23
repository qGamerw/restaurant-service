import React, {useEffect, useState} from 'react';
import {Button, Input, message, Modal} from 'antd';
import orderService from '../services/orderService';
import {useDispatch} from 'react-redux';
import {Category, Dish, Props, UpdateDish} from "../types/types";
import dishService from "../services/dishService";
import Paragraph from 'antd/es/skeleton/Paragraph';
import FormNewDish from "./FormNewDish";

interface ModalDishProperty {
    modal2Open: boolean;
    category: Category[];
    onClose: () => void;
}

const ModalNewDish: React.FC<ModalDishProperty> = ({modal2Open, category, onClose}) => {
    const dispatch = useDispatch();


    return (
            <Modal
                title="Добавить новое блюдо"
                centered
                open={modal2Open}
                footer={null}
                onCancel={onClose}
            >
                <FormNewDish dispatch={dispatch} category={category}/>
            </Modal>
    );
};

export default ModalNewDish;
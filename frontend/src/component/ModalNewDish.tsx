import React from 'react';
import {Modal} from 'antd';
import {useDispatch} from 'react-redux';
import FormNewDish from "./FormNewDish";
import {ModalDishProperty} from "../types/dishType";

const ModalNewDish: React.FC<ModalDishProperty> = ({modalNewDish, category, onClose}) => {
    const dispatch = useDispatch();

    return (
        <Modal
            title="Добавить новое блюдо"
            centered
            open={modalNewDish}
            footer={null}
            onCancel={onClose}
        >
            <FormNewDish dispatch={dispatch} category={category}/>
        </Modal>
    );
};

export default ModalNewDish;
import React from 'react';
import {Button, Modal, Typography, Upload} from 'antd';
import {ChangeCategoryRevers, DishData, DishUpdateName, ModalDishNewProperty} from "../types/dishType";
import logo from "../images/logo.jpg";
import dishService from "../services/dishService";
import {UploadOutlined} from "@ant-design/icons";

const {Paragraph} = Typography;

const ModalOpenDish: React.FC<ModalDishNewProperty> = (
    {
        modalNewDish,
        isAllDish,
        dish,
        dispatch,
        onClose,
        setIsUpdate,
        isUpdate
    }) => {

    // console.log(dish);

    function updateDish(dish: DishData, newStr: DishUpdateName) {
        let updatedDish: DishData;

        if (newStr.name === 'price' || newStr.name === 'weight') {
            updatedDish = {...dish, [newStr.name]: parseInt(newStr.newName)};
        } else {
            updatedDish = {...dish, [newStr.name]: newStr.newName};
        }

        updatedDish = {
            ...updatedDish,
            category: {id: updatedDish.id, category: ChangeCategoryRevers(updatedDish.category.category)}
        };

        setIsUpdate(!isUpdate);
        dishService.dishDataUpdate(updatedDish, dispatch);
    }

    return (
        <Modal
            title='Побронее'
            centered
            open={modalNewDish}
            footer={null}
            onCancel={onClose}
        >
            <div style={{marginTop: 'auto', marginBottom: 'auto'}}>
                <img src={dish.urlImage ? dish.urlImage : logo} alt={"Изображение блюда:" + dish.name}
                     style={{
                         borderRadius: '3vh',
                         maxWidth: '350px',
                         maxHeight: '300px'
                     }}
                />
                {isAllDish? <> </> :
                    <Upload
                    action="https://run.mocky.io/v3/435e224c-44fb-4773-9faf-380c5e6a2188"
                    listType="picture"
                    maxCount={1}
                    style={{marginTop: 0}}
                >
                    <Button icon={<UploadOutlined/>}>Upload</Button>
                </Upload>  }
            </div>


            <div>
                <b>Название блюда: </b>
                <Paragraph
                    editable={isAllDish ? false : {
                        onChange: (newStr) => updateDish(dish, {
                            name: 'name',
                            newName: newStr
                        })
                    }}> {dish.name}
                </Paragraph>
            </div>

            <div>
                <b>Цена: </b>
                <Paragraph
                    editable={isAllDish ? false : {
                        onChange: (newStr) => updateDish(dish, {
                            name: 'price',
                            newName: newStr
                        })
                    }}> {dish.price}
                </Paragraph>
            </div>

            <p style={{marginTop: 30}}><b>Спецификация блюда: </b></p>

            <p><b>Категория: </b>{dish.category.category}</p>
            <div>
                <b>Описание: </b>
                <Paragraph
                    editable={isAllDish ? false : {
                        onChange: (newStr) => updateDish(dish, {
                            name: 'description',
                            newName: newStr
                        })
                    }}> {dish.description}
                </Paragraph>
            </div>

            <div>
                <b>Вес: </b>
                <Paragraph
                    editable={isAllDish ? false : {
                        onChange: (newStr) => updateDish(dish, {
                            name: 'weight',
                            newName: newStr
                        })
                    }}> {dish.weight}
                </Paragraph>
            </div>
        </Modal>
    );
};

export default ModalOpenDish;
import React, {useState} from 'react';
import {Button, Form, Input, Select} from 'antd';
import {EditOutlined} from '@ant-design/icons';
import {AddNewDish, CategoryForm, ChangeCategory, DishNewData} from "../types/dishType";
import dishService from "../services/dishService";

const {Option} = Select;

const tailLayout = {
    wrapperCol: {offset: 8, span: 16},
};

const FormNewDish: React.FC<AddNewDish> = ({dispatch, category}) => {
    const [form] = Form.useForm();
    const [loading, setLoading] = useState(false);

    function onFinish(values: DishNewData) {
        var cat: any = values.category;
        const dish: DishNewData = {
            name: values.name,
            urlImage: values.urlImage,
            description: values.description,
            category: { id: cat},
            price: values.price,
            weight: values.weight
        }
        dishService.dishAddNewDishOnBranch(dish, dispatch);
    }

    const onReset = () => {
        form.resetFields();
    };

    const onFill = () => {
        form.setFieldsValue({
            name: 'Пицца «Цезарь» C ветчиной',
            urlImage: 'https://www.shutterstock.com/shutterstock/photos/666662188/display_1500/stock-photo-closeup-hand-of-chef-baker-in-white-uniform-making-pizza-at-kitchen-666662188.jpg',
            category: 4,
            description: 'Традиционная итальянская технология приготовления пиццы подразумевает длительную расстойку и нанесение соуса на сырую основу, так пицца получается сочной и ароматной. Пицца «Цезарь» С ветчиной изготовлена по этой технологии.',
            price: 400,
            weight: 300
        });
    };

    return (
        <Form
            name="basic"
            labelCol={{span: 8}}
            wrapperCol={{span: 16}}
            style={{maxWidth: 600}}
            onFinish={onFinish}
            autoComplete="off"
            form={form}
        >
            <Form.Item<DishNewData>
                label="Название"
                name="name"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input prefix={<EditOutlined className="site-form-item-icon"/>} placeholder="Название"/>
            </Form.Item>

            <Form.Item<DishNewData>
                label="Url image"
                name="urlImage"
            >
                <Input prefix={<EditOutlined className="site-form-item-icon"/>} placeholder="https://..."/>
            </Form.Item>

            <Form.Item<DishNewData>
                label="Категория"
                name="category"
                rules={[{required: true, message: 'Пожалуйста, выберете!'}]}>
                <Select style={{width: 200}} >
                    {category.map((item) => (
                        <Select.Option key={item.id} value={item.id}>
                            {ChangeCategory(item.category)}
                        </Select.Option>
                    ))}
                </Select>
            </Form.Item>

            <Form.Item<DishNewData>
                label="Описание"
                name="description"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input prefix={<EditOutlined className="site-form-item-icon"/>} placeholder="Опишите блюдо"/>
            </Form.Item>

            <Form.Item<DishNewData>
                label="Цена"
                name="price"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input type="number" prefix={<EditOutlined className="site-form-item-icon"/>} placeholder="100"/>
            </Form.Item>

            <Form.Item<DishNewData>
                label="Вес"
                name="weight"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input type="number" prefix={<EditOutlined className="site-form-item-icon"/>} placeholder="100"/>
            </Form.Item>
            <Form.Item {...tailLayout}>
                <Button type="primary" htmlType="submit" loading={loading} style={{marginLeft: -130, marginRight: 10}}>
                    Добавить
                </Button>
                <Button htmlType="button" onClick={onReset} style={{ display: 'inline-block', marginRight: 10 }}>
                    Сбросить форму
                </Button>
                <Button type="link" htmlType="button" onClick={onFill} style={{ display: 'inline-block' }}>
                    Заполнить форму
                </Button>
            </Form.Item>
        </Form>
    );
};

export default FormNewDish;
import React from 'react';
import {Avatar, Button, Form, Input, message} from 'antd';
import authService from '../services/authService';
import {AntDesignOutlined, EditOutlined} from '@ant-design/icons';
import {NewDataUser, User} from "../types/types";
import account from "../images/employee.png";

// не смотрел
const FormNewDataUser = () => {
    const userDataString = sessionStorage.getItem('user');
    const userData: User = userDataString ? JSON.parse(userDataString) : {};

    function onFinish(values: NewDataUser) {
        const updateData: NewDataUser = {
            email: values.email || userData.email,
            phoneNumber: values.phoneNumber || userData.phoneNumber,
            idBranchOffice: String(userData.idBranchOffice.id),
            firstName: values.firstName || userData.firstName,
            lastName: values.lastName || userData.lastName,
        };

        // authService.newDataUser(updateData).then((user) => {
        //     message.success("Сохранено");
        //     window.location.reload();
        //
        // }, (error) => {
        //     const _content = (error.response && error.response.data)
        //     console.log(_content);
        //     message.error("Ошибка обновления данных");
        // })
    }

    return (
        <>
            <Avatar
                size={150}
                icon={<AntDesignOutlined/>}
                style={{marginTop: 10, marginBottom: 10 }}
                src={account}
            />

            <Form
                name="basic"
                labelCol={{span: 8}}
                wrapperCol={{span: 16}}
                style={{maxWidth: 600}}
                initialValues={{remember: true}}
                onFinish={onFinish}
                autoComplete="off"
            >
                <Form.Item
                    label="Почта"
                    name="email"
                    rules={[
                        {type: 'email', message: 'Введите почту!'}
                    ]}
                >
                    <Input prefix={<EditOutlined className="site-form-item-icon"/>} placeholder={String(userData.email)} maxLength={30} />
                </Form.Item>

                <Form.Item
                    label="Номер телефона"
                    name="phoneNumber"
                >
                    <Input prefix={<EditOutlined className="site-form-item-icon"/>} placeholder={String(userData.phoneNumber)} maxLength={12} />
                </Form.Item>

                <Form.Item
                    label="Имя"
                    name="firstName"
                >
                    <Input prefix={<EditOutlined  className="site-form-item-icon"/>} placeholder={String(userData.firstName)} maxLength={30} />
                </Form.Item>

                <Form.Item
                    label="Фамилия"
                    name="lastName"
                >
                    <Input prefix={<EditOutlined className="site-form-item-icon"/>} placeholder={String(userData.lastName)} maxLength={30} />
                </Form.Item>

                <Form.Item wrapperCol={{offset: 8, span: 16}}>
                    <Button type="primary" htmlType="submit">
                        Сохранить
                    </Button>
                </Form.Item>
            </Form>
        </>
    );
};

export default FormNewDataUser;
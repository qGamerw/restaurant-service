import React from 'react';
import {Button, Form, Input, message} from 'antd';
import {authLoginPath, AuthRegistration} from "../types/authType";
import {useNavigate} from "react-router-dom";
import authService from "../services/authService";

const FormRegistration: React.FC = () => {
    const navigate = useNavigate();

    const onFinish = (values: AuthRegistration) => {
        console.log('Success:', values);
        authService.authRegisterUser(values).then(() => navigate(authLoginPath));
    }

    const onFinishFailed = () => {
        message.error("Ошибка регистрации, поля не заполнены полностью.");
    };

    return (
        <Form
            name="basic"
            labelCol={{span: 6}}
            wrapperCol={{span: 16}}
            style={{maxWidth: 500}}
            initialValues={{remember: false}}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
        >
            <Form.Item<AuthRegistration>
                label="Логин"
                name="username"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input placeholder={"********"}/>
            </Form.Item>

            <Form.Item<AuthRegistration>
                label="Email"
                name="email"
                rules={[
                    {
                        type: 'email',
                        message: 'Некорректный формат!',
                    },
                    {
                        required: true,
                        message: 'Пожалуйста, введите email',
                    },
                ]}
            >
                <Input type="email" placeholder={"********"}/>
            </Form.Item>

            <Form.Item<AuthRegistration>
                label="Пароль"
                name="password"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input.Password placeholder={"********"}/>
            </Form.Item>

            <Form.Item<AuthRegistration>
                label="Office"
                name="idBranchOffice"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input placeholder={"********"}/>
            </Form.Item>

            <Form.Item wrapperCol={{offset: 6, span: 8}}>
                <Button type="primary" htmlType="submit">
                    Зарегистрироваться
                </Button>

            </Form.Item>

            <div style={{
                fontSize: 12
            }}>
                Есть учетная запись? <a href={authLoginPath}> Войти</a>
            </div>
        </Form>
    );
};

export default FormRegistration;
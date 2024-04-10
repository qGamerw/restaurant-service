import React from 'react';
import {Button, Form, Input, message} from 'antd';
import {AuthLogin, authRegistrationPath, authResetPasswordPath} from "../types/authType";
import {useNavigate} from "react-router-dom";
import {dishPath} from "../types/dishType";
import authService from "../services/authService";

const FormLogin: React.FC = () => {
    const navigate = useNavigate();

    const onFinish = (values: AuthLogin) => {
        message.warning("Попытка входа.");

        authService.authLogin(values).then((user) => {
            console.log('Success:', user);
            navigate(dishPath);
            window.location.reload();
        }, (error) => {
            const _content = (error.response && error.response.data)
            console.log(_content);
            message.error("Неправильный логин или пароль!");
        })
    };

    const onFinishFailed = () => {
        message.error("Ошибка входа, поля не заполнены полностью.");
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
            <Form.Item<AuthLogin>
                label="Логин"
                name="username"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input placeholder={"********"}/>
            </Form.Item>

            <Form.Item<AuthLogin>
                label="Пароль"
                name="password"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input.Password placeholder={"********"}/>
            </Form.Item>

            <Form.Item<AuthLogin>
            >
                <a href={authResetPasswordPath}>Забыли пароль?</a>
            </Form.Item>

            <Form.Item wrapperCol={{offset: 8, span: 16}}>
                <Button type="primary" htmlType="submit">
                    Войти
                </Button>

            </Form.Item>

            <div style={{
                fontSize: 12
            }}>
                Не имеете учетной записи? <a href={authRegistrationPath}> Зарегистрироваться</a>
            </div>
        </Form>
    );
};

export default FormLogin;
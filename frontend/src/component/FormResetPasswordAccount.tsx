import React from 'react';
import {Button, Form, Input, message} from 'antd';
import {authLoginPath, AuthResetPassword} from "../types/authType";
import {useNavigate} from "react-router-dom";
import authService from "../services/authService";
import {AccountUser, userDatesSessionStorage} from "../types/accountType";

const FormResetPasswordAccount: React.FC = () => {
    const userDataString = sessionStorage.getItem(userDatesSessionStorage);
    const userData: AccountUser = userDataString ? JSON.parse(userDataString) : null;
    const navigate = useNavigate();

    const onFinish = (values: AuthResetPassword) => {
        const {email, ...rest} = values;

        authService.authResetPassword({email: userData.email, ...rest}).then(() => {
            message.warning("Попытка сброса пароля пользователя.");
            navigate(authLoginPath);
        });
    };

    const onFinishFailed = () => {
        message.error("Ошибка восстановления пароля, поля не заполнены полностью.");
    }

    const getToken = () => {
        authService.authGetPasswordToken(userData.email);
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
            <Form.Item<AuthResetPassword>
                label="Пароль"
                name="password"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input.Password placeholder={"********"}/>
            </Form.Item>

            <Form.Item<AuthResetPassword>
                label="Токен"
                name="token"
                rules={[{required: true, message: 'Пожалуйста, введите!'}]}
            >
                <Input placeholder={"********"}/>
            </Form.Item>

            <Form.Item wrapperCol={{offset: 4, span: 2}}>
                <div style={{marginLeft: 80}}>
                    <Button type="primary" onClick={getToken}>
                        Получить токен на почту
                    </Button>

                    <Button type="primary" htmlType="submit" style={{marginTop: 10, marginLeft: 50}}>
                        Сбросить
                    </Button>
                </div>
            </Form.Item>
        </Form>
    );
};

export default FormResetPasswordAccount;
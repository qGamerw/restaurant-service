import React, {useState} from 'react';
import {Button, Form, Input, message} from 'antd';
import {authLoginPath, AuthResetPassword} from "../types/authType";
import {useNavigate} from "react-router-dom";
import authService from "../services/authService";

const FormResetPassword: React.FC = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState<string>('');

    const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
    };

    const onFinish = (values: AuthResetPassword) => {
        console.log('Success:', values);
        authService.authResetPassword(values).then(() => {
            message.warning("Попытка сброса пароля пользователя.");
            navigate(authLoginPath);
        });
    };

    const onFinishFailed = () => {
        message.error("Ошибка восстановления пароля, поля не заполнены полностью.");
    }

    const getToken = () => {
        if (email === '') {
            message.error('Поле email не заполнено!')
            return;
        }
        authService.authGetPasswordToken(email);
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
                <Input type="email" placeholder={"********"} onChange={handleEmailChange}/>
            </Form.Item>

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
                <Button type="primary" onClick={getToken}>
                    Получить токен на почту
                </Button>

                <Button type="primary" htmlType="submit" style={{marginTop: 10, marginLeft: 50, marginRight: -60}}>
                    Сбросить
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

export default FormResetPassword;
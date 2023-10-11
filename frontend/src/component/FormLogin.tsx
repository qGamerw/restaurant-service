import React from 'react';
import {Button, Form, Input, message} from 'antd';
import authService from "../services/authService";
import {useDispatch} from "react-redux";
import {LockOutlined, UserOutlined} from "@ant-design/icons";
import {useNavigate} from "react-router-dom";

interface Login {
    employeeName: string;
    password: string;
}

type FieldType = {
    employeeName?: string;
    password?: string;
};

const FormLogin: React.FC = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const onFinish = (values: Login) => {
        navigate("/231");
        authService.login(values, dispatch).then((user) => {
            console.log('Success:', user);

            navigate("/");
        }, (error) => {
            const _content = (error.response && error.response.data)
            console.log(_content);
            message.error("Неправильный логин или пароль");
        })
    };

    return <Form
        name="basic"
        labelCol={{span: 8}}
        wrapperCol={{span: 16}}
        style={{maxWidth: 600}}
        initialValues={{remember: true}}
        onFinish={onFinish}
        autoComplete="off"
    >
        <Form.Item<FieldType>
            label="Username"
            name="employeeName"
            rules={[{required: true, message: 'Please input your username!'}]}
        >
            <Input prefix={<UserOutlined className="site-form-item-icon"/>}
                   placeholder="Username"
            />
        </Form.Item>

        <Form.Item<FieldType>
            label="Password"
            name="password"
            rules={[{required: true, message: 'Please input your password!'}]}
        >
            <Input.Password
                prefix={<LockOutlined className="site-form-item-icon"/>}
                placeholder="Password"
            />
        </Form.Item>

        <Form.Item wrapperCol={{offset: 8, span: 16}}>
            <Button type="primary" htmlType="submit">
                Sign In
            </Button>
        </Form.Item>
    </Form>
};

export default FormLogin;
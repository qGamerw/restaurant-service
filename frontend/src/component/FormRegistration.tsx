import React from 'react';
import {Button, Form, Input} from 'antd';
import authService from '../services/authService';
import {LockOutlined, MailOutlined, PhoneOutlined, UserOutlined} from '@ant-design/icons';
import {useNavigate} from 'react-router-dom';
import {UserRegistration} from "../types/types";

interface Registration {
    employeeName: string;
    email: string;
    password: string;
    branchOffice: string;
}

const FormRegistration: React.FC = () => {
    const navigate = useNavigate();

    async function onFinish(values: UserRegistration) {

        authService.register(values).then((massage) => {
            console.log('Success:', massage);
            navigate("/dishes")
        }, (error) => {
            const _content = (error.response && error.response.data)
            console.log(_content);
        })
    }

    return (
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
                label="Username"
                name="username"
                rules={[{required: true, message: 'Please input your username!'}]}
            >
                <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Username"/>
            </Form.Item>

            <Form.Item
                label="First name"
                name="firstName"
                rules={[{required: true, message: 'Please input your first name!'}]}
            >
                <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="First name"/>
            </Form.Item>

            <Form.Item
                label="Last name"
                name="lastName"
                rules={[{required: true, message: 'Please input your last name!'}]}
            >
                <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Last name"/>
            </Form.Item>

            <Form.Item
                label="Phone number"
                name="phoneNumber"
                rules={[{required: true, message: 'Please input your last name!'}]}
            >
                <Input prefix={<PhoneOutlined className="site-form-item-icon"/>} placeholder="Phone number"/>
            </Form.Item>

            <Form.Item
                label="Email"
                name="email"
                rules={[
                    {type: 'email', message: 'Введите email!'},
                    {required: true, message: 'Please input your email!'},
                ]}
            >
                <Input prefix={<MailOutlined className="site-form-item-icon"/>} placeholder="Email"/>
            </Form.Item>

            <Form.Item
                label="Branch office"
                name="idBranchOffice"
                rules={[{required: true, message: 'Please input your id branch Office!'}]}
            >
                <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Id branch office"/>
            </Form.Item>

            <Form.Item
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
                    Sign Up
                </Button>
            </Form.Item>
        </Form>
    );
};

export default FormRegistration;
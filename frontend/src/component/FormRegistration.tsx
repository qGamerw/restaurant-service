import React from 'react';
import {Button, Form, Input} from 'antd';
import authService from '../services/authService';
import {LockOutlined, MailOutlined, UserOutlined} from '@ant-design/icons';
import {useNavigate} from 'react-router-dom';

interface Registration {
    employeeName: string;
    email: string;
    password: string;
    branchOffice: string;
}

const FormRegistration: React.FC = () => {
    const navigate = useNavigate();

    const onFinish = async (values: Registration) => {

        authService.register(values).then((massage) => {
            console.log('Success:', massage);
            navigate("/dishes")
        }, (error) => {
            const _content = (error.response && error.response.data)
            console.log(_content);
        })
    };

    return (
        <Form
            name="basic"
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            style={{ maxWidth: 600 }}
            initialValues={{ remember: true }}
            onFinish={onFinish}
            autoComplete="off"
        >
            <Form.Item
                label="Username"
                name="employeeName"
                rules={[{ required: true, message: 'Please input your username!' }]}
            >
                <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
            </Form.Item>

            <Form.Item
                label="Email"
                name="email"
                rules={[
                    { type: 'email', message: 'Введите email!' },
                    { required: true, message: 'Please input your email!' },
                ]}
            >
                <Input prefix={<MailOutlined className="site-form-item-icon" />} placeholder="Email" />
            </Form.Item>

            <Form.Item
                label="Branch office"
                name="branchOffice"
                rules={[{ required: true, message: 'Please input your id branch Office!' }]}
            >
                <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Id branch office" />
            </Form.Item>

            <Form.Item
                label="Password"
                name="password"
                rules={[{ required: true, message: 'Please input your password!' }]}
            >
                <Input.Password
                    prefix={<LockOutlined className="site-form-item-icon" />}
                    placeholder="Password"
                />
            </Form.Item>

            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                <Button type="primary" htmlType="submit">
                    Sign Up
                </Button>
            </Form.Item>
        </Form>
    );
};

export default FormRegistration;
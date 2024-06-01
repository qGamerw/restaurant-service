import React from 'react';
import {Avatar, Button, Form, Input, Space, Upload} from 'antd';
import {AntDesignOutlined, EditOutlined, UploadOutlined} from '@ant-design/icons';
import account from "../images/employee.png";
import {AccountUser, AccountUserUpdate, userDatesSessionStorage} from "../types/accountType";
import accountUserService from "../services/accountUserService";
import authService from "../services/authService";

const FormNewDataUser = () => {
    const userDataString = sessionStorage.getItem(userDatesSessionStorage);
    const userData: AccountUser = userDataString ? JSON.parse(userDataString) : {};

    function onFinish(values: AccountUserUpdate) {
        const updateData: AccountUserUpdate = {
            email: values.email || userData.email,
            phoneNumber: values.phoneNumber || userData.phoneNumber,
            idBranchOffice: String(userData.idBranchOffice.id),
            firstName: values.firstName || userData.firstName,
            lastName: values.lastName || userData.lastName,
        };

        accountUserService.accountUserUpdate(updateData);
        authService.authUserGetData();
    }

    return (
        <>
            <Space direction="horizontal">
                <Avatar
                    size={150}
                    icon={<AntDesignOutlined/>}
                    style={{marginTop: 10, marginBottom: 10}}
                    src={account}
                />
                <Upload
                    action="https://run.mocky.io/v3/435e224c-44fb-4773-9faf-380c5e6a2188"
                    listType="picture"
                    maxCount={1}
                    style={{marginTop: 50}}
                >
                    <Button icon={<UploadOutlined/>}>Upload</Button>
                </Upload>
            </Space>

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
                        {
                            type: 'email',
                            message: 'Некорректный формат!',
                        },
                    ]}
                >
                    <Input type="email" prefix={<EditOutlined className="site-form-item-icon"/>}
                           placeholder={String(userData.email)} maxLength={30}/>
                </Form.Item>

                <Form.Item
                    label="Номер телефона"
                    name="phoneNumber"
                >
                    <Input prefix={<EditOutlined className="site-form-item-icon"/>}
                           placeholder={userData.phoneNumber != null? (userData.phoneNumber): ""} maxLength={12}/>
                </Form.Item>

                <Form.Item
                    label="Имя"
                    name="firstName"
                >
                    <Input prefix={<EditOutlined className="site-form-item-icon"/>}
                           placeholder={userData.firstName != null?  String(userData.firstName): ""} maxLength={30}/>
                </Form.Item>

                <Form.Item
                    label="Фамилия"
                    name="lastName"
                >
                    <Input prefix={<EditOutlined className="site-form-item-icon"/>}
                           placeholder={userData.lastName != null?  String(userData.lastName): ""} maxLength={30}/>
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
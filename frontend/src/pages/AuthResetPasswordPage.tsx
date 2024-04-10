import React from 'react';
import {Card} from 'antd';
import FormResetPassword from "../component/FormResetPassword";

const AuthResetPasswordPage: React.FC = () => {


    return (
        <div style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "100vh",
            width: "100vw",
            minWidth: 550
        }}>
            <Card hoverable={true}>
                <FormResetPassword/>
            </Card>
        </div>
    );
};

export default AuthResetPasswordPage;
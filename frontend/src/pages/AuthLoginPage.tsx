import React from 'react';
import {Card} from 'antd';
import FormLogin from "../component/FormLogin";

const AuthLoginPage: React.FC = () => {
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
                <FormLogin/>
            </Card>
        </div>
    );
};

export default AuthLoginPage;
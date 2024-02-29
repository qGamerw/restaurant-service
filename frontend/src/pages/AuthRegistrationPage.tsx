import React from 'react';
import {Card} from 'antd';
import FormRegistration from "../component/FormRegistration";

const AuthRegistrationPage: React.FC = () => {


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
                <FormRegistration/>
            </Card>
        </div>
    );
};

export default AuthRegistrationPage;
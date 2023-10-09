import React from 'react';
import {Layout, Menu, theme, Typography} from 'antd';
import {NotFoundPage} from "./pages/NotFoundPage";
import {Route, Routes, useNavigate} from "react-router-dom";
import RegistrationPage from "./pages/Registration";
import {CrownOutlined, ReadOutlined, UnorderedListOutlined, UserOutlined} from "@ant-design/icons";

const {
    Header,
    Content,
    Footer
} = Layout;

const isLoginIn = true;

const items = [
    {key: '1', icon: <UnorderedListOutlined/>, label: 'Orders'},
    {key: '2', icon: <ReadOutlined/>, label: 'Menu dishes'},
    {key: '3', icon: <UserOutlined/>, label: 'Account'},
    (isLoginIn ? {key: '4', icon: <CrownOutlined/>, label: 'Restaurant'} : null)]

const App: React.FC = () => {
    const {token: {colorBgContainer}} = theme.useToken();
    const navigate = useNavigate();

    const onClick = (e: any) => {
        if (e.key === "1") {
            console.log('click', e);
            navigate("/orders")

        } else if (e.key === "2") {
            console.log('click', e);
            navigate("/dishes");

        } else if (e.key === "3") {
            console.log('click', e);
            navigate("/user");

        } else if (e.key === "4") {
            console.log('click', e);
            navigate("/super-user");
        }
    };

    return (
        <Layout className="layout">
            <Header style={{display: 'flex', alignItems: 'center'}}>
                <div className="demo-logo"/>
                <Typography.Title level={5}
                                  style={{
                                      color: 'white',
                                      margin: 0,
                                      marginLeft: -30,
                                      marginRight: 10
                                  }}>Restaurant</Typography.Title>
                <Menu
                    theme="dark"
                    mode="horizontal"
                    defaultSelectedKeys={['2']}
                    onClick={onClick}
                    items={isLoginIn ? items : []}
                />
            </Header>
            <Content style={{padding: '0 50px'}}>
                <div className="site-layout-content" style={{background: colorBgContainer}}>
                    <Routes>
                        <Route index element={(isLoginIn ? <RegistrationPage/> : <NotFoundPage/>)}/>
                        <Route path="*" element={<NotFoundPage/>}/>
                    </Routes>
                </div>
            </Content>
            <Footer style={{textAlign: 'center'}}>Restoration Service ©2023</Footer>
        </Layout>
    );
};

export default App;
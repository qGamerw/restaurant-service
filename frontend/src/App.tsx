import React from 'react';
import {Avatar, Badge, Layout, Menu, theme, Typography} from 'antd';
import {Route, Routes, useNavigate} from "react-router-dom";
import RegistrationPage from "./pages/RegistrationPage";
import NotFoundPage from "./pages/NotFoundPage";
import {CrownOutlined, ReadOutlined, UnorderedListOutlined, UserOutlined} from "@ant-design/icons";
import OrderPage from "./pages/OrderPage";
import {useSelector} from "react-redux";

const {
    Header,
    Content,
    Footer
} = Layout;

const App: React.FC = () => {
    const {token: {colorBgContainer}} = theme.useToken();
    const navigate = useNavigate();
    // const location = useLocation();

    const isLoginIn = useSelector((state: any) => state.users.isAuth);
    const isAdmin = true;// useSelector((state: any) => state.users.user.position.position);

    const items = [
        {key: '1', icon: <UnorderedListOutlined/>, label: 'Orders'},
        {key: '2', icon: <ReadOutlined/>, label: 'Menu dishes'},
        {key: '3', icon: <UserOutlined/>, label: 'Account'},
        (isAdmin ? {key: '4', icon: <CrownOutlined/>, label: 'Restaurant'} : null)]

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
                    // defaultSelectedKeys={[location.pathname]}
                    defaultSelectedKeys={['2']}
                    onClick={onClick}
                    items={items}
                />
                <div style={{marginLeft: 'auto'}}>
                    <Badge count={10} size={'small'}>
                        <Avatar style={{backgroundColor: '#1677ff'}}>
                            U
                        </Avatar>
                    </Badge>
                </div>
            </Header>
            <Content style={{padding: '0 50px'}}>
                <div className="site-layout-content" style={{background: colorBgContainer}}>
                    <Routes>
                        <Route index element={<RegistrationPage/>}/>
                        <Route path="/orders" element={<OrderPage/>}/>

                        <Route path="*" element={<NotFoundPage/>}/>
                    </Routes>
                </div>
            </Content>
            <Footer style={{textAlign: 'center'}}>Restoration Service ©2023</Footer>
        </Layout>
    );
};

export default App;
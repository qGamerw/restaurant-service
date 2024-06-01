import React, {useEffect, useState} from 'react';
import {Avatar, Drawer, Layout, theme, Typography} from 'antd';
import {Route, Routes} from "react-router-dom";
import DishesPage from "./pages/DishesPage";
import NotFoundPage from "./pages/NotFoundPage";
import AuthLoginPage from "./pages/AuthLoginPage";
import AuthRegistrationPage from "./pages/AuthRegistrationPage";
import AuthResetPasswordPage from "./pages/AuthResetPasswordPage";
import AboutPage from "./pages/AboutPage";
import NavigateDrawerPage from "./component/NavigateDrawerPage";
import {authDatesSessionStorage, authRegistrationPath, authResetPasswordPath} from "./types/authType";
import {AccountUser, userDatesSessionStorage} from "./types/accountType";
import authService from "./services/authService";
import userAvatar from "./images/employee.png"
import {orderPath} from "./types/orderType";
import OrderPage from "./pages/OrderPage";

const {Header, Content, Footer,} = Layout;
const { Title } = Typography;

const App: React.FC = () => {
    const {token: {colorBgContainer,},} = theme.useToken();
    const [open, setOpen] = useState(false);
    const userDataString = sessionStorage.getItem(userDatesSessionStorage);
    const userData: AccountUser = userDataString ? JSON.parse(userDataString) : null;
    const checkAuthData = sessionStorage.getItem(authDatesSessionStorage) === null;

    const showDrawer = () => {
        setOpen(true);
    };

    const onClose = () => {
        setOpen(false);
    };

    useEffect(() => {
        const refreshInterval = setInterval(() => {
            refreshToken()
        }, 4 * 60 * 1000);
        return () => clearInterval(refreshInterval);
    }, [userDataString]);

    const refreshToken = () => {
        const authDate = sessionStorage.getItem(authDatesSessionStorage);

        let authObj = null;
        if (authDate) {
            authObj = JSON.parse(authDate);
            authService.authRefreshToken(authObj.refresh_token);
        }
    };

    return (
        !checkAuthData ?
            <Layout style={{minHeight: '100vh', overflow: 'hidden' }}>
                <div style={{ float: 'right' }}>
                    <Avatar src={userAvatar}
                        onClick={showDrawer}
                        style={{
                            backgroundColor: '#1677ff',
                            cursor: 'pointer',
                            marginTop: 20,
                            marginRight: 20,
                            width: 50,
                            height: 50,
                            float: 'right'
                        }} />
                </div>

                <Drawer
                    placement="right"
                    closable={false}
                    onClose={onClose}
                    open={open}
                >
                    <>
                        <Avatar src={userAvatar}
                            style={{
                                backgroundColor: '#1677ff',
                                cursor: 'pointer',
                                marginTop: -5,
                                marginBottom: 16,
                                width: 50,
                                height: 50,
                            }} />
                        <div style={{marginLeft: 60, marginTop: -90}}>
                            <Title level={5}>{userData.username}</Title>
                            <p style={{ marginTop: -5, fontSize: 12 }}>{userData.email}</p>
                        </div>
                        <NavigateDrawerPage setOpen={setOpen}/>
                    </>
                </Drawer>

                <Layout style={{ marginTop: -100 }}>
                    <Header style={{padding: 0, background: colorBgContainer, }}/>
                    <Content style={{margin: '0 16px'}}>
                        <Routes>
                            <Route index element={<DishesPage/>}/>
                            <Route path={orderPath} element={<OrderPage/>}/>

                            <Route path="/about" element={<AboutPage/>}/>
                            <Route path="*" element={<NotFoundPage/>}/>
                        </Routes>
                    </Content>
                    <Footer style={{textAlign: 'center'}}>
                        <a href={'/about'}>Restoration Service ©2023-2024</a>
                    </Footer>
                </Layout>
            </Layout>
            : <Routes>
                <Route index element={<AuthLoginPage/>}/>
                <Route path={authRegistrationPath} element={<AuthRegistrationPage/>}/>
                <Route path={authResetPasswordPath} element={<AuthResetPasswordPage/>}/>

                <Route path="/about" element={<AboutPage/>}/>
                <Route path="*" element={<NotFoundPage/>}/>
            </Routes>
    );
};

export default App;

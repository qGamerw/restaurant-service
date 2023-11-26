import React, {useEffect, useState} from 'react';
import {Avatar, Badge, Button, Layout, Menu, message, theme, Typography} from 'antd';
import {Route, Routes, useLocation, useNavigate} from 'react-router-dom';
import {useDispatch, useSelector} from 'react-redux';
import {CrownOutlined, ReadOutlined, UnorderedListOutlined, UserOutlined} from '@ant-design/icons';

import RegistrationPage from './pages/RegistrationPage';
import NotFoundPage from './pages/NotFoundPage';
import OrderPage from './pages/OrderPage';
import authService from './services/authService';
import DishesPage from './pages/DishesPage';
import {RootState} from './store';
import orderService from './services/orderService';
import AccountPage from "./pages/AccountPage";
import {Order} from "./types/types";
import AboutPage from "./pages/AboutPage";

const {Header, Content, Footer} = Layout;

const App: React.FC = () => {
    const {token: {colorBgContainer}} = theme.useToken();
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const location = useLocation();
    const [selectedKeys, setSelectedKeys] = useState<string>('2');
    const allOrders = useSelector((store: RootState) => store.orders.allOrders).filter(item => item.status === 'REVIEW');
    const isLoginIn = localStorage.getItem('user') !== null;
    const userDataString = localStorage.getItem('user');
    const userData = userDataString ? JSON.parse(userDataString) : null;
    const isAdmin = true; // localStorage.getItem('user') !== null && localStorage.user.position.position === 'ADMIN';
    const currentDate = new Date();


    function onCancel(id: string) {
        orderService.cancelOrderByListId(id, 'Waiting time exceeded', dispatch).then(() => {
            console.log('Success: cancel');
        });
    }

    function checkWaitingTime(order: Order[]) {
        const orderCheck: number[] = order
            .filter((item: Order) => {
                return 120 < Math.floor((currentDate.getTime() - new Date(item.orderTime).getTime()) / 60000);
            })
            .map((item: Order) => {
                return item.id
            });

        if (orderCheck.length !== 0) {
            onCancel(orderCheck.join(','));
        }
    }

    useEffect(() => {
        if (isLoginIn) {
            orderService.getListOrders(dispatch)
                .then(r => {
                    checkWaitingTime(allOrders.filter((item: Order) => item.status === 'REVIEW'))
                });

        }
    }, [dispatch, isLoginIn,]);

    useEffect(() => {
        switch (location.pathname) {
            case '/orders':
                setSelectedKeys('1');
                break;
            case '/dishes':
                setSelectedKeys('2');
                break;
            case '/user':
                setSelectedKeys('3');
                break;
            case '/super-user':
                setSelectedKeys('4');
                break;
            case '/':
                authService.logout();
                break;
            default:
                setSelectedKeys('');
                break;
        }
    }, [location, dispatch]);

    const items = [
        {key: '1', icon: <UnorderedListOutlined/>, label: 'Заказы'},
        {key: '2', icon: <ReadOutlined/>, label: 'Меню'},
        {key: '3', icon: <UserOutlined/>, label: 'Аккаунт'},
        isAdmin ? {key: '4', icon: <CrownOutlined/>, label: 'Restaurant'} : null,
    ];

    function onClick(e: any) {
        console.log('click', e);

        switch (e.key) {
            case '1':
                navigate('/orders');
                break;
            case '2':
                navigate('/dishes');
                break;
            case '3':
                navigate('/user');
                break;
            case '4':
                navigate('/super-user');
                break;
            default:
                break;
        }
    }

    return (
        <Layout className="layout" style={{minWidth: 1370}}>
            <Header style={{display: 'flex', alignItems: 'center'}}>
                <div className="demo-logo"/>
                <Typography.Title level={5} style={{
                    color: 'white',
                    margin: 0,
                    marginLeft: -30,
                    marginRight: 10,
                    cursor: 'pointer'
                }}
                                  onClick={() => {
                                      navigate('/dishes')
                                  }}>
                    Restaurant
                </Typography.Title>
                {isLoginIn && (
                    <Menu theme="dark" mode="horizontal" defaultSelectedKeys={[selectedKeys]} onClick={onClick}
                          items={items}/>
                )}
                {isLoginIn && (
                    <div style={{marginLeft: 'auto'}}>
                        <Badge count={allOrders.length} size="small">
                            <Avatar
                                onClick={() => {
                                    navigate('/user')
                                }}
                                style={{
                                    backgroundColor: '#1677ff',
                                    cursor: 'pointer'
                                }}>{userData ? userData.employeeName[0] : null} </Avatar>
                        </Badge>
                        <Button
                            type="primary"
                            style={{marginLeft: 20}}
                            onClick={() => {
                                authService.logout();
                                message.error('Вы вышли');
                                navigate('/');
                            }}
                        >
                            Выход
                        </Button>
                    </div>
                )}
            </Header>
            <Content style={{padding: '0 50px'}}>
                <div className="site-layout-content" style={{background: colorBgContainer}}>
                    <Routes>
                        <Route index element={<RegistrationPage/>}/>
                        <Route path="/orders" element={<OrderPage/>}/>
                        <Route path="/dishes" element={<DishesPage/>}/>
                        <Route path="/user" element={<AccountPage/>}/>
                        <Route path="/about" element={<AboutPage/>}/>

                        <Route path="*" element={<NotFoundPage/>}/>
                    </Routes>
                </div>
            </Content>
            <Footer style={{textAlign: 'center'}}><a onClick={() => {
                navigate('/about')
            }}>Restoration Service ©2023</a></Footer>
        </Layout>
    );
};

export default App;
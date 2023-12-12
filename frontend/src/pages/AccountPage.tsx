import React, {useEffect, useState} from 'react';
import {Avatar, Menu, MenuProps, message} from 'antd';
import {useDispatch} from "react-redux";
import account from "../employee.png"
import {AntDesignOutlined, AppstoreOutlined, MailOutlined, SettingOutlined} from '@ant-design/icons';
import orderService from "../services/orderService";
import analyticService from "../services/analyticsService";
import categoryService from "../services/categoryService";
import dishService from "../services/dishService";

type MenuItem = Required<MenuProps>['items'][number];

const AccountPage = () => {
    const dispatch = useDispatch();
    const [displayText, setDisplayText] = useState('');
    let count_order = 0;
    let count_order_per_mouth = 0;

    const items: MenuItem[] = [
        getItem('Достижения', 'sub1', <AppstoreOutlined/>),
        getItem('Новости', 'sub2', <MailOutlined/>),
        getItem('Настройки', 'sub3', <SettingOutlined/>),
    ];

    function getItem(
        label: React.ReactNode,
        key?: React.Key | null,
        icon?: React.ReactNode,
        children?: MenuItem[],
        type?: 'group',
    ): MenuItem {
        return {
            key,
            icon,
            children,
            label,
            type,
        } as MenuItem;
    }

    const onClick: MenuProps['onClick'] = (e) => {
        console.log('click', e);
    };

    useEffect(() => {
        analyticService.getCountOrderFromEmployeeRestaurant().then((length) => {
            count_order = length;
        });
    }, []);

    useEffect(() => {
        analyticService.getOrderPerMonth(null, null).then((length) => {
            count_order_per_mouth = length;
        });
    }, []);

    return (
        <>
            <Avatar
                size={150}
                icon={<AntDesignOutlined/>}
                style={{marginTop: 10, marginLeft: -40}}
                src={account}
            />

            <Menu onClick={onClick} style={{width: 256, marginLeft: -40}} mode="vertical" items={items}/>
            <>
                Количество заказов всего: {count_order}<br/>
                Количество заказов за месяц: {count_order_per_mouth}
            </>
        </>
    );
};

export default AccountPage;
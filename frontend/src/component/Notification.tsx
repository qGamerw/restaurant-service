import React, {useEffect} from 'react';
import {message} from 'antd';
import orderService from "../services/orderService";
import {useDispatch} from "react-redux";

const Notification: React.FC = () => {
    const dispatch = useDispatch();

// не смотрел

    useEffect(() => {
        const timer = setInterval(() => {
            orderService.getListOrdersByNotify(dispatch).then((length) => {
                if (length > 0) message.warning('Обновлено заказов: ' + length);
            });
        }, 30000);

        return () => {
            clearInterval(timer);
        };
    }, [dispatch]);


    return (
        < >
        </>
    );
};

export default Notification;
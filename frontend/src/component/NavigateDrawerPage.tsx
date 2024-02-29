import React from "react";
import {Button, message} from "antd";
import {Link} from "react-router-dom";
import authService from "../services/authService";

const NavigateDrawerPage: React.FC<{ setOpen: React.Dispatch<React.SetStateAction<boolean>> }> = ({setOpen}) => {
    function logOutUser() {
        message.warning('Вы вышли').then(() => {
            authService.authLogOut();
            window.location.reload();
        });
        setOpen(false)
    }

    return (
        <div style={{display: 'flex', flexDirection: 'column'}}>
            <Button type="primary" onClick={() => setOpen(false)} style={{ marginBottom: 16 }}>
                <Link to="/">Аккаунт</Link>
            </Button>

            <Button type="primary" onClick={() => setOpen(false)} style={{ marginBottom: 16 }}>
                <Link to="/">На главную - меню</Link>
            </Button>

            <Button type="primary" onClick={() => setOpen(false)} style={{ marginBottom: 16 }}>
                <Link to="/">Заказы</Link>
            </Button>

            <Button type="primary" onClick={logOutUser} style={{ marginBottom: 16 }}>
                Выход
            </Button>
        </div>
    );
};

export default NavigateDrawerPage;
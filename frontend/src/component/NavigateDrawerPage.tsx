import React, {useState} from "react";
import {Button, message} from "antd";
import {useNavigate} from "react-router-dom";
import authService from "../services/authService";
import {orderPath} from "../types/orderType";
import {dishPath} from "../types/dishType";
import AccountPage from "../pages/AccountPage";

const NavigateDrawerPage: React.FC<{ setOpen: React.Dispatch<React.SetStateAction<boolean>> }> = ({setOpen}) => {
    const navigate = useNavigate();
    const [modalOpen, setModalOpen] = useState(false);

    function logOutUser() {
        message.warning('Вы вышли').then(() => {
            authService.authLogOut();
            window.location.reload();
        });
        setOpen(false)
    }

    return (
        <div style={{display: 'flex', flexDirection: 'column'}}>
            <AccountPage modalOpen={modalOpen} setModalOpen={setModalOpen}/>
            <Button type="primary" onClick={() => {
                setOpen(false);
                setModalOpen(true);
            }} style={{marginBottom: 16}}>
                Аккаунт
            </Button>

            <Button type="primary" onClick={() => {
                setOpen(false);
                navigate(dishPath);
            }}
                    style={{marginBottom: 16}}
            >
                На главную - Меню
            </Button>

            <Button type="primary" onClick={() => {
                setOpen(false);
                navigate(orderPath);
            }}
                    style={{marginBottom: 16}}>
                Заказы
            </Button>

            <Button type="primary" onClick={logOutUser} style={{marginBottom: 16}}>
                Выход
            </Button>
        </div>
    );
};

export default NavigateDrawerPage;
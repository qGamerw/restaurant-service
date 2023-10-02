import {Button, message, Result} from "antd";
import {Link} from "react-router-dom";
import React from "react";

export const NotFoundPage = () => {
    const handleButtonClick = () => {
        setTimeout(() => {
            message.success('Привет!', 3);
        }, 100);
    };
    handleButtonClick();

    return (
        <Result
            status="404"
            title="404"
            subTitle="Я тоже не могу понять как такое могло произойти"
            extra={
                <div>
                    <Button type="primary">
                        <Link to="/">Назад на главную</Link>
                    </Button>
                </div>
            }
        />
    );
};

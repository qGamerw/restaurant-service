import React from 'react';
import {EChartOption} from "echarts";
import Chart from "./Chart";
import {Typography} from "antd";

const { Title } = Typography;

// не смотрел
const FormUserAchievements = () => {
    const data: EChartOption = {
        series: [
            {
                type: "pie",
                data: [
                    { value: 100, name: "Янв 7.88%" },
                    { value: 120, name: "Фев 9.46%" },
                    { value: 90, name: "Мар 7.09%" },
                    { value: 100, name: "Апр 7.88%" },
                    { value: 110, name: "Май 8.67%" },
                    { value: 110, name: "Июн 8.67%" },
                    { value: 120, name: "Июл 9.46%" },
                    { value: 115, name: "Авг 9.06%" },
                    { value: 113, name: "Сен 8.90%" },
                    { value: 100, name: "Окт 7.88%" },
                    { value: 100, name: "Ноя 7.88%" },
                    { value: 90, name: "Дек 7.09%" },
                ],
            },
        ],
    };

    const data2: EChartOption = {
        xAxis: {
            type: "category",
            data: ["За сегодня", "Норма"],
        },
        yAxis: {
            type: "value",
        },
        series: [
            {
                data: [12, 100],
                type: "bar",
            },
        ],
    };

    return (
        <div>
            <Title level={4}>Количество принятых заказов за год</Title>
            <Chart data={data} />

            <Title level={4}>Количество принятых заказов за месяц</Title>
            <Chart data={data2} />
        </div>
    );
};

export default FormUserAchievements;
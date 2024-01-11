import {EChartOption} from "echarts";
import ReactECharts from "echarts-for-react";
import React from "react";

interface ChartProps {
    data: EChartOption;
}

const Chart: React.FC<ChartProps> = ({ data }) => {
    return <ReactECharts option={data} />;
};

export default Chart;
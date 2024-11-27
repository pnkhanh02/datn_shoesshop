import axios from "axios";
import React, { useEffect, useState } from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from "recharts";

const BarChartExample = () => {
  const [data, setData] = useState([]);
  const userData = JSON.parse(localStorage.getItem("user"));
  const getMonthName = (monthNumber) => {
    const months = [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December",
    ];
    return months[monthNumber - 1];
  };
  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/orders/monthly`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      );
      const transformedData = response.data.map((item) => ({
        ...item,
        month: getMonthName(parseInt(item.month)),
      }));
      setData(transformedData);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  // Tính toán chiều cao dựa trên số lượng dữ liệu
  const minHeight = 400; // Chiều cao tối thiểu
  const calculatedHeight = Math.max(minHeight, data.length * 50); // Tính toán chiều cao

  // Tính toán domain cho trục Y (từ 0 đến giá trị lớn nhất của totalAmount)
  let maxTotalAmount = 0;
  if (data.length > 0) {
    maxTotalAmount = Math.max(
      ...data.map((item) => parseInt(item.totalAmount, 10))
    );
  }

  return (
    <div style={{ width: "100%", height: `${calculatedHeight}px` }}>
      <BarChart
        width={600}
        height={calculatedHeight}
        data={data}
        margin={{
          top: 0,
          right: 30,
          left: 20,
          bottom: 5,
        }}
        name="Revenue"
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="month" />
        <YAxis domain={[0, maxTotalAmount + 1000000]} />{" "}
        {/* Điều chỉnh domain của trục Y */}
        <Tooltip />
        <Legend />
        <Bar dataKey="totalAmount" fill="#8884d8" barCategoryGap="25%" />{" "}
        {/* Tăng khoảng cách giữa các cột */}
      </BarChart>
    </div>
  );
};

export default BarChartExample;

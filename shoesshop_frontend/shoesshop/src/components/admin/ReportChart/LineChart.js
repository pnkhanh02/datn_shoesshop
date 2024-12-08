import axios from "axios";
import React, { useEffect, useState } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from "recharts";

// Hàm chuyển đổi số tháng thành tiếng Anh
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

const LineChartExample = () => {
  const [data, setData] = useState([]);
  const [yDomain, setYDomain] = useState([0, 0]); // Tạo state để lưu domain Y
  const userData = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/orders/CountOrderMonthly`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      );

      // Chuyển đổi số tháng thành tên tiếng Anh
      const transformedData = response.data.map((item) => ({
        ...item,
        month: getMonthName(parseInt(item.month)),
      }));

      // Tính toán domain Y
      const maxOrder = Math.max(...transformedData.map((item) => item.order_Count));
      const minOrder = Math.min(...transformedData.map((item) => item.order_Count));
      setYDomain([Math.max(0, minOrder - 2), maxOrder + 2]); // Thêm khoảng đệm trên/dưới

      setData(transformedData);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  return (
    <LineChart
      width={700}
      height={400}
      data={data}
      margin={{ top: 0, right: 30, left: 20, bottom: 5 }}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="month" />
      <YAxis domain={yDomain}/>
      <Tooltip />
      <Legend />
      <Line
        type="monotone"
        dataKey="order_Count"
        stroke="#8884d8"
        activeDot={{ r: 8 }}
      />
    </LineChart>
  );
};

export default LineChartExample;

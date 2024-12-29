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
// const getMonthName = (monthNumber) => {
//   const months = [
//     "January",
//     "February",
//     "March",
//     "April",
//     "May",
//     "June",
//     "July",
//     "August",
//     "September",
//     "October",
//     "November",
//     "December",
//   ];
//   return months[monthNumber - 1];
// };

const normalizeData = (rawData, defaultKey) => {
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

  // Tạo dữ liệu mặc định cho 12 tháng
  const defaultData = months.map((month, index) => ({
    month,
    [defaultKey]: 0, // Giá trị mặc định là 0
  }));

  // Map dữ liệu trả về từ API vào dữ liệu mặc định
  rawData.forEach((item) => {
    const monthIndex = parseInt(item.month) - 1; // Lấy index từ số tháng
    if (defaultData[monthIndex]) {
      defaultData[monthIndex] = {
        ...defaultData[monthIndex],
        [defaultKey]: item[defaultKey] || 0,
      };
    }
  });

  return defaultData;
};

const LineChartExample = ({ year }) => {
  const [data, setData] = useState([]);
  const [yDomain, setYDomain] = useState([0, 0]); // Tạo state để lưu domain Y
  const userData = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    fetchData(year);
  }, [year]);

  const fetchData = async (selectedYear) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/orders/CountOrderMonthly?year=${selectedYear}`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      );

      // Chuyển đổi số tháng thành tên tiếng Anh
      // const transformedData = response.data.map((item) => ({
      //   ...item,
      //   month: getMonthName(parseInt(item.month)),
      // }));

      const transformedData = normalizeData(response.data, "order_Count"); // Chuẩn hóa dữ liệu
      setData(transformedData);

      // Tính toán domain Y
      const maxOrder = Math.max(
        ...transformedData.map((item) => item.order_Count)
      );
      const minOrder = Math.min(
        ...transformedData.map((item) => item.order_Count)
      );
      setYDomain([Math.max(0, minOrder - 2), maxOrder + 2]); // Thêm khoảng đệm trên/dưới

      setData(transformedData);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  return (
    <LineChart
      width={1100}
      height={400}
      data={data}
      margin={{ top: 0, right: 30, left: 20, bottom: 5 }}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="month" />
      <YAxis domain={yDomain} />
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

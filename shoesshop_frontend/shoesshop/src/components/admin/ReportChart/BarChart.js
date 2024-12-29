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

const BarChartExample = ({ year }) => {
  const [data, setData] = useState([]);
  const userData = JSON.parse(localStorage.getItem("user"));
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
  useEffect(() => {
    fetchData(year);
  }, [year]);

  const fetchData = async (selectedYear) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/orders/monthly?year=${selectedYear}`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      );
      // const transformedData = response.data.map((item) => ({
      //   ...item,
      //   month: getMonthName(parseInt(item.month)),
      // }));
      // setData(transformedData);

      const transformedData = normalizeData(response.data, "totalAmount"); // Chuẩn hóa dữ liệu
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
        width={1100}
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

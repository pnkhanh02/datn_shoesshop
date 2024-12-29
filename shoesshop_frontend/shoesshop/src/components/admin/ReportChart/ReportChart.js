import React, { useEffect, useState } from "react";
import "./ReportChart.css";
import LineChartExample from "./LineChart";
import BarChartExample from "./BarChart";

const ReportChart = () => {
  const [year, setYear] = useState(new Date().getFullYear()); // Lấy năm hiện tại làm mặc định
  useEffect(() => {
    window.scrollTo(0, 0); // Scroll to top when component mounts
  }, []);

  const handleYearChange = (event) => {
    setYear(event.target.value); // Cập nhật year khi người dùng chọn
  };

  return (
    <div className="ReportManager">
      {/* Dropdown chọn năm */}
      <div className="dropdown-container">
        <label htmlFor="year-select">Select Year: </label>
        <select id="year-select" value={year} onChange={handleYearChange}>
          {Array.from({ length: 10 }, (_, index) => {
            const yearOption = new Date().getFullYear() - index;
            return (
              <option key={yearOption} value={yearOption}>
                {yearOption}
              </option>
            );
          })}
        </select>
      </div>

      <div className="barchart">
        <h2>Doanh thu các tháng trong năm {year}</h2>
        <BarChartExample year={year} />
      </div>
      <div className="barchart">
        <h2>Số lượng đơn hàng các tháng trong năm {year}</h2>
        <LineChartExample year={year} />
      </div>
      {/* <RadialBarChartExample/> */}
    </div>
  );
};

export default ReportChart;

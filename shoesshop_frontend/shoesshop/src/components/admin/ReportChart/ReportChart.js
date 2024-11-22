import React, { useEffect } from 'react';
import "./ReportChart.css";
import LineChartExample from './LineChart';
import BarChartExample from './BarChart';

const ReportChart = () => {
    useEffect(() => {
        window.scrollTo(0, 0); // Scroll to top when component mounts
      }, []);
    
      return (
        <div className="ReportManager" >
    
            <div className="barchart"><h2>Doanh thu các tháng trong năm 2024</h2><BarChartExample /></div>
            <div className="barchart">
              <h2>Số lượng đơn hàng các tháng trong năm 2024</h2>
              <LineChartExample/>
              </div>
            {/* <RadialBarChartExample/> */}
        </div>
      );
}

export default ReportChart
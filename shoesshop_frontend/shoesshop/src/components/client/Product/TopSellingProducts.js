import React, { useEffect, useState } from "react";
import { Table, Button } from "antd";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const TopSellingProducts = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const userData = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    // Fetch data from the API
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await axios.get(
          "http://localhost:8080/api/v1/products/topSellingProduct", {
            headers: {
              Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
            },
          }
        );
        setData(
          response.data.map((item, index) => ({
            key: index + 1,
            order: index + 1,
            productName: item.productName,
            productUrl: item.productUrl,
            productPrice: item.productPrice,
            totalQuantitySold: item.totalQuantitySold,
            productId: item.productId,
          }))
        );
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  // Define table columns
  const columns = [
    {
      title: "Thứ tự",
      dataIndex: "order",
      key: "order",
      align: "center",
    },
    {
      title: "Tên sản phẩm",
      dataIndex: "productName",
      key: "productName",
    },
    {
      title: "Ảnh",
      dataIndex: "productUrl",
      key: "productUrl",
      render: (text) => (
        <img
          src={text}
          alt="Product"
          style={{ width: "50px", height: "50px", objectFit: "cover" }}
        />
      ),
    },
    {
      title: "Giá tiền",
      dataIndex: "productPrice",
      key: "productPrice",
      render: (text) => `${text.toLocaleString()} VND`,
      align: "right",
    },
    {
      title: "Số lượng",
      dataIndex: "totalQuantitySold",
      key: "totalQuantitySold",
      align: "right",
    },
    {
      title: "Action",
      key: "action",
      render: (_, record) => (
        <Button
          type="primary"
          onClick={() => navigate(`/client/product/${record.productId}`)} // Navigate to the product page
        >
          Chi tiết
        </Button>
      ),
      align: "center",
    },
  ];

  return (
    <div style={{ padding: "20px" }}>
      <h2>Top sản phẩm bán chạy nhất</h2>
      <Table
        columns={columns}
        dataSource={data}
        loading={loading}
        pagination={false}
        bordered
      />
    </div>
  );
};

export default TopSellingProducts;

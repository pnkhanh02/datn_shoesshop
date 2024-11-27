import React, { useEffect, useState } from "react";
import "./ManagerOrder.css";
import { Breadcrumb, Flex, message, Modal, Table } from "antd";
import axios from "axios";
import { Link } from "react-router-dom";

const ManagerOrder = () => {
  const { confirm } = Modal;
  const [messageApi, contextHolder] = message.useMessage();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(false);
  const [optionVisible, setOptionVisible] = useState(false);
  const adminData = JSON.parse(localStorage.getItem("user"));
  const userData = JSON.parse(localStorage.getItem("user"));
  const fetchData = () => {
    setLoading(true);
    axios
      .get("http://localhost:8080/api/v1/orders/getAll", {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })

      .then((response) => {
        console.log(response.data);
        const OrdersFormatted = response.data.map((order) => {
          return {
            order_id: order.id,
            total_amount: order.total_amount,
            oder_date: order.oder_date,
            oderStatus: order.oderStatus,
            customer_name: order.customer_name,
            employee_id: order.employeeId,
            address: order.address,
            phone: order.phone,
            paymentName: order.payment_method,
          };
          //   console.log("response :", response.data);
        });

        setOrders(OrdersFormatted);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  useEffect(() => {
    window.scrollTo(0, 0);

    fetchData();
  }, []);

  const columns = [
    {
      title: "ID",
      dataIndex: "order_id",
      render: (record) => (
        <Link to={`/admin/orders/checkOrder/${record}`}>{record}</Link>
      ),
    },
    {
      title: "Tổng tiền",
      dataIndex: "total_amount",
    },
    {
      title: "Ngày đặt",
      dataIndex: "oder_date",
    },
    {
      title: "Trạng thái",
      dataIndex: "oderStatus",
    },
    {
      title: "Khách hàng",
      dataIndex: "customer_name",
    },
    // {
    //   title: "Nhân viên",
    //   dataIndex: "employee_id",
    // },
    {
      title: "Địa chỉ",
      dataIndex: "address",
    },
    {
      title: "SDT",
      dataIndex: "phone",
    },
    {
      title: "Phương thức",
      dataIndex: "paymentName",
    },
  ];

  return (
    <Flex
      className="ProductManager"
      vertical="true"
      gap={20}
      style={{ position: "relative" }}
    >
      {contextHolder}
      <Breadcrumb
        items={[
          {
            title: "Quản lý",
          },
          {
            title: "Quản lý hóa đơn",
          },
        ]}
      />

      {optionVisible}
      <Table
        columns={columns}
        dataSource={orders}
        loading={loading}
        pagination={true}
        onRow={(record, rowIndex) => {
          return {
            onClick: (event) => {},
          };
        }}
        size="large"
      />
    </Flex>
  );
};

export default ManagerOrder;

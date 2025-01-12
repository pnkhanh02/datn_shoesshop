import { Breadcrumb, message, Modal, Table } from "antd";
import axios from "axios";
import moment from "moment";
import React, { useEffect, useState } from "react";
import "./OrderComfirmation.css";

const OrderComfirmation = () => {
  const { confirm } = Modal;
  const [messageApi, contextHolder] = message.useMessage();
  const [itemsData, setItemsData] = useState([]);
  const adminData = JSON.parse(localStorage.getItem("user"));
  const userData = JSON.parse(localStorage.getItem("user"));
  const [loading, setLoading] = useState(false);

  const fetchData = () => {
    setLoading(true);
    axios
      .get(
        "http://localhost:8080/api/v1/orders/getOrderToPayAndToReceiveAndCompleted",
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      )
      .then((response) => {
        const ordersFormatted = response.data.map((order) => ({
          order_id: order.id,
          employee_id: userData.id,
          customer_name: order.customer_name,
          phone: order.phone,
          status: order.orderStatus,
          order_date: moment(order.order_date).format("YYYY-MM-DD"),
        }))
        .sort((a, b) => b.order_id - a.order_id); // Sắp xếp giảm dần theo order_id
        console.log(response.data);
        setItemsData(ordersFormatted);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        setLoading(false);
      });
  };

  useEffect(
    () => {
      window.scrollTo(0, 0);
      fetchData();
    }
    , [userData.token]
  );

  const handleDelete = async (orderId) => {
    try {
      await axios.delete(
        `http://localhost:8080/api/v1/orders/delete/${orderId}`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      );
      setItemsData(itemsData.filter((order) => order.order_id !== orderId));
      message.success("Order deleted successfully");
    } catch (error) {
      console.error("Error deleting order: ", error);
      message.error("Error deleting order");
    }
  };

  const showDeleteConfirm = (orderId) => {
    confirm({
      title: "Xác nhận xóa?",
      // content: "This action cannot be undone.",
      okText: "Xóa",
      okType: "danger",
      cancelText: "Hủy",
      onOk() {
        handleDelete(orderId);
      },
      onCancel() {
        console.log("Cancel");
      },
    });
  };

  const handleUpdateStatus = async (orderId, newStatus) => {
    try {
      const response = await axios.put(
        `http://localhost:8080/api/v1/orders/changeStatus/${orderId}`,
        { employee_id: userData.id, orderStatus: newStatus },
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      );
      fetchData();

      const updatedOrder = response.data;
      setItemsData(
        itemsData.map((order) =>
          order.order_id === orderId
            ? { ...order, status: updatedOrder.orderStatus }
            : order
        )
      );
    } catch (error) {
      console.error("Error updating order status: ", error);
    }
  };

  const columns = [
    {
      title: "Order ID",
      dataIndex: "order_id",
    },
    {
      title: "Customer Name",
      dataIndex: "customer_name",
    },
    {
      title: "Phone",
      dataIndex: "phone",
    },
    {
      title: "Status",
      dataIndex: "status",
      filters: [
        { text: "TO_PAY", value: "TO_PAY" },
        { text: "TO_RECEIVE", value: "TO_RECEIVE" },
        { text: "COMPLETED", value: "COMPLETED" },
      ],
      onFilter: (value, record) => record.status === value,
    },
    {
      title: "Order Date",
      dataIndex: "order_date",
    },
    {
      title: "Actions",
      dataIndex: "actions",
      render: (_, record) => (
        <div className="actions">
          <button
            className="button1"
            onClick={() => handleUpdateStatus(record.order_id, "COMPLETED")}
          >
            Đã giao
          </button>
          <button
            className="button2"
            onClick={() => handleUpdateStatus(record.order_id, "TO_RECEIVE")}
          >
            Giao hàng
          </button>
          {/* <button
            className="button3"
            onClick={() => showDeleteConfirm(record.order_id)}
          >
            Xóa
          </button> */}
        </div>
      ),
    },
  ];

  return (
    <div className="order-comfirmation-container">
      {contextHolder}
      <Breadcrumb
        items={[
          {
            title: "Management",
          },
          {
            title: "Order Confirmation",
          },
        ]}
      />
      <Table
        columns={columns}
        dataSource={itemsData}
        loading={loading}
        rowKey="order_id"
        size="large"
      />
    </div>
  );
};

export default OrderComfirmation;

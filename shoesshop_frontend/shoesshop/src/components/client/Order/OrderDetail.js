import { Button, Flex, Image, message, Table, Tag } from "antd";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Feedback from "../Feedback/Feedback";

const userData = JSON.parse(localStorage.getItem("user"));

function convertDateTimeFormat(originalDateTimeString) {
  const originalDate = new Date(originalDateTimeString);
  const day = originalDate.getDate().toString().padStart(2, "0");
  const month = (originalDate.getMonth() + 1).toString().padStart(2, "0");
  const year = originalDate.getFullYear();
  const hours = originalDate.getHours().toString().padStart(2, "0");
  const minutes = originalDate.getMinutes().toString().padStart(2, "0");
  const formattedDateTime = `${day}/${month}/${year} ${hours}:${minutes}`;

  return formattedDateTime;
}

const OrderDetail = () => {
  const navigate = useNavigate();
  const { id } = useParams(); // Assuming you have a route parameter for the order ID
  const [ordereds, setOrdereds] = useState([]);
  const [loading, setLoading] = useState(false);
  const [totalAmount, setTotalAmount] = useState(0);
  const [showReviewColumn, setShowReviewColumn] = useState(false);
  const [orderData, setOrderData] = useState({});

  const [messageApi, contextHolder] = message.useMessage();

  const [isModalVisible, setIsModalVisible] = useState(false);
  const [orderId, setOrderId] = useState(null);
  const showModal = () => {
    setIsModalVisible(true);
  };

  const hideModal = () => {
    setIsModalVisible(false);
  };

  const onReview = (orderValue) => {
    showModal();
    setOrderData(orderValue);
  };

  const successMessage = (msg) => {
    messageApi.open({
      type: "success",
      content: msg,
    });
  };

  const errorMessage = (msg) => {
    messageApi.open({
      type: "error",
      content: msg,
    });
  };

  const columns = [
    {
      title: "Sản phẩm",
      dataIndex: "group",
      key: "group",
      render: (record) => (
        <Flex align="center" gap={15}>
          <Image
            width={60}
            src={record.url_img}
            style={{ borderRadius: "5px" }}
          />
          <Flex vertical>
            <h3>{record.product_detail_name}</h3>
            <span>{record.subQuantity} sản phẩm</span>
          </Flex>
        </Flex>
      ),
    },
    {
      title: "Ngày đặt hàng",
      dataIndex: "orderDate",
      key: "orderDate",
      render: (text) => convertDateTimeFormat(text),
    },
    {
      title: "Thành tiền",
      dataIndex: "totalAmount",
      key: "totalAmount",
      render: (text) => (
        <h3>{text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + "đ"}</h3>
      ),
    },
    {
      title: "Phương thức thanh toán",
      dataIndex: "paymentName",
      key: "paymentName",
    },
    {
      title: "Trạng thái",
      key: "status",
      dataIndex: "status",
      render: (text) => (
        <Tag color="blue">{text ? text.toUpperCase() : "UNKNOWN"}</Tag>
      ),
    },
    showReviewColumn && {
      title: "Đánh giá",
      render: (value, record) => {
        return (
          <Button
            color="blue"
            disabled={record.isFeedbackReceived}
            onClick={() => onReview(value)}
          >
            {record.isFeedbackReceived ? "Đã đánh giá" : "Đánh giá"}
          </Button>
        );
      },
    },
  ].filter(Boolean);

  const fetchOrderDetails = async () => {
    setLoading(true);
    try {
      const responseOrder = await axios.get(
        `http://localhost:8080/api/v1/orders/getOrderbyID/${id}`,
        {
          auth: {
            username: userData.username,
            password: userData.password,
          },
        }
      );

      const responseStatus = await axios.get(
        `http://localhost:8080/api/v1/orders/status/${userData.id}`,
        {
          auth: {
            username: userData.username,
            password: userData.password,
          },
        }
      );

      const orderItems = responseOrder.data;
      const orderStatus = responseStatus.data.find(
        (order) => order.idOrder === parseInt(id)
      );

      if (!orderStatus) {
        errorMessage("Order status not found.");
        setLoading(false);
        return;
      }

      const dataForState = orderItems.map((item) => ({
        group: {
          url_img: item.url_img,
          subQuantity: item.quantity,
          product_detail_name: item.product_detail_name,
          product_detail_id: item.product_detail_id,
        },
        status: orderStatus.oderStatus,
        totalAmount: item.subtotal,
        key: item.id,
        orderDate: orderStatus.orderDate,
        paymentName: orderStatus.paymentName,
        action: item.id,
        isFeedbackReceived: item.isFeedbackReceived,
      }));

      setOrdereds(dataForState);
      calculateTotalAmount(dataForState);

      // Kiểm tra nếu tất cả các đơn hàng đều có trạng thái "COMPLETED"
      const allCompleted = dataForState.every(
        (item) => item.status === "COMPLETED"
      );
      setShowReviewColumn(allCompleted);

      setLoading(false);
    } catch (error) {
      console.error("Error fetching order details:", error);
      errorMessage("Failed to fetch order details.");
      setLoading(false);
    }
  };

  const calculateTotalAmount = (data) => {
    const total = data.reduce((acc, curr) => acc + curr.totalAmount, 0);
    setTotalAmount(total);
  };

  useEffect(() => {
    if (userData?.role === "CUSTOMER") {
      fetchOrderDetails(id); // Fetch order details for the specific order ID
      setOrderId(id);
    } else {
      navigate("/");
    }
  }, [id]);

  const handleViewOrder = () => {
    navigate(-1);
  };

  return (
    <div className="TO_PAY">
      {contextHolder}
      {/* button quay lại */}
      <div>
        <Button
          style={{
            backgroundColor: "#1495E1",
            borderColor: "#1495E1",
            color: "white",
            marginBottom: 15,
            marginTop: 5,
            marginLeft: 5,
          }}
          onClick={() => handleViewOrder()}
        >
          Quay lại
        </Button>
      </div>
      {/* order detail */}
      <Table
        columns={columns}
        dataSource={ordereds}
        size="large"
        loading={loading}
      />
      <div
        style={{ marginTop: "20px", display: "flex", justifyContent: "center" }}
      >
        <h2>
          Tổng tiền:{" "}
          {totalAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")}đ
        </h2>
      </div>
      <Feedback
        visible={isModalVisible}
        hideModal={hideModal}
        orderData={orderData}
        fetchOrderDetails={fetchOrderDetails}
        orderId={orderId}
      />
    </div>
  );
};

export default OrderDetail;

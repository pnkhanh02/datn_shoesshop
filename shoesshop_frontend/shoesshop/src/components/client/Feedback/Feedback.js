import { Button, Form, message, Modal, Rate } from "antd";
import TextArea from "antd/es/input/TextArea";
import axios from "axios";
import moment from "moment";
import React, { useEffect, useState } from "react";

const Feedback = ({
  visible,
  hideModal,
  orderData,
  fetchOrderDetails,
  orderId,
}) => {
  const [rating, setRating] = useState(0);
  const [comment, setComment] = useState("");
  const [productName, setProductName] = useState("");
  const [productImage, setProductImage] = useState("");
  const [productPrice, setProductPrice] = useState("");
  const [productId, setProductId] = useState(null);

  const userData = JSON.parse(localStorage.getItem("user"));

  const handleRatingChange = (value) => {
    setRating(value);
  };

  const handleCommentChange = (e) => {
    setComment(e.target.value);
  };

  const fetchProductDetails = async () => {
    try {
      const { data } = await axios.get(
        `http://localhost:8080/api/v1/productDetails/${orderData.group.product_detail_id}`,
        {
          auth: {
            username: userData.username,
            password: userData.password,
          },
        }
      );
      setProductId(data.product_id);
    } catch (error) {
      console.error("Error fetching product details:", error);
      message.error("Failed to fetch product details. Please try again.");
    }
  };

  const handleSubmit = async () => {
    if (!comment || !rating) {
      message.error("Please complete all fields before submitting.");
      return;
    }

    const feedbackData = {
      comment: comment,
      rating: rating - 1,
      feedback_date: moment().format("YYYY-MM-DD"),
      customer_id: userData.id,
      product_id: productId,
      order_id: orderId,
    };

    try {
      await axios.post(
        "http://localhost:8080/api/v1/feedbacks/customer",
        feedbackData,
        {
          auth: {
            username: userData.username,
            password: userData.password,
          },
        }
      );
      fetchOrderDetails();
      message.success("Feedback created successfully!");
      hideModal();
    } catch (error) {
      console.error("Error creating feedback:", error);
      message.error("Failed to create feedback. Please try again.");
    }
  };

  useEffect(() => {
    const orderDataEmpty = Object.keys(orderData).length === 0;
    if (!orderDataEmpty) {
      setProductImage(orderData.group.url_img);
      setProductPrice(orderData.totalAmount);
      setProductName(orderData.group.product_detail_name);
      fetchProductDetails();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [orderData]);

  return (
    <Modal
      title="Feedback"
      visible={visible}
      onCancel={hideModal}
      footer={[
        <Button key="submit" type="primary" onClick={handleSubmit}>
          Submit
        </Button>,
      ]}
    >
      <div style={{ textAlign: "center" }}>
        <img
          src={productImage}
          alt={productName}
          style={{ width: "100px", height: "100px", objectFit: "cover" }}
        />
        <h2>{productName}</h2>
        <p>Price: ${productPrice} đ</p>
      </div>
      <Form layout="vertical">
        <Form.Item label="Rate">
          <Rate onChange={handleRatingChange} value={rating} />
        </Form.Item>
        <Form.Item label="Comment">
          <TextArea
            rows={4}
            placeholder="Leave your comment here..."
            value={comment}
            onChange={handleCommentChange}
            style={{ marginTop: "10px" }}
          />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default Feedback;

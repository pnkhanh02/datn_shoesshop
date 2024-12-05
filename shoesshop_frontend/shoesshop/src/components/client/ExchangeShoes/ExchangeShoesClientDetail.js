import React, { useEffect, useState } from "react";
import { useParams, useLocation } from "react-router-dom";
import { Form, Input, DatePicker, InputNumber, Button, message } from "antd";
import axios from "axios";
import "./ExchangeShoesClientDetail.css";
import { UploadOutlined, ArrowLeftOutlined } from "@ant-design/icons";
import moment from "moment";

const ExchangeShoesClientDetail = () => {
  const { id } = useParams(); // Lấy id từ URL
  const location = useLocation(); // Dữ liệu truyền qua state (nếu có)
  const [data, setData] = useState(null);
  const userData = JSON.parse(localStorage.getItem("user"));

  // Fetch data from API
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/exchange-shoes/${id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`,
        },
      })
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        message.error("Failed to fetch details");
      });
  }, [id, userData.token]);

  if (!data) return <p>Loading...</p>;

  return (
    <div className="exchange-shoes-detail-container">
      <Form.Item>
        <Button
          type="default"
          icon={<ArrowLeftOutlined />}
          onClick={() => window.history.back()}
        ></Button>
      </Form.Item>
      <h2>Chi tiết sản phẩm</h2>
      <Form layout="vertical" initialValues={data}>
        <Form.Item label="Tên giày" name="exchangeShoesName">
          <Input value={data.exchangeShoesName} readOnly />
        </Form.Item>
        <Form.Item label="Loại giày" name="exchangeShoesType">
          <Input value={data.type} readOnly />
        </Form.Item>
        <Form.Item label="Thời gian mua giày" name="purchaseDate">
          <Input value={data.purchaseDate} style={{ width: "100%" }} readOnly/>
        </Form.Item>
        <Form.Item label="Giá tiền (VNĐ)" name="price">
          <InputNumber value={data.price} readOnly style={{ width: "100%" }} />
        </Form.Item>
        <Form.Item label="Mô tả tình trạng giày" name="description">
          <Input.TextArea value={data.description} readOnly rows={4} />
        </Form.Item>
        <Form.Item label="Trạng thái" name="status">
          <Input value={data.status} readOnly />
        </Form.Item>
        <Form.Item label="Giảm giá" name="exchangeShoesSales">
          <Input value={data.exchangeShoesSales} readOnly />
        </Form.Item>
        <Form.Item label="Ảnh giày muốn đổi" name="img_url">
          {data.img_url ? (
            <img
              src={data.img_url}
              alt="Ảnh giày muốn đổi"
              style={{
                width: "100%",
                maxHeight: "300px",
                objectFit: "contain",
                borderRadius: "8px",
              }}
            />
          ) : (
            <p>Không có hình ảnh</p>
          )}
        </Form.Item>
      </Form>
    </div>
  );
};

export default ExchangeShoesClientDetail;

import React, { useEffect, useState } from "react";
import { useParams, useLocation } from "react-router-dom";
import {
  Form,
  Input,
  DatePicker,
  InputNumber,
  Button,
  message,
  Select,
} from "antd";
import axios from "axios";
import "./ExchangeShoesDetail.css";
import { UploadOutlined, ArrowLeftOutlined } from "@ant-design/icons";
import moment from "moment";

const ExchangeShoesDetail = () => {
  const { id } = useParams(); // Lấy id từ URL
  const [data, setData] = useState(null); // Dữ liệu giày
  const [isEditable, setIsEditable] = useState(false); // Trạng thái chỉnh sửa giảm giá
  const [form] = Form.useForm();
  const userData = JSON.parse(localStorage.getItem("user"));

  // Fetch data từ API
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/exchange-shoes/${id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`,
        },
      })
      .then((response) => {
        setData(response.data);
        form.setFieldsValue(response.data); // Đặt giá trị ban đầu cho form
      })
      .catch((error) => {
        message.error("Failed to fetch details");
      });
  }, [id, userData.token, form]);

  // Submit cập nhật
  const submitUpdate = () => {
    form
      .validateFields()
      .then((values) => {
        const payload = {
          status: values.status, // Giá trị trạng thái
          exchangeShoesSales: values.exchangeShoesSales || 0, // Giá trị giảm giá
        };

        axios
          .put(
            `http://localhost:8080/api/v1/exchange-shoes/update/${id}`,
            payload,
            {
              headers: {
                Authorization: `Bearer ${userData.token}`,
                "Content-Type": "application/json",
              },
            }
          )
          .then(() => {
            message.success("Cập nhật trạng thái thành công");
          })
          .catch((error) => {
            message.error("Cập nhật trạng thái thất bại");
            console.error(error);
          });
      })
      .catch(() => {
        message.error("Vui lòng kiểm tra lại thông tin");
      });
  };

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
      <Form
        layout="vertical"
        initialValues={data}
        form={form}
        onValuesChange={(changedValues, allValues) => {
          console.log("Changed Values:", changedValues);
          console.log("All Values:", allValues); // Debug dữ liệu khi thay đổi
        }}
      >
        <Form.Item label="Tên giày" name="exchangeShoesName">
          <Input disabled />
        </Form.Item>
        <Form.Item label="Loại giày" name="exchangeShoesType">
          <Input disabled />
        </Form.Item>
        <Form.Item label="Thời gian mua giày" name="purchaseDate">
          <Input disabled style={{ width: "100%" }} />
        </Form.Item>
        <Form.Item label="Giá tiền (VNĐ)" name="price">
          <InputNumber disabled style={{ width: "100%" }} />
        </Form.Item>
        <Form.Item label="Mô tả tình trạng giày" name="description">
          <Input.TextArea disabled rows={4} />
        </Form.Item>
        <Form.Item label="Trạng thái" name="status">
          <Select
            style={{ width: "100%" }}
            onChange={(value) => {
              form.setFieldsValue({ status: value }); // Cập nhật trạng thái vào form
              setIsEditable(value === "APPROVE"); // Bật/tắt giảm giá nếu là APPROVE
            }}
          >
            <Select.Option value="PENDING">PENDING</Select.Option>
            <Select.Option value="APPROVE">APPROVE</Select.Option>
            <Select.Option value="REJECT">REJECT</Select.Option>
          </Select>
        </Form.Item>
        <Form.Item
          label="Giảm giá"
          name="exchangeShoesSales"
          rules={[
            {
              required: isEditable,
              message: "Vui lòng nhập giảm giá khi trạng thái là APPROVE",
            },
          ]}
        >
          <Input disabled={!isEditable} style={{ width: "100%" }} />
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
        <Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            onClick={submitUpdate}
            style={{ marginTop: "20px", width: "100%" }}
          >
            Cập nhật trạng thái
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default ExchangeShoesDetail;

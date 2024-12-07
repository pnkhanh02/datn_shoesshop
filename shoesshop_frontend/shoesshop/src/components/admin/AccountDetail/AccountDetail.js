import React, { useEffect, useState } from "react";
import { Form, Input, DatePicker, Select, message, Button } from "antd";
import axios from "axios";
import moment from "moment";
import "./AccountDetail.css";
import { UploadOutlined, ArrowLeftOutlined } from "@ant-design/icons";
import { useParams } from "react-router-dom";

const { Option } = Select;

const AccountDetail = ({ customerId }) => {
  const { id } = useParams();
  const [form] = Form.useForm();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const userData = JSON.parse(localStorage.getItem("user"));

  // Fetch customer data by ID
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/accounts/${id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        message.error("Failed to fetch details");
      });
  }, [id, userData.token]);

  // Populate form when data changes
  useEffect(() => {
    if (data) {
      form.setFieldsValue({
        id: data.id,
        username: data.username,
        firstName: data.firstName,
        lastName: data.lastName,
        address: data.address,
        phone: data.phone,
        birthday: moment(data.birthday), // Convert to moment
        email: data.email,
        gender: data.gender,
        role: data.role,
        createdDate: data.createdDate,
      });
    }
  }, [data, form]);

  return (
    <div className="customer-detail-container">
      {data && data.id !== userData.id && (
        <Form.Item>
          <Button
            type="default"
            icon={<ArrowLeftOutlined />}
            onClick={() => window.history.back()}
          ></Button>
        </Form.Item>
      )}
      <h2
        style={{
          textAlign: "center", // Căn giữa theo chiều ngang
          marginBottom: "20px", // Khoảng cách dưới (tùy chỉnh nếu cần)
        }}
      >
        {data && data.id === userData.id
          ? "Thông tin tài khoản của tôi"
          : "Thông tin tài khoản"}
      </h2>
      <Form layout="vertical" className="customer-detail-form" form={form}>
        {/* ID Field */}
        <Form.Item label="ID" name="id">
          <Input disabled placeholder="Auto-generated ID" />
        </Form.Item>

        {/* Username Field */}
        <Form.Item label="Username" name="username">
          <Input disabled />
        </Form.Item>

        <Form.Item label="FirstName" name="firstName">
          <Input placeholder="Enter firstName" disabled />
        </Form.Item>

        <Form.Item label="LastName" name="lastName">
          <Input placeholder="Enter lastName" disabled />
        </Form.Item>

        <Form.Item label="Role" name="role">
          <Input disabled />
        </Form.Item>

        <Form.Item label="Phone" name="phone">
          <Input placeholder="Enter phone" disabled />
        </Form.Item>

        {/* Address Field */}
        <Form.Item label="Address" name="address">
          <Input
            placeholder="Enter address"
            // disabled={loading}
            disabled
          />
        </Form.Item>

        {/* Birthday Field */}
        <Form.Item label="Birthday" name="birthday">
          <DatePicker style={{ width: "100%" }} disabled />
        </Form.Item>

        {/* Email Field */}
        <Form.Item
          label="Email"
          name="email"
          rules={[
            {
              type: "email",
              message: "Please enter a valid email!",
            },
          ]}
        >
          <Input placeholder="Enter email" disabled />
        </Form.Item>

        {/* Gender Field */}
        <Form.Item label="Gender" name="gender">
          <Select placeholder="Select gender" disabled>
            <Option value="male">Male</Option>
            <Option value="female">Female</Option>
            <Option value="other">Other</Option>
          </Select>
        </Form.Item>

        {/* Created Date Field */}
        <Form.Item label="Created Date" name="createdDate">
          <Input disabled />
          {/* <DatePicker style={{ width: "100%" }} disabled /> */}
        </Form.Item>
      </Form>
    </div>
  );
};

export default AccountDetail;

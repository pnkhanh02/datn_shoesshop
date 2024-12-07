import React, { useEffect, useState } from "react";
import { Form, Input, DatePicker, Select, message } from "antd";
import axios from "axios";
import moment from "moment";
import "./CustomerDetail.css";
import { useParams } from "react-router-dom";

const { Option } = Select;

const CustomerDetail = ({ customerId }) => {
  const { id } = useParams();
  const [form] = Form.useForm();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const userData = JSON.parse(localStorage.getItem("user"));

  // Fetch customer data by ID
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/customers/${id}`, {
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
        birthday: moment(data.birthday), // Convert to moment
        email: data.email,
        gender: data.gender,
        createdDate: moment(data.createdDate), // Convert to moment
      });
    }
  }, [data, form]);

  return (
    <div className="customer-detail-container">
      <Form layout="vertical" className="customer-detail-form" form={form}>
        {/* ID Field */}
        <Form.Item label="ID" name="id">
          <Input disabled placeholder="Auto-generated ID" />
        </Form.Item>

        {/* Username Field */}
        <Form.Item
          label="Username"
          name="username"
          rules={[{ required: true, message: "Please enter username!" }]}
        >
          <Input placeholder="Enter username" disabled />
        </Form.Item>

        <Form.Item
          label="FirstName"
          name="firstName"
          rules={[{ required: true, message: "Please enter firstName!" }]}
        >
          <Input placeholder="Enter username" disabled />
        </Form.Item>

        <Form.Item
          label="LastName"
          name="lastName"
          rules={[{ required: true, message: "Please enter username!" }]}
        >
          <Input placeholder="Enter username" disabled />
        </Form.Item>

        {/* Address Field */}
        <Form.Item
          label="Address"
          name="address"
          rules={[{ required: true, message: "Please enter address!" }]}
        >
          <Input placeholder="Enter address" 
          // disabled={loading}
          disabled
           />
        </Form.Item>

        {/* Birthday Field */}
        <Form.Item
          label="Birthday"
          name="birthday"
          rules={[{ required: true, message: "Please select birthday!" }]}
        >
          <DatePicker style={{ width: "100%" }} disabled/>
        </Form.Item>

        {/* Email Field */}
        <Form.Item
          label="Email"
          name="email"
          rules={[
            {
              required: true,
              type: "email",
              message: "Please enter a valid email!",
            },
          ]}
        >
          <Input placeholder="Enter email" disabled />
        </Form.Item>

        {/* Gender Field */}
        <Form.Item
          label="Gender"
          name="gender"
          rules={[{ required: true, message: "Please select gender!" }]}
        >
          <Select placeholder="Select gender" disabled>
            <Option value="male">Male</Option>
            <Option value="female">Female</Option>
            <Option value="other">Other</Option>
          </Select>
        </Form.Item>

        {/* Created Date Field */}
        <Form.Item label="Created Date" name="createdDate">
          <DatePicker style={{ width: "100%" }} disabled />
        </Form.Item>
      </Form>
    </div>
  );
};

export default CustomerDetail;

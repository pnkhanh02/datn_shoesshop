import React, { useState } from "react";
import dayjs from "dayjs";
import { CheckCircleOutlined } from "@ant-design/icons";
import customParseFormat from "dayjs/plugin/customParseFormat";
import { Button, DatePicker, Input, notification, Radio } from "antd";
import axios from "axios";
import "./Signup.css";
import { Link } from "react-router-dom";

const Signup = () => {
  dayjs.extend(customParseFormat);
  const dateFormat = "YYYY/MM/DD";

  const [formData, setFormData] = useState({
    username: "",
    password: "",
    firstName: "",
    lastName: "",
    address: "",
    birthday: dayjs("2015/01/01", dateFormat),
    email: "",
    phone: "",
    gender: "",
  });

  const [errors, setErrors] = useState({});

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleDateChange = (date) => {
    setFormData({
      ...formData,
      birthday: date,
    });
  };

  const handleRadioChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const validate = () => {
    let tempErrors = {};
    tempErrors.username = formData.username ? "" : "Nhập tên đăng nhập!";
    tempErrors.password = formData.password ? "" : "Nhập mật khẩu!";
    tempErrors.firstName = formData.firstName ? "" : "Nhập họ và tên lót!";
    tempErrors.lastName = formData.lastName ? "" : "Nhập tên!";
    tempErrors.address = formData.address ? "" : "Nhập địa chỉ!";
    tempErrors.phone = formData.phone ? "" : "Nhập số điện thoại!";
    tempErrors.birthday = formData.birthday ? "" : "Chọn ngày sinh!";
    tempErrors.email = formData.email ? "" : "Nhập email!";
    tempErrors.gender = formData.gender ? "" : "Chọn giới tính!";
    setErrors(tempErrors);
    return Object.values(tempErrors).every((x) => x === "");
  };

  const showSuccessNotification = () => {
    notification.success({
      message: "Đăng ký thành công",
      description: "Tài khoản đã được tạo thành công.",
      icon: <CheckCircleOutlined style={{ color: "#52c41a" }} />,
    });
  };

  const onFinish = async () => {
    if (validate()) {
      const newAccount = {
        username: formData.username,
        password: formData.password,
        firstName: formData.firstName,
        lastName: formData.lastName,
        address: formData.address,
        birthday: formData.birthday.format("YYYY-MM-DD"),
        email: formData.email,
        phone: formData.phone,
        gender: formData.gender,
        role: "CUSTOMER",
      };

      try {
        const response = await axios.post(
          "http://localhost:8080/api/v1/customers/register",
          newAccount
        );
        console.log("API Response:", response.data);
        showSuccessNotification();
        window.scrollTo(0, 0); // Cuộn trang lên đầu sau khi đăng ký thành công
      } catch (error) {
        console.error(
          "Đăng ký không thành công",
          error.response?.data || error.message
        );
        notification.error({
          message: "Đăng ký không thành công",
        });
        window.scrollTo(0, 0); // Cuộn trang lên đầu nếu đăng ký thất bại
      }
    }
  };

  return (
    <div className="Signup-form">
      <div className="Signup">
        <div className="Signup_container">
          <div className="Signup_form-item">
            <Input
              name="username"
              placeholder="Tên đăng nhập"
              size="large"
              value={formData.username}
              onChange={handleInputChange}
            />
            {errors.username && (
              <span className="Signup_error">{errors.username}</span>
            )}
          </div>
          <div className="Signup_form-item">
            <Input
              name="password"
              type="password"
              placeholder="Mật khẩu"
              size="large"
              value={formData.password}
              onChange={handleInputChange}
            />
            {errors.password && (
              <span className="Signup_error">{errors.password}</span>
            )}
          </div>
          <div className="Signup_form-item">
            <Input
              name="firstName"
              placeholder="Họ và tên đệm"
              size="large"
              value={formData.firstName}
              onChange={handleInputChange}
            />
            {errors.firstName && (
              <span className="Signup_error">{errors.firstName}</span>
            )}
          </div>
          <div className="Signup_form-item">
            <Input
              name="lastName"
              placeholder="Tên"
              size="large"
              value={formData.lastName}
              onChange={handleInputChange}
            />
            {errors.lastName && (
              <span className="Signup_error">{errors.lastName}</span>
            )}
          </div>
          <div className="Signup_form-item">
            <Input
              name="address"
              placeholder="Địa chỉ"
              size="large"
              value={formData.address}
              onChange={handleInputChange}
            />
            {errors.address && (
              <span className="Signup_error">{errors.address}</span>
            )}
          </div>
          <div className="Signup_form-item">
            <Input
              name="phone"
              placeholder="Số điện thoại"
              size="large"
              value={formData.phone}
              onChange={handleInputChange}
            />
            {errors.phone && (
              <span className="Signup_error">{errors.phone}</span>
            )}
          </div>
          <div className="Signup_form-item">
            <DatePicker
              name="birthday"
              format={dateFormat}
              placeholder="Ngày sinh"
              value={formData.birthday}
              onChange={handleDateChange}
              style={{ width: "100%" }}
            />
            {errors.birthday && (
              <span className="Signup_error">{errors.birthday}</span>
            )}
          </div>
          <div className="Signup_form-item">
            <Input
              name="email"
              placeholder="Email"
              size="large"
              value={formData.email}
              onChange={handleInputChange}
            />
            {errors.email && (
              <span className="Signup_error">{errors.email}</span>
            )}
          </div>
          <div className="Signup_form-item">
            <Radio.Group
              name="gender"
              value={formData.gender}
              onChange={handleRadioChange}
              style={{ width: "100%" }}
            >
              <Radio value={"FEMALE"}>FEMALE</Radio>
              <Radio value={"MALE"}>MALE</Radio>
              <Radio value={"UNKNOWN"}>UNKNOWN</Radio>
            </Radio.Group>
            {errors.gender && (
              <span className="Signup_error">{errors.gender}</span>
            )}
          </div>
          <div className="Signup_form-item">
            <Button type="primary" size="large" onClick={onFinish}>
              Đăng ký
            </Button>
          </div>
          <p>
            Đã có tài khoản? Hãy <Link to="/login">Đăng nhập!</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Signup;

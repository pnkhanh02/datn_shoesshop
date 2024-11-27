import { Button, Checkbox, Form, Input, message } from "antd";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Login.css";
import "../../images/shoes_2.png";
import React, { useState } from "react";
import { LockOutlined, UserOutlined } from "@ant-design/icons";

const Login = (props) => {
  //const { success = () => {}, errorMessage = () => {} } = props;
  const navigate = useNavigate();

  const [messageApi, contextHolder] = message.useMessage();

  const success = () => {
    messageApi.open({
      type: "success",
      content: "Đăng nhập thành công",
      duration: 1
    });
  };

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Đăng nhập thất bại",
    });
  };

  const onFinish = async (values) => {
    const { username, password } = values;

    try {
      // Gửi yêu cầu đăng nhập
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/login-jwt",
        {
          username: username,
          password: password,
        }
      );

      // Kiểm tra nếu phản hồi có token (bạn có thể tùy chỉnh logic kiểm tra)
      if (response.data && response.data.token) {
        const userData = {
          ...response.data,
          password, // Lưu password (nếu cần thiết, nhưng nên tránh vì lý do bảo mật)
          username,
        };
        
        // Lưu thông tin user vào localStorage
        localStorage.setItem("user", JSON.stringify(userData));
        
        success();
        console.log("Đăng nhập thành công");
        setTimeout(() => {
          navigate("/client/");
        }, 1000);
        //navigate("/client/");
        //window.location.reload();
      } else {
        throw new Error("Phản hồi không hợp lệ từ server");
      }
    } catch (error) {
      // Hiển thị lỗi nếu xảy ra
      console.log("Đăng nhập không thành công");
      errorMessage();
    }
  };

  return (
    <>
      {contextHolder}
      <div className="Login">
        {/* <div className="overplay"></div> */}
        <div className="Login_container">
          <Form
            name="normal_login"
            className="login-form"
            initialValues={{
              remember: true,
            }}
            onFinish={onFinish}
          >
            <Form.Item>
              <h2 style={{ width: "90%", textAlign: "center" }}>Đăng nhập</h2>
            </Form.Item>
            <Form.Item
              name="username"
              rules={[
                {
                  required: true,
                  message: "Nhập tên đăng nhập!",
                },
              ]}
            >
              <Input
                prefix={<UserOutlined className="site-form-item-icon" />}
                placeholder="Tên đăng nhập"
                size="large"
              />
            </Form.Item>
            <Form.Item
              name="password"
              rules={[
                {
                  required: true,
                  message: "Nhập mật khẩu!",
                },
              ]}
            >
              <Input
                prefix={<LockOutlined className="site-form-item-icon" />}
                type="password"
                placeholder="Mật khẩu"
                size="large"
              />
            </Form.Item>
            <Form.Item>
              <Form.Item name="remember" valuePropName="checked" noStyle>
                <Checkbox>Nhớ lần đăng nhập này</Checkbox>
              </Form.Item>

              <a className="login-form-forgot" href="">
                Quên mật khẩu?
              </a>
            </Form.Item>
            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                className="login-form-button"
                size="large"
                style={{
                  width: "100%",
                  backgroundColor: "black",
                  color: "white",
                }}
              >
                Đăng nhập
              </Button>
            </Form.Item>
            Hoặc <a>Đăng ký!</a>
          </Form>
        </div>
      </div>
    </>
  );
};

export default Login;

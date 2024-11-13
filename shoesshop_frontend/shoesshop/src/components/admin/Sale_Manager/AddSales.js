import {
  Breadcrumb,
  Button,
  DatePicker,
  Flex,
  Form,
  Input,
  message,
  Spin,
} from "antd";
import axios from "axios";
import React, { useState } from "react";

const AddSales = () => {
  const [loading, setLoading] = useState(false);
  const [messageApi, contextHolder] = message.useMessage();

  const successMessage = () => {
    messageApi.open({
      type: "success",
      content: "Thêm khuyến mãi thành công",
    });
  };

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Thêm khuyến mãi thất bại",
    });
  };

  const handleCreateSales = async (data) => {
    //const adminData = JSON.parse(localStorage.getItem("user"));

    axios
      .post("http://localhost:8080/api/v1/sales/create", data
      //   , {
      //   auth: {
      //     username: adminData.username,
      //     password: adminData.password,
      //   },
      // }
    )
      .then((response) => {
        console.log("Add sales successfully");
        successMessage();
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        errorMessage();
      });
  };

  const onFinish = async (values) => {
    setLoading(true);
    const newSales = {
      sale_info: values.sale_info,
      percent_sale: values.percent_sale,
      start_date: values.start_date,
      end_date: values.end_date,
    };
    handleCreateSales(newSales);
    setLoading(false);
  };

  return (
    <Flex className="AddSales" vertical="true" gap={50}>
      {contextHolder}
      {/* title của page */}
      <Breadcrumb
        items={[
          {
            title: "Quản lý",
          },
          {
            title: "Thêm khuyến mãi",
          },
        ]}
      />

      {loading && <Spin />}

      {/* form */}
      <Form
        name="add-sales-form"
        labelCol={{
          span: 10,
        }}
        wrapperCol={{
          span: 18,
        }}
        layout="horizontal"
        style={{
          maxWidth: 800,
        }}
        onFinish={onFinish}
        disabled={loading}
      >
        {/* Tên khuyến mãi */}
        <Form.Item
          label="Tên khuyến mãi"
          name="sale_info"
          rules={[
            {
              required: true,
              message: "Nhập tên khuyến mãi!",
            },
          ]}
        >
          <Input />
        </Form.Item>

        {/* Phần trăm khuyến mãi */}
        <Form.Item
          label="Phần trăm khuyến mãi"
          name="percent_sale"
          rules={[
            {
              required: true,
              message: "Nhập phần trăm khuyến mãi!",
            },
          ]}
        >
          <Input />
        </Form.Item>

        {/* Ngày bắt đầu */}
        <Form.Item
          label="Ngày bắt đầu"
          name="start_date"
          rules={[
            {
              required: true,
              message: "Nhập ngày bắt đầu!",
            },
          ]}
        >
          <DatePicker />
        </Form.Item>

        {/* Ngày kết thúc */}
        <Form.Item
          label="Ngày kết thúc"
          name="end_date"
          rules={[
            {
              required: true,
              message: "Nhập ngày kết thúc!",
            },
          ]}
        >
          <DatePicker />
        </Form.Item>

        {/* button submit */}
        <Form.Item wrapperCol={{ offset: 10 }}>
          <Button type="primary" htmlType="submit">
            Thêm khuyến mãi
          </Button>
        </Form.Item>
      </Form>
    </Flex>
  );
};

export default AddSales;

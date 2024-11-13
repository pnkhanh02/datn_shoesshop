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
import moment from "moment";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

const UpdateSales = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const [currentUser, setCurrentUser] = useState(
    JSON.parse(localStorage.getItem("user"))
  );
  const [salesData, setSalesData] = useState({});
  const [messageApi, contextHolder] = message.useMessage();
  const [form] = Form.useForm();

  const successMessage = () => {
    messageApi.open({
      type: "success",
      content: "Cập nhật khuyến mãi thành công",
    });
    navigate("/admin/sales");
  };

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Cập nhật khuyến mãi thất bại",
    });
    navigate("/admin/sales");
  };

  const fetchSalesData = async () => {
    setLoading(true);
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/sales/${id}`,
        {
          auth: {
            username: currentUser.username,
            password: currentUser.password,
          },
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = response.data;
      setSalesData(data);
      form.setFieldsValue({
        sale_info: data.sale_info,
        percent_sale: data.percent_sale,
        start_date: data.start_date ? moment(data.start_date) : null,
        end_date: data.end_date ? moment(data.end_date) : null,
      });
    } catch (error) {
      console.error("Error fetching sales data:", error);
      errorMessage();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSalesData();
  }, [id]);

  const handleUpdateSales = async (data) => {
    axios
      .put(`http://localhost:8080/api/v1/sales/update/${id}`, data, {
        auth: {
          username: currentUser.username,
          password: currentUser.password,
        },
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        console.log("Update sales successfully");
        successMessage();
      })
      .catch((error) => {
        console.error("Error updating sales:", error);
        errorMessage();
      });
  };

  const onFinish = async (values) => {
    setLoading(true);
    const newSales = {
      sale_info: values.sale_info,
      percent_sale: values.percent_sale,
      start_date: values.start_date.format("YYYY-MM-DD"),
      end_date: values.end_date.format("YYYY-MM-DD"),
    };
    await handleUpdateSales(newSales);
    setLoading(false);
  };
  return (
    <Flex className="UpdateSales" vertical="true" gap={50}>
      {contextHolder}
      {/* title của page */}
      <Breadcrumb
        items={[
          {
            title: "Quản lý",
          },
          {
            title: (
              <Link to="/admin/sales">
                <span>Quản lý khuyến mãi</span>
              </Link>
            ),
          },
          {
            title: "Cập nhật khuyến mãi",
          },
        ]}
      />

      {loading && <Spin />}

      {/* form */}
      <Form
        form={form}
        name="update-sales-form"
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
            Cập nhật khuyến mãi
          </Button>
        </Form.Item>
      </Form>
    </Flex>
  );
};

export default UpdateSales;

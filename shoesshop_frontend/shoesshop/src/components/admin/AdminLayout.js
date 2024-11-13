import React from "react";
import "./AdminLayout.css";
import { Button, Flex, Layout, Menu, Space, Typography } from "antd";
import {
  Routes,
  Route,
  Link,
  useLocation,
  useNavigate,
} from "react-router-dom";

import {
  DashboardOutlined,
  UserOutlined,
  SettingOutlined,
  TableOutlined,
  UserAddOutlined,
  TeamOutlined,
  BuildOutlined,
  PlusOutlined,
  BarsOutlined,
  FileTextOutlined,
  PercentageOutlined,
} from "@ant-design/icons";
import SalesManager from "./Sale_Manager/SalesManager";
import AddSales from "./Sale_Manager/AddSales";
import UpdateSales from "./Sale_Manager/UpdateSales";

const { Sider, Content } = Layout;

const AdminLayout = () => {
  return (
    <>
      <Layout className="AdminLayout">
        <Sider width={280} theme="dark" style={{ padding: 10 }}>
          <Flex justify="space-between" align="center">
            <h1 className="AdminLayout_title">SHOES SHOP</h1>
            <Button type="text" style={{ color: "white" }}>
              <Link to="/">
                <i class="fa-solid fa-arrow-up-right-from-square"></i>
              </Link>
            </Button>
          </Flex>
          <Menu theme="dark" defaultSelectedKeys={["1"]} mode="inline">
            <Menu.Item key="1" icon={<DashboardOutlined />}>
              <Link to="reports">Báo cáo thống kê</Link>
            </Menu.Item>
            <Menu.SubMenu key="UM" icon={<UserOutlined />} title="Người dùng">
              <Menu.Item key="users" icon={<BarsOutlined />}>
                <Link to="users">Quản lý người dùng</Link>
              </Menu.Item>

              <Menu.Item key="feedback-management" icon={<BarsOutlined />}>
                <Link to="feedback-management">Quản lý feedback</Link>
              </Menu.Item>
              <Menu.Item key="users/add" icon={<PlusOutlined />}>
                <Link to="users/add">Thêm mới nhân sự</Link>
              </Menu.Item>
            </Menu.SubMenu>
            <Menu.SubMenu
              key="UM2"
              icon={<FileTextOutlined />}
              title="Đơn hàng"
            >
              <Menu.Item key="orders" icon={<BarsOutlined />}>
                <Link to="orders">Quản lý đơn hàng</Link>
              </Menu.Item>
              <Menu.Item key="payment" icon={<BarsOutlined />}>
                <Link to="payment">Kiểu thanh toán</Link>
              </Menu.Item>
              <Menu.Item key="orders_comfirm" icon={<PlusOutlined />}>
                <Link to="orders-comfirm">Xác nhận đơn hàng</Link>
              </Menu.Item>
            </Menu.SubMenu>
            <Menu.SubMenu key="PM" icon={<BuildOutlined />} title="Sản phẩm">
              <Menu.Item key="products/add_product" icon={<PlusOutlined />}>
                <Link to="products/add-product">Thêm sản phẩm</Link>
              </Menu.Item>
              <Menu.Item key="products" icon={<BarsOutlined />}>
                <Link to="products">Quản lý sản phẩm</Link>
              </Menu.Item>
              <Menu.Item key="product_type" icon={<BarsOutlined />}>
                <Link to="products-type">Loại sản phẩm</Link>
              </Menu.Item>
              <Menu.Item key="product_detail" icon={<BarsOutlined />}>
                <Link to="products-detail">Chi tiết sản phẩm</Link>
              </Menu.Item>
            </Menu.SubMenu>
            <Menu.SubMenu
              key="sale"
              icon={<PercentageOutlined />}
              title="Khuyến mãi"
            >
              <Menu.Item key="sales/create" icon={<PlusOutlined />}>
                <Link to="sales/create">Thêm khuyến mãi</Link>
              </Menu.Item>
              <Menu.Item key="sales" icon={<BarsOutlined />}>
                <Link to="sales">Quản lý khuyến mãi</Link>
              </Menu.Item>
            </Menu.SubMenu>
          </Menu>
        </Sider>
        <Layout>
          <Content className="AdminContent">
            <Routes>
              <Route path="/sales/create" element={<AddSales />}></Route>
              <Route path="/sales/" element={<SalesManager />}></Route>
              <Route path="/sales/:id" element={<UpdateSales />}></Route>
            </Routes>
          </Content>
        </Layout>
      </Layout>
    </>
  );
};

export default AdminLayout;

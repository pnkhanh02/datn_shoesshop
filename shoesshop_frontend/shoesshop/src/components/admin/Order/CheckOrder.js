import { Breadcrumb, Flex, Space, Table } from "antd";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

const CheckOrder = () => {
  const { id } = useParams();

  const [orderDetail, setorderDetail] = useState([]);
  const adminData = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    window.scrollTo(0, 0);

    axios
      .get(
        `http://localhost:8080/api/v1/orders/getOrderbyID/${id}`
        //     , {
        //   auth: {
        //     username: adminData.username,
        //     password: adminData.password,
        //   },
        // }
      )
      .then((response) => {
        const data = response.data.map((pd, index) => {
          return {
            detail_id: pd.id,
            sell_price: pd.sell_price,
            subtotal: pd.subtotal,
            quantity: pd.quantity,
            order_id: pd.order_id,
            product_detail_id: pd.product_detail_id,
            product_detail_name: pd.product_detail_name,
            size: pd.size,
            color: pd.color,
            url_img: pd.url_img,

            // fhdsfakhd
          };
        });

        setorderDetail(data);
        // console.log(response.data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  });

  const columns = [
    {
      title: "ID",
      dataIndex: "detail_id",
    },
    {
      title: "Ảnh",
      dataIndex: "url_img",
      render: (text) => <img src={text} alt="" width={80} />,
    },
    {
      title: "Tên sản phẩm",
      dataIndex: "product_detail_name",
    },
    {
      title: "Màu sắc",
      dataIndex: "color",
    },
    {
      title: "Size",
      dataIndex: "size",
    },
    {
      title: "Số lượng",
      dataIndex: "quantity",
    },
    {
      title: "Gía bán",
      dataIndex: "sell_price",
    },
  ];

  return (
    <Flex className="ProductDetailManagerById" vertical="true" gap={40}>
      <Breadcrumb
        items={[
          {
            title: "Quản lý",
          },
          {
            title: (
              <Link to="/admin/orders">
                <span>Quản lý đơn hàng</span>
              </Link>
            ),
          },
          {
            title: "Chi tiết đơn hàng",
          },
          {
            title: `${id}`,
          },
        ]}
      />
      <Space direction="vertical" size="large">
        <Table columns={columns} dataSource={orderDetail} pagination={true} />
      </Space>
    </Flex>
  );
};

export default CheckOrder;

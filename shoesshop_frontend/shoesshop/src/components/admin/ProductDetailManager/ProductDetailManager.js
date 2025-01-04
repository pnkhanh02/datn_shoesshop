import { Breadcrumb, Flex, Table } from "antd";
import axios from "axios";
import React, { useEffect, useState } from "react";

const ProductDetailManager = () => {
  const [productDetails, setProductDetails] = useState([]);
  const [currentUser, setCurrentUser] = useState(
    JSON.parse(localStorage.getItem("user"))
  );
  const userData = JSON.parse(localStorage.getItem("user"));
  useEffect(() => {
    window.scrollTo(0, 0);

    axios
      .get("http://localhost:8080/api/v1/products/full",
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      )
      .then((response) => {
        const product_arr = response.data;

        axios
          .get(
            `http://localhost:8080/api/v1/productDetails?page=1&size=999&sort=id,asc&search=`
            ,
            {
              headers: {
                Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
              },
            }
          )
          .then((response) => {
            const data = response.data.content.map((pd, index) => {
              return {
                color: pd.color,
                size: pd.size,
                quantity: pd.quantity,
                key: index,
                name: product_arr.filter((p) => {
                  return p.id === pd.product_id;
                })[0].name,
                image: pd.img_url,
              };
            });

            setProductDetails(data);
            // console.log(response.data);
          })
          .catch((error) => {
            console.error("Error fetching data:", error);
          });
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  const columns = [
    {
      title: "Tên sản phẩm",
      dataIndex: "name",
      render: (text) => <a>{text}</a>,
    },
    {
      title: "Ảnh",
      dataIndex: "image",
      render: (text) => <img src={text} alt="" width={80} />,
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
      sorter: (a, b) => a.quantity - b.quantity,
      width: 160,
    },
  ];

  const rowSelection = {
    onChange: (selectedRowKeys, selectedRows) => {
      console.log(
        `selectedRowKeys: ${selectedRowKeys}`,
        "selectedRows: ",
        selectedRows
      );
    },
    getCheckboxProps: (record) => ({
      disabled: record.name === "Disabled User",
      // Column configuration not to be checked
      name: record.name,
    }),
  };

  return (
    <Flex className="ProductDetailManager" vertical="true" gap={50}>
      <Breadcrumb
        items={[
          {
            title: "Quản lý",
          },
          {
            title: "Chi tiết sản phẩm",
          },
        ]}
      />
      <Table
        rowSelection={{
          ...rowSelection,
        }}
        columns={columns}
        dataSource={productDetails}
        pagination={true}
      />
    </Flex>
  );
};

export default ProductDetailManager;

import { Breadcrumb, Button, Checkbox, Flex, message, Space, Table } from "antd";
import confirm from "antd/es/modal/confirm";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

const ProductDetailManagerById = () => {
  const { id } = useParams();

  const [product, setProduct] = useState([]);
  const [productDeatail, setProductDetail] = useState([]);
  const [selectedRowKeys, setSelectedRowKeys] = useState([]);
  const [productDetailData, setProductDetailData] = useState([]);
  const [currentUser, setCurrentUser] = useState(
    JSON.parse(localStorage.getItem("user"))
  );
  const userData = JSON.parse(localStorage.getItem("user"));
  useEffect(() => {
    window.scrollTo(0, 0);

    axios
      .get("http://localhost:8080/api/v1/products/full", {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        const product_arr = response.data;

        const curr = product_arr.filter((p) => {
          return p.id.toString() === id;
        })[0];

        setProduct(curr);

        axios
          .get(`http://localhost:8080/api/v1/products/productDetail/${id}`, {
            headers: {
              Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
            },
          })
          .then((response) => {
            const data = response.data.map((pd, index) => {
              return {
                color: pd.color,
                size: pd.size,
                quantity: pd.quantity,
                key: index,
                update_productdetail: pd.id,
                name: product_arr.filter((p) => {
                  return p.id === pd.product_id;
                })[0].name,
                image: pd.img_url,
              };
            });

            setProductDetailData(data);
            setProductDetail(data);
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
      title: <Checkbox onChange={(e) => handleSelectAll(e.target.checked)} />,
      dataIndex: "checkbox",
      render: (_, record) => (
        <Checkbox
          checked={selectedRowKeys.includes(record.update_productdetail)}
          onChange={(e) => handleCheckboxChange(e, record.update_productdetail)}
        />
      ),
      width: 50,
    },
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
    {
      title: "Sửa",
      dataIndex: "update_productdetail",
      render: (text) => (
        <Link to={`/admin/productdetails/update/${text}`}>
          <i class="fa-solid fa-pen-to-square" style={{ fontSize: "18px" }}></i>
        </Link>
      ),
    },
  ];

  // const rowSelection = {
  //   onChange: (selectedRowKeys, selectedRows) => {
  //     console.log(
  //       `selectedRowKeys: ${selectedRowKeys}`,
  //       "selectedRows: ",
  //       selectedRows
  //     );
  //   },
  //   getCheckboxProps: (record) => ({
  //     disabled: record.name === "Disabled User",
  //     // Column configuration not to be checked
  //     name: record.name,
  //   }),
  // };

  // Handle checkbox select all
  const handleSelectAll = (checked) => {
    if (checked) {
      setSelectedRowKeys(productDetailData.map((item) => item.update_productdetail));
    } else {
      setSelectedRowKeys([]);
    }
  };

  // Handle individual checkbox change
  const handleCheckboxChange = (e, id) => {
    if (e.target.checked) {
      setSelectedRowKeys((prev) => [...prev, id]);
    } else {
      setSelectedRowKeys((prev) => prev.filter((key) => key !== id));
    }
  };

  // Handle delete selected
  const handleDeleteSelected = () => {
    // Gửi yêu cầu xóa API với danh sách các ID đã chọn
    axios
      .delete(
        `http://localhost:8080/api/v1/productDetails?ids=${selectedRowKeys.join(
          ","
        )}`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      )
      .then((response) => {
        // Xóa các bản ghi đã chọn khỏi bảng
        setProductDetailData((prevData) =>
          prevData.filter((item) => !selectedRowKeys.includes(item.update_productdetail))
        );
        message.success(`Xóa thành công`);
        setSelectedRowKeys([]); // Xóa các mục đã chọn
      })
      .catch((error) => {
        message.error("Xóa thất bại");
      });
  };

  // Handle deselect all
  const handleDeselectAll = () => {
    setSelectedRowKeys([]); // Xóa tất cả các lựa chọn
  };

  const showDeleteConfirm = () => {
    confirm({
      title: `${selectedRowKeys?.length} sản phẩm sẽ bị xoá vĩnh viễn`,
      content: "Tiếp tục?",
      okText: "Xoá sản phẩm",
      okType: "danger",
      cancelText: "Huỷ",
      maskClosable: true,
      onOk() {
        handleDeleteSelected();
      },
      onCancel() {},
    });
  };

  return (
    <Flex className="ProductDetailManagerById" vertical="true" gap={40}>
      <Breadcrumb
        items={[
          {
            title: "Quản lý",
          },
          {
            title: (
              <Link to="/admin/products">
                <span>Quản lý sản phẩm</span>
              </Link>
            ),
          },
          {
            title: "Chi tiết sản phẩm",
          },
          {
            title: `${product?.name}`,
          },
        ]}
      />
      <Space direction="vertical" size="large">
        <Link to="add-product-detail">
          <Button type="primary" style={{ width: "fit-content" }}>
            Thêm chi tiết sản phẩm
          </Button>
        </Link>
        <Flex gap={10} justify="flex-start">
          <Button
            type="default"
            onClick={handleDeselectAll} // Bỏ chọn tất cả
          >
            Bỏ chọn
          </Button>
          <Button
            type="primary"
            danger
            disabled={selectedRowKeys.length === 0}
            onClick={showDeleteConfirm}
          >
            Xóa các mục đã chọn
          </Button>
        </Flex>
        <Table
          // rowSelection={{
          //   ...rowSelection,
          // }}
          rowKey="update_productdetail"
          columns={columns}
          dataSource={productDeatail}
          pagination={true}
          onRow={(record, rowIndex) => {
            return {
              onClick: (event) => {},
            };
          }}
        />
      </Space>
    </Flex>
  );
};

export default ProductDetailManagerById;

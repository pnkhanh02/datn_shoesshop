import {
  Badge,
  Breadcrumb,
  Button,
  Flex,
  Input,
  message,
  Modal,
  Table,
} from "antd";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Pagination from "../Paginate/Pagination.js";

const ProductManager = () => {
  const { confirm } = Modal;
  const [messageApi, contextHolder] = message.useMessage();
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [optionVisible, setOptionVisible] = useState(false);
  const [productTypes, setProductTypes] = useState([]);
  const [productSelected, setProductSelected] = useState([]);
  const userData = JSON.parse(localStorage.getItem("user"));
  const [searchText, setSearchText] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const successMessage = () => {
    messageApi.open({
      type: "success",
      content: "Xoá sản phẩm thành công",
    });
  };

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Xoá sản phẩm thất bại",
    });
  };

  const showDeleteConfirm = () => {
    confirm({
      title: `${productSelected?.length} sản phẩm sẽ bị xoá vĩnh viễn`,
      content: "Tiếp tục?",
      okText: "Xoá sản phẩm",
      okType: "danger",
      cancelText: "Huỷ",
      maskClosable: true,
      onOk() {
        handleDeleteProducts();
      },
      onCancel() {},
    });
  };

  const fetchData = (page = 1, search = "") => {
    setLoading(true);
    axios
      .get("http://localhost:8080/api/v1/products/all", {
        params: {
          page: page,
          size: 10,
          sort: "id,desc",
          search: search,
        },
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        const { content, totalPages, totalElements } = response.data;
        const productsFormatted = response.data.content
          .map((product, index) => {
            return {
              key: product.id,
              name: { id: product.id, name: product.name },
              description: product.description,
              image: product.image_url,
              price: product.price,
              gender_type: product.gender_for,
              sale_percent: product.sale_percent,
              type_name: product.type_name,
              update_product: product.id,
            };
          })
          .sort((a, b) => b.key - a.key);

        setProducts(productsFormatted);
        setTotalPages(totalPages);
        setTotalElements(totalElements);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        setLoading(false);
      });

    axios
      .get("http://localhost:8080/api/v1/productTypes/full", {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        // console.log(response.data);
        setProductTypes(response.data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  useEffect(() => {
    window.scrollTo(0, 0);

    fetchData(currentPage, searchText);
  }, [currentPage, searchText]);

  const handleSearch = () => {
    setCurrentPage(1);
    fetchData(1, searchText);
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  const columns = [
    {
      title: "Tên sản phẩm",
      dataIndex: "name",
      render: (record) => (
        <Link to={`/admin/products-detail/${record.id}`}>{record.name}</Link>
      ),
    },
    {
      title: "Mô tả",
      dataIndex: "description",
    },
    {
      title: "Ảnh",
      dataIndex: "image",
      render: (text) => <img src={text} alt="" width={80} />,
    },
    {
      title: "Giá",
      dataIndex: "price",
      sorter: (a, b) => a.price - b.price,
      width: 140,
    },
    {
      title: "Loại",
      dataIndex: "type_name",
      filters: productTypes.map((p) => {
        return {
          text: p.name,
          value: p.name,
        };
      }),
      onFilter: (value, record) => record.type_name.indexOf(value) === 0,
    },
    {
      title: "Giới tính",
      dataIndex: "gender_type",
      render: (text) =>
        text === "MALE" ? "NAM" : text === "FEMALE" ? "NỮ" : "UNISEX",
      filters: [
        { text: "Nam", value: "MALE" },
        { text: "Nữ", value: "FEMALE" },
        { text: "Unisex", value: "UNISEX" },
      ],
      onFilter: (value, record) => record.gender_type.indexOf(value) === 0,
    },
    {
      title: "Giảm giá",
      dataIndex: "sale_percent",
      render: (text) => (
        <Badge count={`${text}%`} color={text > 0 ? "#52c41a" : "#b9b9b9"} />
      ),
    },
    {
      title: "Sửa",
      dataIndex: "update_product",
      render: (text) => (
        <Link to={`/admin/products/update/${text}`}>
          <i class="fa-solid fa-pen-to-square" style={{ fontSize: "18px" }}></i>
        </Link>
      ),
    },
  ];

  const rowSelection = {
    // productSelected,
    selectedRowKeys: productSelected,
    onChange: (selectedRowKeys) => {
      // console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
      // !selectedRowKeys.length
      //   ? setOptionVisible(false)
      //   : setOptionVisible(true);
      setOptionVisible(selectedRowKeys.length > 0);
      setProductSelected(selectedRowKeys);
      console.log(selectedRowKeys.join(","));
    },
  };

  const handleDeleteProducts = () => {
    setLoading(true);

    const adminData = JSON.parse(localStorage.getItem("user"));

    axios
      .delete(
        `http://localhost:8080/api/v1/products?ids=${productSelected.join(
          ","
        )}`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      )
      .then((response) => {
        console.log("Delete product successfully");
        successMessage();
        fetchData();
        setProductSelected([]);
        setOptionVisible(false);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        errorMessage();
      });
  };

  const handleDeselectAll = () => {
    setProductSelected([]);
    setOptionVisible(false);
  };

  return (
    <Flex
      className="ProductManager"
      vertical="true"
      gap={20}
      style={{ position: "relative" }}
    >
      {contextHolder}
      <Breadcrumb
        items={[
          {
            title: "Quản lý",
          },
          {
            title: "Quản lý sản phẩm",
          },
        ]}
      />

      <Input
        placeholder="Search by name"
        value={searchText}
        onChange={(e) => setSearchText(e.target.value)}
        style={{ marginBottom: 20, width: 300 }}
        onPressEnter={handleSearch}
      />

      {optionVisible && (
        <Flex className="option-sticky" justify="space-between" align="center">
          <span>{`Đã chọn ${productSelected?.length} sản phẩm`}</span>
          <Flex gap={10} justify="flex-end">
            <Button
              type="primary"
              style={{ width: "fit-content" }}
              onClick={() => handleDeselectAll()}
            >
              Bỏ chọn
            </Button>
            <Button
              type="primary"
              danger
              style={{ width: "fit-content" }}
              onClick={showDeleteConfirm}
            >
              Xoá
            </Button>
          </Flex>
        </Flex>
      )}
      <Table
        rowSelection={rowSelection}
        columns={columns}
        dataSource={products}
        loading={loading}
        pagination={false}
        onRow={(record, rowIndex) => {
          return {
            onClick: (event) => {},
          };
        }}
        size="large"
      />
      {/* Pagination component */}
      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={handlePageChange}
      />
    </Flex>
  );
};

export default ProductManager;

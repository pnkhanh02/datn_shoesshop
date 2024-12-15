import { Breadcrumb, Button, Flex, Input, message, Modal, Table } from "antd";
import React, { useEffect, useState } from "react";
import "./SalesManager.css";
import { Link } from "react-router-dom";
import axios from "axios";
import moment from "moment";
import Pagination from "../Paginate/Pagination.js";

const SalesManager = () => {
  const { confirm } = Modal;
  const [sales, setSales] = useState([]);
  const [loading, setLoading] = useState(false);
  const [messageApi, contextHolder] = message.useMessage();
  const [optionVisible, setOptionVisible] = useState(false);
  const [salesSelected, setSalesSelected] = useState([]);
  const [currentUser, setCurrentUser] = useState(
    JSON.parse(localStorage.getItem("user"))
  );
  const userData = JSON.parse(localStorage.getItem("user"));
  const [searchText, setSearchText] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const fetchData = (page = 1, search = "") => {
    setLoading(true);
    axios
      .get("http://localhost:8080/api/v1/sales", {
        params: {
          page: page,
          size: 10,
          sort: "id,desc",
          search: search,
        },
        headers: {
          Authorization: `Bearer ${userData.token}`,
        },
      })
      .then((response) => {
        const { content, totalPages, totalElements } = response.data;
        const data = response.data.content.map((sl, index) => {
          return {
            key: sl.id,
            id: sl.id,
            sale_info: sl.sale_info,
            percent_sale: sl.percent_sale,
            start_date: moment(sl.start_date).format("YYYY-MM-DD"),
            end_date: moment(sl.end_date).format("YYYY-MM-DD"),
          };
        });
        setSales(data);
        setTotalPages(totalPages);
        setTotalElements(totalElements);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        setLoading(false);
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

  const successMessage = () => {
    messageApi.open({
      type: "success",
      content: "Xoá khuyến mãi thành công",
    });
  };

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Xoá khuyến mãi thất bại",
    });
  };

  const showDeleteConfirm = () => {
    confirm({
      title: `${salesSelected?.length} khuyến mãi sẽ bị xoá vĩnh viễn`,
      content: "Tiếp tục?",
      okText: "Xoá khuyến mãi",
      okType: "danger",
      cancelText: "Huỷ",
      maskClosable: true,
      onOk() {
        handleDeleteSales();
      },
      onCancel() {},
    });
  };

  const columns = [
    {
      title: "Tên khuyến mãi",
      dataIndex: "sale_info",
      render: (text, record) => (
        <Link to={`/admin/sales/${record.id}`}>{text}</Link>
      ),
    },
    {
      title: "Phần trăm khuyến mãi",
      dataIndex: "percent_sale",
    },
    {
      title: "Ngày bắt đầu",
      dataIndex: "start_date",
    },
    {
      title: "Ngày kết thúc",
      dataIndex: "end_date",
    },
    {
      title: "Sửa",
      dataIndex: "update_sales",
      render: (_, record) => (
        <Link to={`/admin/sales/${record.id}`}>
          <i
            className="fa-solid fa-pen-to-square"
            style={{ fontSize: "18px" }}
          ></i>
        </Link>
      ),
    },
  ];

  const rowSelection = {
    selectedRowKeys: salesSelected, // Dùng `salesSelected` làm giá trị của `selectedRowKeys`
    onChange: (selectedRowKeys) => {
      setSalesSelected(selectedRowKeys); // Cập nhật lại trạng thái `salesSelected` khi người dùng chọn hàng
      setOptionVisible(selectedRowKeys.length > 0); // Hiển thị hoặc ẩn phần tùy chọn dựa trên số lượng lựa chọn
    },
  };

  const handleDeleteSales = () => {
    axios
      .delete(
        `http://localhost:8080/api/v1/sales?ids=${salesSelected.join(",")}`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      )
      .then((response) => {
        // Xóa các bản ghi đã chọn khỏi bảng
        setSales((prevData) =>
          prevData.filter((item) => !salesSelected.includes(item.id))
        );
        setSalesSelected([]); // Reset các mục đã chọn
        setOptionVisible(false); // Ẩn các tùy chọn
        successMessage(); // Hiển thị thông báo thành công
      })
      .catch((error) => {
        errorMessage(); // Hiển thị thông báo lỗi nếu có lỗi
      });
  };

  const handleDeselectAll = () => {
    setSalesSelected([]); // Xóa tất cả các lựa chọn
    setOptionVisible(false); // Ẩn các tùy chọn khi không còn sản phẩm nào được chọn
  };

  return (
    <Flex
      className="SalesManager"
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
            title: "Quản lý khuyến mãi",
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
          <span>{`Đã chọn ${salesSelected?.length} sản phẩm`}</span>
          <Flex gap={10} justify="flex-end">
            <Button
              type="primary"
              style={{ width: "fit-content" }}
              onClick={handleDeselectAll}
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
        dataSource={sales}
        loading={loading}
        pagination={false}
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

export default SalesManager;

import React, { useState, useEffect } from "react";
import {
  Table,
  Button,
  Checkbox,
  Space,
  message,
  Tag,
  Flex,
  Modal,
} from "antd";
import { useNavigate } from "react-router-dom";
import axios from "axios"; // Import axios
import "./ExchangeShoesManager.css";

const ExchangeShoesManager = () => {
  const { confirm } = Modal;
  const navigate = useNavigate();
  const [selectedRowKeys, setSelectedRowKeys] = useState([]);
  const [exchangeShoesData, setExchangeShoesData] = useState([]); // State to store API data
  const [loading, setLoading] = useState(false); // Loading state
  const userData = JSON.parse(localStorage.getItem("user"));

  // Fetch data from API
  const fetchData = () => {
    console.log(localStorage.getItem("user"));
    setLoading(true);

    axios
      .get(
        `http://localhost:8080/api/v1/exchange-shoes/getAllExchangeShoes?page=0&size=99&sort=id,asc`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      )
      .then((response) => {
        const data = response.data.content.map((sl, index) => {
          return {
            key: sl.id,
            id: sl.exchangeShoesId,
            exchangeShoesName: sl.exchangeShoesName,
            customerId: sl.customerId,
            status: sl.status,
            exchangeShoesSales: sl.exchangeShoesSales,
          };
        });
        setExchangeShoesData(data);
        setLoading(false);
      })
      .catch((error) => {
        message.error("Failed to load data from API");
        setLoading(false);
      });
  };

  // Use effect to fetch data on component mount
  useEffect(() => {
    fetchData();
  }, []);

  // Table columns
  const columns = [
    {
      title: <Checkbox onChange={(e) => handleSelectAll(e.target.checked)} />,
      dataIndex: "checkbox",
      render: (_, record) => (
        <Checkbox
          checked={selectedRowKeys.includes(record.id)}
          onChange={(e) => handleCheckboxChange(e, record.id)}
        />
      ),
      width: 50,
    },
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Tên sản phẩm",
      dataIndex: "exchangeShoesName",
      key: "exchangeShoesName",
    },
    {
      title: "CustomerId",
      dataIndex: "customerId",
      key: "customerId",
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (text) => {
        let color;
        switch (text) {
          case "PENDING":
            color = "blue"; // Màu xanh lam cho "PENDING"
            break;
          case "APPROVE":
            color = "green"; // Màu xanh lá cho "APPROVE"
            break;
          case "REJECT":
            color = "red"; // Màu đỏ cho "REJECT"
            break;
          default:
            color = "default"; // Màu mặc định nếu không khớp với bất kỳ giá trị nào
        }
        return <Tag color={color}>{text}</Tag>;
      },
    },
    {
      title: "Giảm giá",
      dataIndex: "exchangeShoesSales",
      key: "exchangeShoesSales",
    },
    {
      title: "Chức năng",
      key: "actions",
      render: (_, record) => (
        <Space size="middle">
          <Button type="link" onClick={() => handleViewDetails(record)}>
            Xem chi tiết
          </Button>
        </Space>
      ),
    },
  ];

  // Handle checkbox select all
  const handleSelectAll = (checked) => {
    if (checked) {
      setSelectedRowKeys(exchangeShoesData.map((item) => item.id));
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
        `http://localhost:8080/api/v1/exchange-shoes?ids=${selectedRowKeys.join(
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
        setExchangeShoesData((prevData) =>
          prevData.filter((item) => !selectedRowKeys.includes(item.id))
        );
        message.success(`Xóa thành công`);
        setSelectedRowKeys([]); // Xóa các mục đã chọn
      })
      .catch((error) => {
        message.error("Xóa thất bại");
      });
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

  // Handle deselect all
  const handleDeselectAll = () => {
    setSelectedRowKeys([]); // Xóa tất cả các lựa chọn
  };

  // Handle view details
  const handleViewDetails = (record) => {
    navigate(`/admin/exchangeShoes/${record.id}`, { state: record });
  };

  // Handle add new product
  const handleAddNewProduct = () => {
    navigate("/exchange-shoes-form");
  };

  return (
    <div className="exchange-shoes-client-container">
      <div className="actions">
        <Button
          type="primary"
          style={{ marginBottom: 16 }}
          onClick={handleAddNewProduct}
        >
          Thêm sản phẩm cũ
        </Button>
        <Flex gap={10} justify="flex-end">
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
      </div>
      <Table
        rowKey="id"
        columns={columns}
        dataSource={exchangeShoesData}
        loading={loading}
        pagination={true}
        onRow={(record, rowIndex) => {
          return {
            onClick: (event) => {},
          };
        }}
        size="large"
      />
    </div>
  );
};

export default ExchangeShoesManager;
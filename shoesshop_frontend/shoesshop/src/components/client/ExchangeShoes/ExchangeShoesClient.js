import React, { useState } from 'react';
import { Table, Button, Checkbox, Space, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import './ExchangeShoesClient.css';

const ExchangeShoesClient = () => {
  const navigate = useNavigate();
  const [selectedRowKeys, setSelectedRowKeys] = useState([]);

  // Fake data
  const data = [
    {
      id: 1,
      name: 'Nike Air Force 1',
      status: 'Pending',
    },
    {
      id: 2,
      name: 'Adidas Ultraboost',
      status: 'Approved',
      sales: '10%',
    },
  ];

  // Table columns
  const columns = [
    {
      title: <Checkbox onChange={(e) => handleSelectAll(e.target.checked)} />,
      dataIndex: 'checkbox',
      render: (_, record) => (
        <Checkbox
          checked={selectedRowKeys.includes(record.id)}
          onChange={(e) => handleCheckboxChange(e, record.id)}
        />
      ),
      width: 50,
    },
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: 'Tên sản phẩm',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Trạng thái',
      dataIndex: 'status',
      key: 'status',
    },
    {
        title: 'Giảm giá',
        dataIndex: 'sales',
        key: 'sales',
      },
    {
      title: 'Chức năng',
      key: 'actions',
      render: (_, record) => (
        <Space size="middle">
          <Button type="link" onClick={() => handleViewDetails(record)}>
            Xem chi tiết
          </Button>
        </Space>
      ),
    },
  ];

  const handleSelectAll = (checked) => {
    if (checked) {
      setSelectedRowKeys(data.map((item) => item.id));
    } else {
      setSelectedRowKeys([]);
    }
  };

  const handleCheckboxChange = (e, id) => {
    if (e.target.checked) {
      setSelectedRowKeys((prev) => [...prev, id]);
    } else {
      setSelectedRowKeys((prev) => prev.filter((key) => key !== id));
    }
  };

  const handleDeleteSelected = () => {
    message.success(`Deleted records: ${selectedRowKeys.join(', ')}`);
    setSelectedRowKeys([]);
  };

  const handleViewDetails = (record) => {
    navigate('/client/exchange-shoes-form', { state: record });
  };

  const handleAddNewProduct = () => {
    navigate('/client/exchange-shoes-form');
  };

  return (
    <div className="exchange-shoes-client-container">
      <div className="actions">
        <Button type="primary" style={{ marginBottom: 16 }} onClick={handleAddNewProduct}>
          Thêm sản phẩm cũ
        </Button>
        <Button
          type="danger"
          disabled={selectedRowKeys.length === 0}
          onClick={handleDeleteSelected}
          style={{ marginLeft: 8 }}
        >
          Xóa các mục đã chọn
        </Button>
      </div>
      <Table
        rowKey="id"
        columns={columns}
        dataSource={data}
        pagination={{ pageSize: 5 }}
      />
    </div>
  );
};

export default ExchangeShoesClient;

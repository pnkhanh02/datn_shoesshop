import React from 'react';
import { Form, Input, DatePicker, InputNumber, Button, Upload } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import './ExchangeShoesForm.css';

const ExchangeShoesForm = () => {
  const [form] = Form.useForm();

  const handleSubmit = (values) => {
    console.log('Form values:', values);
  };

  return (
    <div className="exchange-shoes-container">
      <h2 className="exchange-shoes-title">Thu Cũ Đổi Mới Giày</h2>
      <Form
        form={form}
        layout="vertical"
        onFinish={handleSubmit}
        className="exchange-shoes-form"
      >
        <Form.Item
          label="Tên giày"
          name="name"
          rules={[{ required: true, message: 'Vui lòng nhập tên giày!' }]}
        >
          <Input placeholder="Nhập tên giày" />
        </Form.Item>

        <Form.Item
          label="Loại giày"
          name="type"
          rules={[{ required: true, message: 'Vui lòng nhập loại giày!' }]}
        >
          <Input placeholder="Nhập loại giày" />
        </Form.Item>

        <Form.Item
          label="Thời gian mua giày"
          name="purchaseDate"
          rules={[{ required: true, message: 'Vui lòng chọn thời gian mua!' }]}
        >
          <DatePicker className="exchange-shoes-datepicker" style={{ width: '100%' }} />
        </Form.Item>

        <Form.Item
          label="Giá tiền (VNĐ)"
          name="price"
          rules={[{ required: true, message: 'Vui lòng nhập giá tiền!' }]}
        >
          <InputNumber
            placeholder="Nhập giá tiền"
            className="exchange-shoes-input-number"
            style={{ width: '100%' }}
            min={0}
          />
        </Form.Item>

        <Form.Item
          label="Mô tả tình trạng giày"
          name="description"
          rules={[{ required: true, message: 'Vui lòng nhập mô tả tình trạng giày!' }]}
        >
          <Input.TextArea placeholder="Nhập mô tả tình trạng giày" rows={4} />
        </Form.Item>

        <Form.Item
          label="Ảnh giày muốn đổi"
          name="image"
          valuePropName="fileList"
          getValueFromEvent={(e) => (Array.isArray(e) ? e : e?.fileList)}
          rules={[{ required: true, message: 'Vui lòng tải lên ảnh giày!' }]}
        >
          <Upload name="image" listType="picture" beforeUpload={() => false}>
            <Button icon={<UploadOutlined />}>Tải lên ảnh</Button>
          </Upload>
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" className="exchange-shoes-submit">
            Gửi thông tin
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default ExchangeShoesForm;
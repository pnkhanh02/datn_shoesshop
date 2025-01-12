import { Button, DatePicker, Form, Input, Modal } from "antd";
import moment from "moment";
import React, { useEffect } from "react";

const UpdateCustomerModal = ({ visible, onClose, data, onUpdate }) => {
  const [form] = Form.useForm();

  // Cập nhật giá trị form khi dữ liệu hoặc modal thay đổi
  useEffect(() => {
    if (visible && data) {
      form.setFieldsValue({
        username: data.username,
        firstName: data.firstName,
        lastName: data.lastName,
        address: data.address,
        birthday: data.birthday ? moment(data.birthday) : null,
      });
    }
  }, [visible, data, form]);

  const handleFinish = (values) => {
    // Chuyển đổi ngày về định dạng ISO trước khi gửi
    const updatedData = {
      ...values,
      birthday: values.birthday.format("YYYY-MM-DD"),
    };
    onUpdate(updatedData); // Gọi hàm cập nhật từ cha
  };

  return (
    <Modal
      title="Cập nhật thông tin tài khoản"
      visible={visible}
      onCancel={onClose}
      footer={null}
    >
      <Form
        initialValues={{
          username: data.username,
          firstName: data.firstName,
          lastName: data.lastName,
          address: data.address,
          birthday: moment(data.birthday),
        }}
        onFinish={handleFinish}
      >
        <Form.Item name="firstName" label="First Name" rules={[{ required: true, message: "Hãy nhập Firstname" }]}>
          <Input />
        </Form.Item>
        <Form.Item name="lastName" label="Last Name" rules={[{ required: true, message: "Hãy nhập Lastname" }]}>
          <Input />
        </Form.Item>
        <Form.Item name="birthday" label="Birthday" rules={[{ required: true, message: "Hãy nhập ngày sinh" }]}>
          <DatePicker />
        </Form.Item>
        <Form.Item name="address" label="Address" rules={[{ required: true, message: "Hãy nhập địa chỉ" }]}>
          <Input />
        </Form.Item>
        <Button type="primary" htmlType="submit">
          Cập nhật
        </Button>
      </Form>
    </Modal>
  );
};

export default UpdateCustomerModal;

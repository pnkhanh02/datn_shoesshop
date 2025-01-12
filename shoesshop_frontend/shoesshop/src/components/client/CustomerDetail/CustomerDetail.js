import React, { useEffect, useState } from "react";
import { Form, Input, DatePicker, Select, Spin, message } from "antd";
import axios from "axios";
import moment from "moment";
import "./CustomerDetail.css";
import { useParams } from "react-router-dom";
import UpdateCustomerModal from "./UpdateCustomerModal";

const { Option } = Select;

const CustomerDetail = ({ customerId }) => {
  const { id } = useParams();
  const [form] = Form.useForm();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const userData = JSON.parse(localStorage.getItem("user"));
  const [isModalVisible, setIsModalVisible] = useState(false);

  // Fetch customer data by ID
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/customers/${id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        setData(response.data);
        setLoading(false);
      })
      .catch((error) => {
        message.error("Failed to fetch details");
        setLoading(false);
      });
  }, [id, userData.token]);

  const handleUpdate = (updatedData) => {
    axios
      .put(`http://localhost:8080/api/v1/customers/update/${id}`, updatedData, {
        headers: {
          Authorization: `Bearer ${userData.token}`,
        },
      })
      .then(() => {
        message.success("Cập nhật thông tin thành công!");
        setIsModalVisible(false);
        // Cập nhật lại dữ liệu hiển thị
        setData((prevData) => ({
          ...prevData,
          ...updatedData,
          birthday: updatedData.birthday
            ? moment(updatedData.birthday).format("YYYY-MM-DD")
            : prevData.birthday,
        }));
      })
      .catch((error) => {
        // Kiểm tra phản hồi lỗi từ server
        if (error.response && error.response.data) {
          const serverError = error.response.data;

          // Hiển thị thông báo lỗi tùy theo phản hồi
          if (serverError.errorCode === "FRISTNAME_NULL") {
            message.error("First name không được để trống!");
          } else if (serverError.errorCode === "LASTNAME_NULL") {
            message.error("Last name không được để trống!");
          } else if (serverError.errorCode === "ADDRESS_NULL") {
            message.error("Địa chỉ không được để trống!");
          } else if (serverError.errorCode === "BIRTHDAY_NULL") {
            message.error("Ngày sinh không được để trống!");
          } else {
            message.error("Đã xảy ra lỗi, vui lòng thử lại!");
          }
        } else {
          message.error("Cập nhật thông tin thất bại!");
        }

        console.error(error);
      });
  };

  if (loading) {
    return (
      <div className="customer-detail-loading">
        <Spin size="large" />
      </div>
    );
  }

  return (
    <div>
      <div className="customer-detail-container">
        <div className="customer-detail-section">
          <div className="customer-detail-left">
            <div className="customer-detail-item">
              <span className="label">Username:</span>
              <span className="value">{data.username}</span>
            </div>
            <div className="customer-detail-item">
              <span className="label">Name:</span>
              <span className="value">
                {[data.firstName, data.lastName].join(" ")}
              </span>
            </div>
            <div className="customer-detail-item">
              <span className="label">Role:</span>
              <span className="value">{data.role}</span>
            </div>
            <div className="customer-detail-item">
              <span className="label">Address:</span>
              <span className="value">{data.address}</span>
            </div>
          </div>

          <div className="customer-detail-right">
            <div className="customer-detail-item">
              <span className="label">Birthday:</span>
              <span className="value">
                {moment(data.birthday).format("YYYY-MM-DD")}
              </span>
            </div>
            <div className="customer-detail-item">
              <span className="label">Email:</span>
              <span className="value">{data.email}</span>
            </div>
            <div className="customer-detail-item">
              <span className="label">Phone:</span>
              <span className="value">{data.phone}</span>
            </div>
            <div className="customer-detail-item">
              <span className="label">Gender:</span>
              <span className="value">{data.gender}</span>
            </div>
          </div>
        </div>
      </div>
      {/* Button for updating information */}
      <div className="update-button-container">
        <button
          className="update-button"
          onClick={() => setIsModalVisible(true)}
        >
          Cập nhật thông tin tài khoản
        </button>
      </div>

      {/* Modal */}
      <UpdateCustomerModal
        visible={isModalVisible}
        onClose={() => setIsModalVisible(false)}
        data={data}
        onUpdate={handleUpdate}
      />
    </div>
  );
};

export default CustomerDetail;

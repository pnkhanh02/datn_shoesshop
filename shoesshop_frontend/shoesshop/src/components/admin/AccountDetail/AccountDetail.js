import React, { useEffect, useState } from "react";
import { Form, Input, DatePicker, Select, message, Button, Spin } from "antd";
import axios from "axios";
import moment from "moment";
import "./AccountDetail.css";
import { UploadOutlined, ArrowLeftOutlined } from "@ant-design/icons";
import { useParams } from "react-router-dom";
import UpdateAccountModal from "./UpdateAccountModal";

const { Option } = Select;

const AccountDetail = ({ customerId }) => {
  const { id } = useParams();
  const [form] = Form.useForm();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const userData = JSON.parse(localStorage.getItem("user"));
  const [isModalVisible, setIsModalVisible] = useState(false);

  // Fetch customer data by ID
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/accounts/${id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        message.error("Failed to fetch details");
      });
  }, [id, userData.token]);

  const handleUpdate = (updatedData) => {
    // update for admin
    if(userData?.role === "ADMIN"){
      axios
        .put(`http://localhost:8080/api/v1/admins/update/${id}`, updatedData, {
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
    }

    if(userData?.role === "EMPLOYEE"){
      axios
        .put(`http://localhost:8080/api/v1/employees/update/${id}`, updatedData, {
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
    }
  };

  if (loading) {
    return (
      <div className="account-detail-loading">
        <Spin size="large" />
      </div>
    );
  }

  return (
    <div>
      {data && data.id !== userData.id && (
        <Form.Item>
          <Button
            type="default"
            icon={<ArrowLeftOutlined />}
            onClick={() => window.history.back()}
          ></Button>
        </Form.Item>
      )}
      <h2
        style={{
          textAlign: "center", // Căn giữa theo chiều ngang
          marginBottom: "20px", // Khoảng cách dưới (tùy chỉnh nếu cần)
        }}
      >
        {data && data.id === userData.id
          ? "Thông tin tài khoản của tôi"
          : "Thông tin tài khoản"}
      </h2>
      <div className="account-detail-container">
        {data && (
          <div className="account-detail-section">
            <div className="account-detail-left">
              <div className="account-detail-item">
                <span className="account-label">Username:</span>
                <span className="account-value">{data.username}</span>
              </div>
              <div className="account-detail-item">
                <span className="account-label">Name:</span>
                <span className="account-value">
                  {[data.firstName, data.lastName].join(" ")}
                </span>
              </div>
              <div className="account-detail-item">
                <span className="account-label">Role:</span>
                <span className="account-value">{data.role}</span>
              </div>
              <div className="account-detail-item">
                <span className="account-label">Address:</span>
                <span className="account-value">{data.address}</span>
              </div>
            </div>

            <div className="account-detail-right">
              <div className="account-detail-item">
                <span className="account-label">Birthday:</span>
                <span className="account-value">
                  {moment(data.birthday).format("YYYY-MM-DD")}
                </span>
              </div>
              <div className="account-detail-item">
                <span className="account-label">Email:</span>
                <span className="account-value">{data.email}</span>
              </div>
              <div className="account-detail-item">
                <span className="account-label">Phone:</span>
                <span className="account-value">{data.phone}</span>
              </div>
              <div className="account-detail-item">
                <span className="account-label">Gender:</span>
                <span className="account-value">{data.gender}</span>
              </div>
            </div>
          </div>
        )}
      </div>
      {/* Button for updating information */}
      {data && data.id === userData.id && (
        <div className="account-update-button-container">
          <button
            className="account-update-button"
            onClick={() => setIsModalVisible(true)}
          >
            Cập nhật thông tin tài khoản
          </button>
        </div>
      )}

      {/* Modal */}
      <UpdateAccountModal
        visible={isModalVisible}
        onClose={() => setIsModalVisible(false)}
        data={data}
        onUpdate={handleUpdate}
      />
    </div>
  );
};

export default AccountDetail;

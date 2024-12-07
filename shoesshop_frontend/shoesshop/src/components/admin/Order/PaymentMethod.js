import React, { useEffect, useState } from "react";
import "./PaymentMethod.css";
import { FaPlus, FaEdit, FaTrash } from "react-icons/fa";
import axios from "axios";
import { Button, Input, message, Modal } from "antd";

const PaymentMethod = () => {
  const adminData = JSON.parse(localStorage.getItem("user")); // Đảm bảo adminData luôn có sẵn
  const userData = JSON.parse(localStorage.getItem("user"));
  const [paymentMethods, setPaymentMethods] = useState([]);
  const [newMethod, setNewMethod] = useState(""); // State để lưu tên phương thức thanh toán mới
  const [newDescription, setNewDescription] = useState(""); // State để lưu mô tả mới
  const [loading, setLoading] = useState(false);
  const [editMethodId, setEditMethodId] = useState(null);
  const [editMethodName, setEditMethodName] = useState("");
  const [editDescription, setEditDescription] = useState("");
  const [error, setError] = useState(null); // State để lưu lỗi
  const [deleteMethodId, setDeleteMethodId] = useState(null); // State để lưu id của phương thức thanh toán muốn xóa
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false); // State để hiển thị hộp thoại xác nhận xóa

  useEffect(() => {
    const fetchPaymentMethods = async () => {
      setLoading(true);
      try {
        const response = await axios.get(
          "http://localhost:8080/api/v1/paymentMethods/full",
          {
            headers: {
              Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
            },
          }
        );
        console.log(response.data);
        setPaymentMethods(response.data);
      } catch (error) {
        console.error("Error fetching payment methods:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchPaymentMethods();
  }, [userData.token]);

  const handleAddMethod = async () => {
    if (newMethod && newDescription) {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/v1/paymentMethods/create",
          { name: newMethod, description_payment: newDescription },
          {
            headers: {
              Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
            },
          }
        );
        setPaymentMethods(
          paymentMethods.map((method) =>
            method.id === editMethodId
              ? {
                  ...method,
                  name: editMethodName,
                  description_payment: editDescription,
                }
              : method
          )
        );
        setNewMethod(""); // Reset input field
        setNewDescription(""); // Reset description field
        setError(null); // Clear any previous error
        message.success("Thêm phương thức thanh toán thành công");
        setTimeout(() => {
          window.location.reload();
        }, 500);
      } catch (error) {
        console.error("Error adding payment method:", error);
        message.error("Thêm phương thức thanh toán thất bại");
        setError("Thêm phương thức thanh toán thất bại");
      }
    } else {
      setError("Please provide all the required fields.");
    }
  };

  const handleEditMethod = async () => {
    if (editMethodId && editMethodName && editDescription) {
      try {
        await axios.put(
          `http://localhost:8080/api/v1/paymentMethods/update/${editMethodId}`,
          { name: editMethodName, description_payment: editDescription },
          {
            headers: {
              Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
            },
          }
        );
        setPaymentMethods(
          paymentMethods.map((method) =>
            method.id === editMethodId
              ? {
                  ...method,
                  name: editMethodName,
                  description_payment: editDescription,
                }
              : method
          )
        );
        setEditMethodId(null);
        setEditMethodName("");
        setEditDescription("");
        setError(null); // Clear any previous error
        message.success("Chỉnh sửa thành công");
      } catch (error) {
        console.error("Error updating payment method:", error);
        message.error("Chỉnh sửa thất bại");
        setError("Chỉnh sửa thất bại");
      }
    } else {
      setError("Please provide all the required fields.");
    }
  };

  const handleDeleteMethod = async () => {
    if (deleteMethodId) {
      try {
        await axios.delete(
          `http://localhost:8080/api/v1/paymentMethods/delete/${deleteMethodId}`,
          {
            headers: {
              Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
            },
          }
        );
        setPaymentMethods(
          paymentMethods.filter((method) => method.id !== deleteMethodId)
        );
        setShowDeleteConfirm(false); // Hide the confirmation dialog
        setDeleteMethodId(null); // Clear the delete method id
        setError(null); // Clear any previous error
        message.success("Xóa thành công");
      } catch (error) {
        console.error("Error deleting payment method:", error);
        message.error("Xóa thất bại");
        setError("Xóa thất bại");
      }
    }
  };

  return (
    <div className="payment-method-list">
      <h2>Payment Methods</h2>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <div className="payment-method-container">
        <ul className="scrollable-list">
          {paymentMethods.map((method) => (
            <li key={method.id}>
              <div className="payment-method">
                <span>{method.name}</span>
                <p>{method.description_payment}</p>
                <div className="icon-container">
                  <FaEdit
                    className="edit-icon"
                    onClick={() => {
                      setEditMethodId(method.id);
                      setEditMethodName(method.name);
                      setEditDescription(method.description_payment);
                    }}
                  />
                  <FaTrash
                    className="delete-icon"
                    onClick={() => {
                      setDeleteMethodId(method.id);
                      setShowDeleteConfirm(true);
                    }}
                  />
                </div>
              </div>
            </li>
          ))}
        </ul>
        </div>
      )}
      <Modal
        title="Edit Payment Method"
        open={!!editMethodId}
        onCancel={() => {
          setEditMethodId(null);
          setEditMethodName("");
          setEditDescription("");
        }}
        footer={[
          <Button
            key="cancel"
            onClick={() => {
              setEditMethodId(null);
              setEditMethodName("");
              setEditDescription("");
            }}
          >
            Hủy
          </Button>,
          <Button key="save" type="primary" onClick={handleEditMethod}>
            Lưu
          </Button>,
        ]}
      >
        <div>
          <Input
            type="text"
            value={editMethodName}
            onChange={(e) => setEditMethodName(e.target.value)}
            placeholder="Edit method name"
            style={{ marginBottom: "10px" }}
          />
          <Input
            type="text"
            value={editDescription}
            onChange={(e) => setEditDescription(e.target.value)}
            placeholder="Edit description"
          />
        </div>
      </Modal>

      <Modal
        title="Confirm Deletion"
        open={showDeleteConfirm}
        onCancel={() => {
          setShowDeleteConfirm(false);
          setDeleteMethodId(null);
        }}
        footer={[
          <Button
            key="cancel"
            onClick={() => {
              setShowDeleteConfirm(false);
              setDeleteMethodId(null);
            }}
          >
            Hủy
          </Button>,
          <Button
            key="delete"
            type="primary"
            danger
            onClick={handleDeleteMethod}
          >
            Xóa
          </Button>,
        ]}
      >
        <p>Bạn có chắc chắn muốn xóa phương thức thanh toán này?</p>
      </Modal>

      <hr className="divider" />

      <div className="add-method">
        <h3>Add New Payment Method</h3>
        <div className="add-method-container">
          <input
            type="text"
            value={newMethod}
            placeholder="Add new payment method"
            onChange={(e) => setNewMethod(e.target.value)}
          />
          <input
            type="text"
            value={newDescription}
            placeholder="Add description"
            onChange={(e) => setNewDescription(e.target.value)}
          />
          <button onClick={handleAddMethod}>
            <FaPlus /> Add
          </button>
        </div>
        {error && <p className="error-message">{error}</p>}{" "}
      </div>
    </div>
  );
};

export default PaymentMethod;

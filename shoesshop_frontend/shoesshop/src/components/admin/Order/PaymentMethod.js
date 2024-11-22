import React, { useEffect, useState } from "react";
import "./PaymentMethod.css";
import { FaPlus, FaEdit, FaTrash } from "react-icons/fa";
import axios from "axios";

const PaymentMethod = () => {
  const adminData = JSON.parse(localStorage.getItem("user")); // Đảm bảo adminData luôn có sẵn
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

  useEffect(
    () => {
      const fetchPaymentMethods = async () => {
        setLoading(true);
        try {
          const response = await axios.get(
            "http://localhost:8080/api/v1/paymentMethods/all"
            // ,
            // {
            //   auth: {
            //     username: adminData.username,
            //     password: adminData.password,
            //   },
            // }
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
    }
    //   , [adminData.username, adminData.password]
  );

  const handleAddMethod = async () => {
    if (newMethod && newDescription) {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/v1/paymentMethods/create",
          { name: newMethod, description_payment: newDescription }
          //   ,
          //   {
          //     auth: {
          //       username: adminData.username,
          //       password: adminData.password,
          //     },
          //   }
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
      } catch (error) {
        console.error("Error adding payment method:", error);
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
          { name: editMethodName, description_payment: editDescription }
          //   ,
          //   {
          //     auth: {
          //       username: adminData.username,
          //       password: adminData.password,
          //     },
          //   }
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
      } catch (error) {
        console.error("Error updating payment method:", error);
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
          `http://localhost:8080/api/v1/paymentMethods/delete/${deleteMethodId}`
          //   ,
          //   {
          //     auth: {
          //       username: adminData.username,
          //       password: adminData.password,
          //     },
          //   }
        );
        setPaymentMethods(
          paymentMethods.filter((method) => method.id !== deleteMethodId)
        );
        setShowDeleteConfirm(false); // Hide the confirmation dialog
        setDeleteMethodId(null); // Clear the delete method id
        setError(null); // Clear any previous error
      } catch (error) {
        console.error("Error deleting payment method:", error);
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
        <ul>
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
      )}
      {editMethodId && (
        <div className="edit-method">
          <h3>Edit Payment Method</h3>
          <div>
            <input
              type="text"
              value={editMethodName}
              onChange={(e) => setEditMethodName(e.target.value)}
              placeholder="Edit method name"
            />
            <input
              type="text"
              value={editDescription}
              onChange={(e) => setEditDescription(e.target.value)}
              placeholder="Edit description"
            />
            <button className="save-button" onClick={handleEditMethod}>
              Save
            </button>
          </div>
        </div>
      )}
      {showDeleteConfirm && (
        <div className="delete-confirm-dialog">
          <p>Are you sure you want to delete this payment method?</p>
          <div className="button-container">
            <button onClick={handleDeleteMethod}>Xóa</button>
            <button
              onClick={() => {
                setShowDeleteConfirm(false);
                setDeleteMethodId(null);
              }}
            >
              Hủy
            </button>
          </div>
        </div>
      )}
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

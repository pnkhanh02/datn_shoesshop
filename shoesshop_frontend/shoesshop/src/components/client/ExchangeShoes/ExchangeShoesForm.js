import React, { useState } from "react";
import {
  Form,
  Input,
  DatePicker,
  InputNumber,
  Button,
  Upload,
  message,
} from "antd";
import { UploadOutlined, ArrowLeftOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { storage } from "../../../firebase";
import imageCompression from "browser-image-compression";
import { ref, getDownloadURL, uploadBytesResumable } from "firebase/storage";
import { PlusOutlined } from "@ant-design/icons";
import "./ExchangeShoesForm.css";

const normFile = (e) => {
  if (Array.isArray(e)) {
    return e;
  }
  return e?.fileList;
};

const ExchangeShoesForm = () => {
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const userData = JSON.parse(localStorage.getItem("user"));

  const handleSubmit = async (data) => {
    // Send API request
    axios
      .post("http://localhost:8080/api/v1/exchange-shoes/create", data, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        console.log("Add exchangeShoes successfully");
        message.success("Tạo sản phẩm cũ thành công!");
        navigate("/client/exchangeShoes");
      })
      .catch((error) => {
        console.error("Error creating exchange shoes:", error);
        message.error("Đã xảy ra lỗi khi tạo sản phẩm!");
      });
  };

  const onFinish = async (values) => {
    setLoading(true);

    const file = values.file[0].originFileObj;

    if (!file) return;

    const maxImageSize = 1024;

    try {
      let compressedFile = file;

      if (file.size > maxImageSize) {
        compressedFile = await imageCompression(file, {
          maxSizeMB: 0.8,
          maxWidthOrHeight: maxImageSize,
          useWebWorker: true,
        });
      }

      const storageRef = ref(storage, `shoesshop/${compressedFile.name}`);
      const uploadTask = uploadBytesResumable(storageRef, compressedFile);

      uploadTask.on(
        "state_changed",
        (snapshot) => {},
        (error) => {
          console.log(error);
        },
        () => {
          getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
            const newExchangeShoes = {
              exchangeShoesName: values.exchangeShoesName,
              exchangeShoesType: values.exchangeShoesType,
              purchaseDate: values.purchaseDate.format("YYYY-MM-DD"),
              price: values.price,
              description: values.description,
              img_url: downloadURL,
              customerId: userData.id,
            };

            handleSubmit(newExchangeShoes);

            setLoading(false);
          });
        }
      );
    } catch (error) {
      console.error("Image Compression Error:", error);
    }
  };

  // Handle the back button click
  const handleBack = () => {
    navigate(-1); // Quay lại trang trước đó (ExchangeShoesClient)
  };

  return (
    <div className="exchange-shoes-container">
      <Form.Item>
        <Button
          type="default"
          onClick={handleBack} // Định nghĩa hàm quay lại
          icon={<ArrowLeftOutlined />} // Thêm icon mũi tên trái
          className="exchange-shoes-back-button"
        ></Button>
      </Form.Item>
      <h2 className="exchange-shoes-title">Thu Cũ Đổi Mới Giày</h2>
      <Form
        form={form}
        layout="vertical"
        onFinish={onFinish}
        disabled={loading}
        className="exchange-shoes-form"
      >
        <Form.Item
          label="Tên giày"
          name="exchangeShoesName"
          rules={[{ required: true, message: "Vui lòng nhập tên giày!" }]}
        >
          <Input placeholder="Nhập tên giày" />
        </Form.Item>

        <Form.Item
          label="Loại giày"
          name="exchangeShoesType"
          rules={[{ required: true, message: "Vui lòng nhập loại giày!" }]}
        >
          <Input placeholder="Nhập loại giày" />
        </Form.Item>

        <Form.Item
          label="Thời gian mua giày"
          name="purchaseDate"
          rules={[{ required: true, message: "Vui lòng chọn thời gian mua!" }]}
        >
          <DatePicker />
        </Form.Item>

        <Form.Item
          label="Giá tiền (VNĐ)"
          name="price"
          rules={[{ required: true, message: "Vui lòng nhập giá tiền!" }]}
        >
          <InputNumber
            placeholder="Nhập giá tiền"
            className="exchange-shoes-input-number"
            style={{ width: "100%" }}
            min={0}
          />
        </Form.Item>

        <Form.Item
          label="Mô tả tình trạng giày"
          name="description"
          rules={[
            { required: true, message: "Vui lòng nhập mô tả tình trạng giày!" },
          ]}
        >
          <Input.TextArea placeholder="Nhập mô tả tình trạng giày" rows={4} />
        </Form.Item>

        <Form.Item
          label="Ảnh giày muốn đổi"
          name="file"
          valuePropName="fileList"
          getValueFromEvent={normFile}
          rules={[{ required: true, message: "Vui lòng tải lên ảnh giày!" }]}
        >
          <Upload action="/upload.do" listType="picture-card" maxCount={1}>
            <div>
              <PlusOutlined />
              <div
                style={{
                  marginTop: 8,
                }}
              >
                Upload
              </div>
            </div>
          </Upload>
        </Form.Item>

        <Form.Item style={{ display: "flex", justifyContent: "flex-end" }}>
          <Button
            type="primary"
            htmlType="submit"
            className="exchange-shoes-submit"
          >
            Gửi thông tin
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default ExchangeShoesForm;

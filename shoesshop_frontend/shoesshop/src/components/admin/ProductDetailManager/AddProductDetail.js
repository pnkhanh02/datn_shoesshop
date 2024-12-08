import { Breadcrumb, Button, Flex, Form, Input, message, Upload } from "antd";
import React, { useEffect, useState } from "react";
import { PlusOutlined } from "@ant-design/icons";
import { storage } from "../../../firebase";
import imageCompression from "browser-image-compression";
import { ref, getDownloadURL, uploadBytesResumable } from "firebase/storage";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const { TextArea } = Input;

const normFile = (e) => {
  if (Array.isArray(e)) {
    return e;
  }
  return e?.fileList;
};

const AddProductDetail = () => {
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const [messageApi, contextHolder] = message.useMessage();
  const [fileList, setFileList] = useState([]);
  //const [productData, setProductData] = useState();
  const userData = JSON.parse(localStorage.getItem("user"));

  const successMessage = () => {
    messageApi.open({
      type: "success",
      content: "Thêm mới sản phẩm thành công",
    });
    setTimeout(() => {
      navigate(`/admin/products-detail/${id}`);
    }, 1000);
  };

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Thêm mới sản phẩm thất bại",
    });
    setTimeout(() => {
      navigate(`/admin/products-detail/${id}`);
    }, 1000);
  };

  const handleCreateProduct = async (data) => {
    axios
      .post(
        `http://localhost:8080/api/v1/productDetails/create`,
        data,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
        //     headers: {
        //         'Content-Type': 'application/json',
        //     }
        // }
      )
      .then((response) => {
        console.log("Update product successfully");
        successMessage();
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        errorMessage();
      });
  };

  const onFinish = async (values) => {
    setLoading(true);

    console.log(values);

    if (!values.file) {
      const newProductdetail = {
        quantity: values.quantity,
        img_url: values.img_url,
        color: values.color,
        size: values.size,
        product_id: parseInt(id),
      };

      handleCreateProduct(newProductdetail);

      setLoading(false);
    } else {
      const file = values.file[0].originFileObj;

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
              const newProductdetail = {
                quantity: values.quantity,
                img_url: downloadURL,
                color: values.color,
                size: values.size,
                product_id: parseInt(id),
              };

              handleCreateProduct(newProductdetail);
              setLoading(false);
            });
          }
        );
      } catch (error) {
        console.error("Image Compression Error:", error);
      }
    }
  };

  useEffect(() => {
    getProductByID();
  }, []);
  const getProductByID = () => {
    setLoading(true);
    axios
      .get(
        `http://localhost:8080/api/v1/productDetails/${id}`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
        //     headers: {
        //       "Content-Type": "application/json",
        //     },
        //   }
      )
      .then((response) => {
        console.log(response.data);
        //setProductData(response.data);
        setFileList([
          {
            uid: "-1",
            name: "productdetail_img.png",
            status: "done",
            url: response.data.img_url,
          },
        ]);
        //  console.log(productData)
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  return (
    <div>
      <Flex className="AddProductDetail" vertical="true" gap={50}>
        <Breadcrumb
          items={[
            {
              title: "Quản lý",
            },
            {
              title: (
                <Link to="/admin/products">
                  <span>Quản lý sản phẩm</span>
                </Link>
              ),
            },
            {
              title: "Chi tiết sản phẩm",
            },
            {
              title: `${id}`,
            },
            {
              title: "Thêm chi tiết sản phẩm",
            },
          ]}
        />

        <Form
          name="add-product-form"
          labelCol={{
            span: 10,
          }}
          wrapperCol={{
            span: 18,
          }}
          layout="horizontal"
          style={{
            maxWidth: 800,
          }}
          onFinish={onFinish}
          disabled={loading}
        >
          <Form.Item
            label="Số lượng: "
            name="quantity"
            rules={[
              {
                required: true,
                message: "Nhập số lượng sản phẩm!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Màu sắc"
            name="color"
            rules={[
              {
                required: true,
                message: "Nhập màu sản phẩm!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Kích cỡ"
            name="size"
            rules={[
              {
                required: true,
                message: "Nhập kích cỡ!",
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="file"
            label="Hình ảnh"
            valuePropName="fileList"
            getValueFromEvent={normFile}
            // rules={[
            //     {
            //         required: true,
            //         message: 'Chọn hình ảnh!',
            //     },
            // ]}
          >
            <Upload
              action="/upload.do"
              listType="picture-card"
              defaultFileList={fileList}
              maxCount={1}
            >
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
          <Form.Item wrapperCol={{ offset: 10 }}>
            <Button type="primary" htmlType="submit">
              Thêm chi tiết sản phẩm
            </Button>
          </Form.Item>
        </Form>
      </Flex>
    </div>
  );
};

export default AddProductDetail;

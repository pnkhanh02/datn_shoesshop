import React, { useEffect, useState } from "react";
import "./ProductDetail.css";
import { Link, useNavigate, useParams } from "react-router-dom";
import { message, Rate } from "antd";
import axios from "axios";
import { RATING_OPTIONS } from "./../../../constants/index";
import TextArea from "antd/es/input/TextArea";

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState({});
  const [productdetail, setProductDetail] = useState([]);
  const [selecteddetail, setSelectedDetail] = useState("");
  const [img, setImg] = useState([]);
  const [color, setColor] = useState([]);
  const [size, setSize] = useState([]);
  const [countProduct, setCountProduct] = useState(1);
  const [recommendedProduct, setRecommendedProduct] = useState([]);
  const [selectedColor, setSelectedColor] = useState("");
  const [selectedSize, setSelectedSize] = useState("");
  const [currentImageUrl, setCurrentImageUrl] = useState(product.image_url);

  const [rating, setRating] = useState(0);
  const [comment, setComment] = useState("");
  const [feedbacks, setFeedbacks] = useState([]);

  const userData = JSON.parse(localStorage.getItem("user"));
  const [messageApi, contextHolder] = message.useMessage();
  const successMessage = () => {
    messageApi.open({
      type: "success",
      content: "Thêm sản phẩm thành công",
    });
  };

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Thêm sản phẩm thất bại",
    });
  };

  const errorMessage2 = () => {
    messageApi.open({
      type: "error",
      content: "Vui lòng đăng nhập để thực hiện tính năng này!",
    });
  };
  // eslint-disable-next-line no-unused-vars
  const navigate = useNavigate();

  const submitFormCart = () => {
    if (userData?.role === "CUSTOMER") {
      const data = {
        quantity: countProduct,
        product_detail_id: selecteddetail,
        customer_id: userData.id,
      };

      axios
        .post("http://localhost:8080/api/v1/orderItems/create", data, {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        })
        .then((response) => {
          console.log("Add cart successfully");
          successMessage();
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
          errorMessage();
        });
    } else {
      errorMessage2();
    }
  };

  const checkQuantity = (detailId) => {
    const getinforbyid = size.filter((s) => {
      return s.id === detailId;
    })[0];
    if (getinforbyid.quantity >= countProduct) {
      return true;
    } else {
      return false;
    }
  };

  const getSizeByColor = (_color) => {
    const size_arr = productdetail.filter((product) => {
      return product.color === _color;
    });
    console.log(size_arr);
    setSize(size_arr);
  };

  useEffect(() => {
    window.scrollTo(0, 0);

    axios
      .get("http://localhost:8080/api/v1/products/full", {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        const product_arr = response.data;
        const curr = product_arr.filter((p) => {
          return p.id.toString() === id;
        })[0];
        setProduct(curr);
        setCurrentImageUrl(curr.image_url);
        setRecommendedProduct(product_arr);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });

    // Fetch feedbacks for the product
    axios
      .get("http://localhost:8080/api/v1/feedbacks/getAll", {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then(({ data }) => {
        const productFeedbacks = data
          .filter((fb) => fb.product_id.toString() === id)
          .map((item) => ({ ...item, rating: RATING_OPTIONS[item.rating] }));
        setFeedbacks(productFeedbacks);
      })
      .catch((error) => {
        console.error("Error fetching feedbacks:", error);
      });

    axios
      .get(`http://localhost:8080/api/v1/products/productDetail/${id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        // console.log(response.data);
        const data = response.data;
        setProductDetail(data);
        // Loại bỏ các phần tử trùng lặp dựa trên img_url
        const uniqueData = data.filter(
          (item, index, self) =>
            index === self.findIndex((t) => t.img_url === item.img_url)
        );
        setImg(uniqueData);

        const color = data.filter(
          (item, index, self) =>
            index === self.findIndex((t) => t.color === item.color)
        );
        setColor(color);
        // console.log(color);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, [id]);

  const calculateAverageRating = (feedbacks) => {
    if (feedbacks.length === 0) return 0; // Nếu không có đánh giá, trả về 0
    const totalRating = feedbacks.reduce((sum, feedback) => sum + feedback.rating, 0);
    return (totalRating / feedbacks.length).toFixed(1); // Làm tròn đến 1 chữ số thập phân
  };
  

  return (
    <div className="ProductDetail">
      {contextHolder}
      <div className="ProductDetail_container">
        <div className="ProductDetail_container_left">
          <Link to="client/product">
            <ion-icon name="arrow-back"></ion-icon>
          </Link>
          <div className="PD_con_left_mainImg">
            <img src={currentImageUrl} alt="" />
          </div>
          <div className="PD_con_left_selectImg">
            <div onClick={() => setCurrentImageUrl(product.image_url)}>
              <img src={product.image_url} alt="" />
            </div>
            {img.map((image, index) => (
              <div
                key={index}
                onClick={() => setCurrentImageUrl(image.img_url)}
              >
                <img src={image.img_url} alt="" />
              </div>
            ))}
          </div>
        </div>
        <div className="ProductDetail_container_right">
          <div className="PD_con_right_info">
            <div className="PD_con_right_info_name">
              <h2>{product.name}</h2>
              <span>{product.type_name?.toUpperCase()}</span>
            </div>
            <div className="PD_con_right_info_price">
              {product.sale_percent !== 0 ? (
                <>
                  <h3>
                    {(product.price * (1 - product.sale_percent / 100)).toFixed(
                      0
                    )}
                    vnđ
                  </h3>
                  <span>{product.price}vnđ</span>
                  <h5>-{product.sale_percent}%</h5>
                </>
              ) : (
                <h3>{product.price} vnđ</h3>
              )}
            </div>
            <div>{product.description}</div>
            <div className="PD_con_right_info_color">
              <span>Màu sắc</span>
              <div>
                {color.map((currentColor, index) => (
                  // <p>{currentColor.color}</p>

                  <button
                    className={
                      selectedColor === currentColor.color ? "selectColor" : ""
                    }
                    key={index}
                    style={{ backgroundColor: currentColor.color }}
                    onClick={() => {
                      getSizeByColor(currentColor.color);
                      setSelectedColor(currentColor.color);
                    }}
                  ></button>
                ))}
              </div>
            </div>
            <div className="PD_con_right_info_size">
              <span>Size</span>
              <div>
                {size.map((sizes, index) => {
                  const temp = checkQuantity(sizes.id) ? "" : "disableClick";
                  return (
                    <button
                      className={
                        selectedSize === sizes.size
                          ? `selectSize ${temp}`
                          : `${temp}`
                      }
                      onClick={() => {
                        setSelectedSize(sizes.size);
                        setSelectedDetail(sizes.id);
                      }}
                    >
                      {sizes.size}
                    </button>
                  );
                })}
              </div>
            </div>
            <div className="PD_con_right_info_count">
              <span>Số lượng</span>
              <div>
                <button
                  onClick={() => setCountProduct(Math.max(countProduct - 1, 1))}
                >
                  -
                </button>
                <span>{countProduct}</span>
                <button onClick={() => setCountProduct(countProduct + 1)}>
                  +
                </button>
              </div>
            </div>
          </div>
          <button onClick={() => submitFormCart()}>Thêm vào giỏ hàng</button>
        </div>
      </div>

      <div className="ProductDetail_recommended">
      <div style={{ display: "flex", alignItems: "center" }}>
    <h3>Đánh giá</h3>
    {feedbacks.length > 0 && (
      <div style={{ marginLeft: "10px", display: "flex", alignItems: "center" }}>
        <span>({calculateAverageRating(feedbacks)})</span>
        {/* <Rate
          value={calculateAverageRating(feedbacks)}
          disabled
          style={{ fontSize: "16px", marginRight: "5px" }}
        /> */}
      </div>
    )}
  </div>
        <div style={{ marginLeft: "20px" }}>
          {feedbacks.map((feedback, index) => (
            <div
              key={index}
              style={{
                display: "flex",
                marginTop: "25px",
                alignItems: "center",
              }}
            >
              <div style={{ width: "150px" }}> {feedback.customer_name} </div>
              <div
                style={{
                  marginLeft: "20px",
                  display: "flex",
                  flexDirection: "column",
                }}
              >
                <Rate
                  value={feedback.rating}
                  disabled
                  style={{ marginBottom: "10px" }}
                />
                <TextArea
                  value={feedback.comment}
                  disabled
                  style={{
                    color: "black",
                    backgroundColor: "white",
                    resize: "none",
                    width: "250px",
                    height: "40px",
                  }}
                />
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="ProductDetail_recommended">
        <h3>Sản phẩm tương tự</h3>
        <div className="Shop_right">
          {recommendedProduct.slice(0, 10).map((shoes) => {
            return (
              <Link
                className="Shop_right_shoes"
                key={shoes.id}
                to={`/client/product/${shoes.id}`}
              >
                <div className="shoes_top">
                  <div className="shoes_top_img">
                    {shoes.sale_percent !== 0 && (
                      <span className="shoes_tags">-{shoes.sale_percent}%</span>
                    )}
                    <img src={shoes.image_url} alt="" />
                  </div>
                  <div className="shoes_top_info">
                    <h3>{shoes.name}</h3>
                    <span>{shoes.type_name?.toUpperCase()}</span>
                  </div>
                </div>
                <div className="shoes_bottom">
                  {/* <h4>
                      {(shoes.price * (1 - product.sale_percent / 100)).toFixed(
                        2
                      )}
                      $
                      
                    </h4> */}

                  <h4>{shoes.price} Vnd</h4>
                </div>
              </Link>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;

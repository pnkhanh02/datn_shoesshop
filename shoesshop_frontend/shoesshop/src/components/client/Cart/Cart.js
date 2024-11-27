import React, { useEffect, useState } from "react";
import "./Cart.css";
import { message } from "antd";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Cart = () => {
  const [messageApi, contextHolder] = message.useMessage();
  const navigate = useNavigate();
  const [cartdetails, setCartDetail] = useState([]);
  const userData = JSON.parse(localStorage.getItem("user"));
  const [selectedItems, setSelectedItems] = useState({});

  function updateQuantityById(_id, _value) {
    const newCartDetails = cartdetails.map((obj) => {
      if (obj.id === _id) {
        return { ...obj, quantity: Math.max(obj.quantity + _value, 1) };
      }
      return obj;
    });

    setCartDetail(newCartDetails);
  }

  const selectAllItems = (e) => {
    const isChecked = e.target.checked;
    const updatedSelectedItems = {};
    cartdetails.forEach((item) => {
      updatedSelectedItems[item.id] = isChecked;
    });
    setSelectedItems(updatedSelectedItems);
  };

  const handleSelectItem = (e, itemId) => {
    const isChecked = e.target.checked;
    setSelectedItems({
      ...selectedItems,
      [itemId]: isChecked,
    });
    console.log(selectedItems);
  };

  const errorMessage2 = () => {
    messageApi.open({
      type: "error",
      content: "Vui lòng đăng nhập để thực hiện tính năng này!",
    });
  };

  const handleGetTotal = () => {
    const newOrdersArr = cartdetails
      .map((order) => ({
        ...order,
        checked: selectedItems[order.id] || false,
      }))
      .filter((order) => order.checked);

    return newOrdersArr.reduce(
      (total, product) => total + (product.quantity * product.price || 0),
      0
    );
  };

  const handleSendCheckout = () => {
    navigate("/client/checkout", { state: [cartdetails, selectedItems] });
  };

  useEffect(() => {
    window.scrollTo(0, 0);
    if (userData?.role === "CUSTOMER") {
      const id = userData.id;
      axios
        .get(
          `http://localhost:8080/api/v1/orders/getCartByCustomer/${id}`
              , 
              {
                headers: {
                  Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
                },
              }
        )
        .then((response) => {
          const data = response.data.map((pd, index) => {
            return {
              imgSrc: pd.imgSrc,
              name: pd.name,
              brand: pd.brand,
              price: pd.price,
              size: pd.size,
              color: pd.color,
              id: pd.orderItem_id,
              quantity: pd.quantity,
            };
          });

          setCartDetail(data);
          console.log(data);
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    } else {
      errorMessage2();
    }
  }, []);

  return (
    <div className="Cart">
      <div className="Cart_left">
        <div className="Cart_left_title">
          <h2>Giỏ hàng</h2>
          <span>{cartdetails.length} sản phẩm</span>
        </div>
        <div className="Cart_left_table">
          <table>
            <thead>
              <tr>
                <th>
                  <input type="checkbox" onChange={selectAllItems} />
                </th>
                <th>Sản phẩm</th>
                <th>Số lượng</th>
                <th>Đơn giá</th>
                <th>
                  <i className="fa-solid fa-trash-can"></i>
                </th>
              </tr>
            </thead>
            <tbody>
              {cartdetails.length !== 0 ? (
                cartdetails.map((cartdetail, index) => {
                  return (
                    <CartItem
                      key={cartdetail.id}
                      shoese={cartdetail}
                      handleSelectItem={handleSelectItem}
                      setSelectedItems={setSelectedItems}
                      selectedItems={selectedItems}
                      cartdetails={cartdetails}
                      updateQuantityById={updateQuantityById}
                    />
                  );
                })
              ) : (
                <tr>
                  <td colSpan={5} style={{ textAlign: "center" }}>
                    Hãy thêm sản phẩm vào giỏ
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
      <div className="Cart_right">
        {cartdetails.length !== 0 ? (
          <>
            <h3>Hoá đơn tạm tính</h3>
            <div>
              <h4>
                Tổng sản phẩm (
                {Object.values(selectedItems).reduce(
                  (count, value) => count + (value === true ? 1 : 0),
                  0
                )}
                )
              </h4>
              <span>
                {handleGetTotal()
                  .toString()
                  .replace(/\B(?=(\d{3})+(?!\d))/g, ".")}
                đ
              </span>
            </div>
            {/* <div>
                  <h4>Phí vận chuyển</h4>
                  <span>50.000đ</span>
                </div> */}
            <hr></hr>
            <div className="Cart_right_total">
              <h4>Tổng cộng</h4>
              <span>
                {handleGetTotal()
                  .toString()
                  .replace(/\B(?=(\d{3})+(?!\d))/g, ".")}
                đ
              </span>
            </div>

            <button onClick={() => handleSendCheckout()}>
              <span>Tiến hành đặt hàng</span>
              <i className="fa-solid fa-arrow-right"></i>
            </button>
          </>
        ) : (
          <p>Giỏ hàng trống</p>
        )}
      </div>
    </div>
  );
};

const CartItem = (props) => {
  const {
    shoese,
    handleSelectItem,
    setSelectedItems,
    selectedItems,
    cartdetails,
    updateQuantityById,
  } = props;
  const [odrerDetails, setOrderDetail] = useState([]);
  const [messageApi, contextHolder] = message.useMessage();
  const [countProduct, setCountProduct] = useState(shoese.quantity);
  const userData = JSON.parse(localStorage.getItem("user"));
  const successMessage3 = () => {
    messageApi.open({
      type: "success",
      content: "Xóa sản phẩm thành công",
    });
  };

  const errorMessage3 = () => {
    messageApi.open({
      type: "error",
      content: "Xóa sản phẩm thất bại",
    });
  };
  const deleteCartItem = (iddelete) => {
    console.log(iddelete);
    axios
      .delete(
        `http://localhost:8080/api/v1/orderItems/delete/${iddelete}`
            , 
            {
              headers: {
                Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
              },
            }
      )
      .then((response) => {
        successMessage3();
        setSelectedItems([]);

        setTimeout(() => {
          window.location.reload();
        }, 500);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        errorMessage3();
      });
  };

  return (
    <tr className="CartItem">
      {contextHolder}
      <td>
        <input
          type="checkbox"
          checked={selectedItems[shoese.id] || false}
          onChange={(e) => handleSelectItem(e, shoese.id)}
        />
      </td>
      <td>
        <div className="CartItem_img">
          <img src={shoese.imgSrc} alt="" />
        </div>
        <div className="CartItem_info">
          <h4>{shoese.name}</h4>
          <div className="CartItem_info_variant">
            <div>
              {shoese.color} - {shoese.size}
            </div>
          </div>
        </div>
      </td>
      <td>
        <div>
          <button onClick={() => updateQuantityById(shoese.id, -1)}>
            <i className="fa-solid fa-minus"></i>
          </button>
          <span>{shoese.quantity}</span>
          <button onClick={() => updateQuantityById(shoese.id, 1)}>
            <i className="fa-solid fa-plus"></i>
          </button>
        </div>
      </td>
      <td>{shoese.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")}đ</td>
      <td>
        <div>
          <button className="delete" onClick={() => deleteCartItem(shoese.id)}>
            <i className="fa-solid fa-xmark"></i>
          </button>
        </div>
      </td>
    </tr>
  );
};

export default Cart;

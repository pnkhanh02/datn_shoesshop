import React, { useEffect, useState } from "react";
import "./Checkout.css";
import { message, Select } from "antd";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

const Checkout = () => {
  const location = useLocation();
  const navigate = useNavigate();
  let [orders, selectedItems] = location.state;
  const [productForCheckout, setProductForCheckout] = useState([]);
  const [currentUser, setCurrentUser] = useState();
  const [citys, setCitys] = useState([]);
  const [districts, setDistrict] = useState([]);
  const [wards, setWards] = useState([]);
  const [isCompleteOrder, setIsCompleteOrder] = useState(false);
  const [isPaymentMethod, setIsPaymentMethod] = useState(false);
  const [selectedCityOption, setSelectedCityOption] = useState(null);
  const [selectedMethod, setSelectedMethod] = useState(null);
  const [selectedDistrictOption, setSelectedDistrictOption] = useState(null);
  const [selectedWardOption, setSelectedWardOption] = useState(null);
  const [customerName, setCustomerName] = useState("");
  const [customerEmail, setCustomerEmail] = useState("");
  const [customerPhone, setCustomerPhone] = useState("");
  const [customerAddress, setCustomerAddress] = useState("");
  const [customerNote, setCustomerNote] = useState("");
  const [messageApi, contextHolder] = message.useMessage();

  const successMessage = () => {
    messageApi.open({
      type: "success",
      content: "Đặt hàng thành công",
    });
  };

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Đặt hàng thất bại",
    });
  };

  const getDistrict = (cityId) => {
    axios
      .get(`https://esgoo.net/api-tinhthanh/2/${cityId}.htm`)
      .then((response) => {
        const distrcitList = response.data.data.map((district) => {
          return {
            value: district.id,
            label: district.full_name,
          };
        });
        setDistrict(distrcitList);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  const getWard = (districtId) => {
    axios
      .get(`https://esgoo.net/api-tinhthanh/3/${districtId}.htm`)
      .then((response) => {
        const WardList = response.data.data.map((ward) => {
          return {
            value: ward.id,
            label: ward.full_name,
          };
        });
        setWards(WardList);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  useEffect(() => {
    const usertemp = JSON.parse(localStorage.getItem("user"));
    setCurrentUser(usertemp);
    setCustomerName(usertemp.fullName);
    setCustomerEmail(usertemp.email);
    setCustomerPhone(usertemp?.phone);

    if (orders == null) navigate("/cart");
    const newOrdersArr = orders.map((order) => ({
      ...order,
      checked: selectedItems[order.id] || false,
    }));

    console.log(newOrdersArr);
    setProductForCheckout(newOrdersArr.filter((order) => order.checked));

    window.scrollTo(0, 0);

    axios
      .get("https://esgoo.net/api-tinhthanh/1/0.htm")
      .then((response) => {
        const citys_arr = response.data.data.map((city) => {
          return {
            value: city.id,
            label: city.full_name,
          };
        });
        setCitys(citys_arr);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });

    axios
      .get("http://localhost:8080/api/v1/paymentMethods/all")
      .then((response) => {
        const payments_arr = response.data.map((p) => {
          return {
            value: p.id,
            label: p.name,
          };
        });
        console.log(response.data);
        setIsPaymentMethod(payments_arr);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });

    axios
      .get(
        "http://localhost:8080/api/v1/products/all?page=1&size=5&sort=id,desc&search="
      )
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  const handleOrderCheckout = () => {
    // setIsCompleteOrder(true);
    handleCompleteOrder();
  };

  const handleGetTotal = () => {
    return productForCheckout.reduce(
      (total, product) => total + (product.price * product.quantity || 0),
      0
    );
  };

  const handleChangeCityOption = (selectedCityOption) => {
    setSelectedCityOption(selectedCityOption);
    getDistrict(selectedCityOption.value);
    // let districts_arr = citys.filter((city) => {
    //   return city.value === selectedCityOption.value;
    // });

    // districts_arr = districts_arr[0].districts.map((district) => {
    //   return {
    //     value: district._id,
    //     label: district.name,
    //     wards: district.name_with_type,
    //   };
    // });

    // setDistrict(districts_arr);
    // setSelectedDistrictOption(null);
    // setSelectedWardOption(null);
  };

  const handleChangeDistrictOption = (selectedCityOption) => {
    setSelectedDistrictOption(selectedCityOption);
    getWard(selectedCityOption.value);
  };

  const handleChangeWardOption = (selectedDistrictOption) => {
    setSelectedWardOption(selectedDistrictOption);
  };

  const handleChangePaymentOption = (selectedPaymentOption) => {
    setSelectedMethod(selectedPaymentOption);
    console.log(selectedPaymentOption);
  };

  // const handleChangeDistrictOption = (selectedDistrictOption) => {
  //   setSelectedDistrictOption(selectedDistrictOption);
  //   let wards_arr = districts.filter((district) => {
  //     return district.value === selectedDistrictOption.value;
  //   });

  //   wards_arr = wards_arr[0].wards.map((ward) => {
  //     return {
  //       value: ward.code,
  //       label: ward.name,
  //     };
  //   });

  //   setWards(wards_arr);
  //   setSelectedWardOption(null);
  // };

  // const handleChangeWardOption = (selectedWardOption) => {
  //   setSelectedWardOption(selectedWardOption);
  // };
  const handleCompleteOrder = () => {
    //console.log(selectedCityOption);
    // console.log(selectedDistrictOption);
    // console.log(productForCheckout);
    // console.log(selectedMethod);
    // console.log(selectedWardOption);
    // console.log(customerPhone);
    // console.log(customerAddress);
    // console.log(customerNote);
    let fullAddress =
      customerAddress +
      ", " +
      selectedWardOption.label +
      ", " +
      selectedDistrictOption.label +
      ", " +
      selectedCityOption.label;

    const newOrder = {
      address: fullAddress,
      phone: customerPhone,
      customer_id: currentUser.id,
      payment_method_id: selectedMethod.value,
      orderItemForms: productForCheckout.map((pdfc) => {
        return {
          id: pdfc.id,
          quantity_item: pdfc.quantity,
        };
      }),
    };
    // console.log(fullAddress);

    axios
      .post("http://localhost:8080/api/v1/orders/create", newOrder, {
        auth: {
          username: currentUser.username,
          password: currentUser.password,
        },
      })
      .then((response) => {
        console.log("Add product successfully");
        successMessage();
        setTimeout(() => {
          setIsCompleteOrder(true);
        }, 2000);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        errorMessage();
      });
  };

  return (
    <div className="Checkout_container">
      {contextHolder}
      <div className="Checkout_status">
        <div className="Checkout_status_item">
          <i className="fa-solid fa-cart-shopping"></i>
          <span>Giỏ hàng</span>
        </div>
        <i className="fa-solid fa-arrow-right-long"></i>
        <div
          className={
            isCompleteOrder
              ? "Checkout_status_item"
              : "Checkout_status_item Checkout_status_item_active"
          }
        >
          <i class="fa-solid fa-pen-to-square"></i>
          <span>Hoàn tất thông tin</span>
        </div>
        <i className="fa-solid fa-arrow-right-long"></i>
        <div
          className={
            !isCompleteOrder
              ? "Checkout_status_item"
              : "Checkout_status_item Checkout_status_item_active"
          }
        >
          <i className="fa-solid fa-circle-check"></i>
          <span>Đặt hàng thành công</span>
        </div>
      </div>
      {isCompleteOrder ? (
        <CompleteOrder />
      ) : (
        <div className="Checkout">
          <div className="Checkout_left">
            <div className="Checkout_left_title">
              <h3>Thông tin giao hàng</h3>
            </div>
            <div className="Checkout_left_row">
              <span>Họ và tên</span>
              <input
                type="text"
                placeholder="Nhập họ và tên"
                value={customerName}
              />
            </div>
            <div className="Checkout_left_row">
              <span>Số điện thoại</span>
              <input
                type="text"
                placeholder="Nhập số điện thoại"
                value={customerPhone}
                onChange={(e) => {
                  setCustomerPhone(e.target.value);
                }}
              />
            </div>
            <div className="Checkout_left_row">
              <span>Email</span>
              <input
                type="text"
                placeholder="Nhập email"
                value={customerEmail}
              />
            </div>

            <test className="Checkout_left_row">
              <span>Tỉnh (Thành phố)</span>
              <Select
                options={citys}
                value={selectedCityOption}
                onChange={handleChangeCityOption}
                theme={(theme) => ({
                  ...theme,
                  borderRadius: 5,
                  minHeight: "50px",
                  colors: {
                    ...theme.colors,
                    primary25: "rgb(209, 209, 209)",
                    primary50: "rgb(209, 209, 209)",
                    primary: "black",
                  },
                })}
                className="select"
                placeholder="Chọn tỉnh/thành phố"
              />
            </test>

            <div className="Checkout_left_row_2col">
              <div className="Checkout_left_row">
                <span>Quận (Huyện)</span>
                <Select
                  options={districts}
                  value={selectedDistrictOption}
                  onChange={handleChangeDistrictOption}
                  theme={(theme) => ({
                    ...theme,
                    borderRadius: 5,
                    minHeight: "50px",
                    colors: {
                      ...theme.colors,
                      primary25: "rgb(209, 209, 209)",
                      primary50: "rgb(209, 209, 209)",
                      primary: "black",
                    },
                  })}
                  className="select"
                  placeholder="Chọn quận/huyện"
                />
              </div>
              <div className="Checkout_left_row">
                <span>Phường (Xã)</span>
                <Select
                  options={wards}
                  value={selectedWardOption}
                  onChange={handleChangeWardOption}
                  theme={(theme) => ({
                    ...theme,
                    borderRadius: 5,
                    minHeight: "50px",
                    colors: {
                      ...theme.colors,
                      primary25: "rgb(209, 209, 209)",
                      primary50: "rgb(209, 209, 209)",
                      primary: "black",
                    },
                  })}
                  className="select"
                  placeholder="Chọn phường/xã"
                />
              </div>
            </div>
            <div className="Checkout_left_row">
              <span>Số nhà:</span>
              <textarea
                type="text"
                placeholder="Nhập số nhà"
                value={customerAddress}
                onChange={(e) => {
                  setCustomerAddress(e.target.value);
                }}
              />
            </div>
            <div className="Checkout_left_row">
              <span>Phương thức thanh toán: </span>
              <Select
                options={isPaymentMethod}
                value={selectedMethod}
                onChange={handleChangePaymentOption}
                theme={(theme) => ({
                  ...theme,
                  borderRadius: 5,
                  minHeight: "50px",
                  colors: {
                    ...theme.colors,
                    primary25: "rgb(209, 209, 209)",
                    primary50: "rgb(209, 209, 209)",
                    primary: "black",
                  },
                })}
                className="select"
                placeholder="Chọn phương thức thanh toán"
              />
            </div>
            <div className="Checkout_left_row">
              <span>Ghi chú</span>
              <textarea
                type="text"
                placeholder="Nhập ghi chú cho đơn hàng"
                value={customerNote}
                onChange={(e) => {
                  setCustomerNote(e.target.value);
                }}
              />
            </div>
          </div>
          <div className="Checkout_right">
            <div className="Checkout_right_title">
              <h3>Hoá đơn của bạn</h3>
              <span>
                {productForCheckout ? productForCheckout.length : 0} sản phẩm
              </span>
            </div>
            <div className="Checkout_right_table">
              <div className="Checkout_right_table-row">
                <h4>Sản phẩm</h4>
                <h4>Giá</h4>
              </div>
              {orders &&
                productForCheckout.map((shoese, index) => {
                  return (
                    <div className="Checkout_right_table-row" key={index}>
                      <div className="table-row-left">
                        <div className="table-row-left_img">
                          <img src={shoese.imgSrc} alt="" />
                        </div>
                        <div className="CartItem_info">
                          <h4>{shoese.name}</h4>
                          <div className="CartItem_info_variant">
                            <div className="checkout_variant"></div>
                            <span>Size {shoese.size}</span>
                            <p>x{shoese.quantity}</p>
                          </div>
                        </div>
                      </div>
                      <div className="table-row-right">
                        <span>
                          {(shoese.price * shoese.quantity)
                            .toString()
                            .replace(/\B(?=(\d{3})+(?!\d))/g, ".")}
                          đ
                        </span>
                      </div>
                    </div>
                  );
                })}
              <div className="Checkout_right_table-row">
                <h4>Tổng sản phẩm</h4>
                <span>
                  {handleGetTotal()
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".")}
                  đ
                </span>
              </div>
              <div className="Checkout_right_table-row">
                <h4>Vận chuyển</h4>
                <span>50.000đ</span>
              </div>
              <div className="Checkout_right_table-row">
                <h4>Thành tiền</h4>
                <h3>
                  {(handleGetTotal() + 50000)
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".")}
                  đ
                </h3>
              </div>
              <button onClick={() => handleOrderCheckout()}>Đặt hàng</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

function CompleteOrder() {
  return <h1>Đặt hàng thành công</h1>;
}

export default Checkout;

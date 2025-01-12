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
  // const [customerEmail, setCustomerEmail] = useState("");
  const [customerPhone, setCustomerPhone] = useState("");
  const [customerAddress, setCustomerAddress] = useState("");
  const [customerNote, setCustomerNote] = useState("");
  const [messageApi, contextHolder] = message.useMessage();
  const [exchangeShoesData, setExchangeShoesData] = useState([]);
  const [exchangeShoesSales, setExchangeShoesSales] = useState(0);
  const [selectedExchangeShoeData, setSelectedExchangeShoeData] =
    useState(null);

  const userData = JSON.parse(localStorage.getItem("user"));

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

  // huyện
  const getDistrict = (cityId) => {
    axios
      .get(`https://esgoo.net/api-tinhthanh/2/${cityId}.htm`)
      .then((response) => {
        if (response.data && response.data.data.length > 0) {
          const districtList = response.data.data.map((district) => {
            return {
              value: district.id,
              label: district.full_name,
            };
          });
          setDistrict(districtList);
        } else {
          console.warn(`No districts found for cityId: ${cityId}`);
          setDistrict([]); // Reset districts nếu không có dữ liệu
        }
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  // xã
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
    // setCustomerEmail(usertemp.email);
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
      .get("http://localhost:8080/api/v1/paymentMethods/full", {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
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
        "http://localhost:8080/api/v1/products/all?page=1&size=10&sort=id,desc&search=",
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      )
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  const updateUsedExchangeShoes = async (id, updateData) => {
    try {
      const response = await axios.put(
        `http://localhost:8080/api/v1/exchange-shoes/updateUsed/${id}`,
        updateData,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Thay `userData.token` bằng token của bạn
          },
        }
      );
      console.log(response.data);
      message.success("Cập nhật trạng thái thành công!");
    } catch (error) {
      console.error("Lỗi khi cập nhật trạng thái:", error);
      message.error("Cập nhật trạng thái thất bại!");
    }
  };

  // const handleOrderCheckout = () => {
  //   // setIsCompleteOrder(true);
  //   handleCompleteOrder();
  // };

  const handleGetTotal = () => {
    return productForCheckout.reduce(
      (total, product) => total + (product.price * product.quantity || 0),
      0
    );
  };

  const shippingCost = 50000;
  const totalAmount = handleGetTotal() + shippingCost - exchangeShoesSales;
  const handleOrderCheckout = async () => {
    try {
      const selectedCity = citys.find(
        (city) => city.value === selectedCityOption
      );
      const selectedDistrict = districts.find(
        (district) => district.value === selectedDistrictOption
      );
      const selectedWard = wards.find(
        (ward) => ward.value === selectedWardOption
      );
  
      const selectedPaymentMethod = isPaymentMethod.find(
        (paymentMethod) => paymentMethod.value === selectedMethod
      );
  
      if (
        !customerName ||
        !customerPhone ||
        !selectedCity ||
        !selectedDistrict ||
        !selectedWard ||
        !selectedPaymentMethod
      ) {
        messageApi.open({
          type: "error",
          content: "Vui lòng điền đầy đủ thông tin (tên, số điện thoại, địa chỉ, phương thức thanh toán)",
        });
        return; // Nếu thiếu thông tin, không gửi yêu cầu.
      }

      let fullAddress =
      customerAddress +
      ", " +
      selectedWard.label +
      ", " +
      selectedDistrict.label +
      ", " +
      selectedCity.label;

      const newOrder = {
        address: fullAddress,
        phone: customerPhone,
        customer_id: currentUser.id,
        payment_method_id: selectedPaymentMethod.value,
        orderItemForms: productForCheckout.map((pdfc) => {
          return {
            id: pdfc.id,
            quantity_item: pdfc.quantity,
          };
        }),
      };

      const response = await axios.post(
        "http://localhost:8080/api/v1/payment/vn-pay", newOrder,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
          params: {
            // Thêm các tham số cần thiết nếu có, ví dụ:

            amount: totalAmount, // Tổng tiền thanh toán
            vnp_BankCode: "NCB", // Mã ngân hàng, có thể để null nếu không chọn
          },
        }
      );
      if (response.data.code === 200) {
        const { paymentUrl } = response.data.data;
        // handleCompleteOrder();
        // Kiểm tra nếu có selectedExchangeShoeData và gọi updateUsedExchangeShoes
        if (selectedExchangeShoeData) {
          updateUsedExchangeShoes(selectedExchangeShoeData.id, {
            used: selectedExchangeShoeData.used,
          });
        }
        // Chuyển hướng người dùng đến URL thanh toán
        window.location.href = paymentUrl;
      } else {
        alert("Không thể tạo thanh toán. Vui lòng thử lại!");
      }
    } catch (error) {
      console.error("Lỗi khi tạo thanh toán:", error);
      alert("Đã xảy ra lỗi trong quá trình thanh toán. Vui lòng thử lại!");
    }
  };


  // tỉnh, thành phố
  const handleChangeCityOption = (selectedCityOption) => {
    setSelectedCityOption(selectedCityOption);
    console.log(selectedCityOption);
    getDistrict(selectedCityOption);
  };

  // quận, huyện
  const handleChangeDistrictOption = (selectedCityOption) => {
    setSelectedDistrictOption(selectedCityOption);
    console.log(selectedCityOption);
    getWard(selectedCityOption);
  };

  // xã
  const handleChangeWardOption = (selectedDistrictOption) => {
    setSelectedWardOption(selectedDistrictOption);
  };

  const handleChangePaymentOption = (selectedPaymentOption) => {
    setSelectedMethod(selectedPaymentOption);
    console.log(selectedPaymentOption);
  };

  const handleCompleteOrder = () => {
    console.log("Customer Phone in order:", customerPhone);
    const selectedCity = citys.find(
      (city) => city.value === selectedCityOption
    );
    const selectedDistrict = districts.find(
      (district) => district.value === selectedDistrictOption
    );
    const selectedWard = wards.find(
      (ward) => ward.value === selectedWardOption
    );

    const selectedPaymentMethod = isPaymentMethod.find(
      (paymentMethod) => paymentMethod.value === selectedMethod
    );

    if (
      !selectedCity ||
      !selectedDistrict ||
      !selectedWard ||
      !selectedPaymentMethod
    ) {
      messageApi.open({
        type: "error",
        content: "Vui lòng điền đầy đủ thông tin!",
      });
      return; // Nếu thiếu thông tin, không gửi yêu cầu.
    }
    console.log(selectedCityOption);
    console.log(selectedDistrictOption);
    //console.log(productForCheckout);
    console.log(selectedMethod);
    console.log(selectedWardOption);
    //console.log(customerPhone);
    // console.log(customerAddress);
    // console.log(customerNote);
    let fullAddress =
      customerAddress +
      ", " +
      selectedWard.label +
      ", " +
      selectedDistrict.label +
      ", " +
      selectedCity.label;

    const newOrder = {
      address: fullAddress,
      phone: customerPhone,
      customer_id: currentUser.id,
      payment_method_id: selectedPaymentMethod.value,
      orderItemForms: productForCheckout.map((pdfc) => {
        return {
          id: pdfc.id,
          quantity_item: pdfc.quantity,
        };
      }),
    };

    axios
      .post("http://localhost:8080/api/v1/orders/create", newOrder, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        console.log(newOrder);
        console.log("Add product successfully");
        successMessage();
        setTimeout(() => {
          setIsCompleteOrder(true);
        }, 2000);
      })
      .catch((error) => {
        console.log(newOrder);
        console.error("Error fetching data:", error);
        errorMessage();
      });
  };

  const fetchData = () => {
    console.log(localStorage.getItem("user"));

    axios
      .get(
        `http://localhost:8080/api/v1/exchange-shoes/getAllByCustomerId/${userData.id}?page=0&size=99&sort=id,asc`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      )
      .then((response) => {
        // Lọc dữ liệu lấy những phần tử thỏa mãn điều kiện
        const filteredData = response.data.content
          .filter((item) => item.status === "APPROVE" && !item.used)
          .map((sl, index) => {
            return {
              key: sl.id,
              id: sl.exchangeShoesId,
              exchangeShoesName: sl.exchangeShoesName,
              status: sl.status,
              exchangeShoesSales: sl.exchangeShoesSales,
              used: sl.used,
            };
          });

        setExchangeShoesData(filteredData);
      })
      .catch((error) => {
        message.error("Failed to load data from API");
      });
  };

  useEffect(() => {
    fetchData(); // Gọi hàm để lấy dữ liệu exchangeShoes
  }, []);

  const handleExchangeShoesChange = (value) => {
    const selectedExchangeShoe = exchangeShoesData.find(
      (item) => item.id === value
    );
    if (selectedExchangeShoe) {
      setExchangeShoesSales(selectedExchangeShoe.exchangeShoesSales);

      // Lưu thông tin exchange shoe vào state
      setSelectedExchangeShoeData({
        id: selectedExchangeShoe.id,
        used: true,
      });
    } else {
      setExchangeShoesSales(0); // Nếu không chọn gì, giá trị sẽ là 0
    }
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
            {/* <div className="Checkout_left_row">
              <span>Email</span>
              <input
                type="text"
                placeholder="Nhập email"
                value={customerEmail}
              />
            </div> */}

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
            <div className="Checkout_left_row">
              <span>Giảm giá do thu lại giày cũ:</span>
              <Select
                options={exchangeShoesData.map((item) => ({
                  value: item.id,
                  label: `${item.exchangeShoesName} : ${item.exchangeShoesSales} đ`,
                }))}
                onChange={handleExchangeShoesChange}
                placeholder="Chọn giày để đổi"
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
                <span>
                  {shippingCost
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".")}
                  đ
                </span>
              </div>
              <div className="Checkout_right_table-row">
                <h4>Giảm giá thu lại giày cũ</h4>
                <span>
                  {exchangeShoesSales
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".")}
                  đ
                </span>
              </div>
              <div className="Checkout_right_table-row">
                <h4>Thành tiền</h4>
                <h3>
                  {totalAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")}
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

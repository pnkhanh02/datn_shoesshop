import { message } from "antd";
import React from "react";
import "./Header.css";
import { useEffect } from "react";
import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import Chatbot from "../client/Chatbot/Chatbot";

const Header = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [searchVisible, setSearchVisible] = useState(false);
  const [isLoginPopup, setIsLoginPopup] = useState(false);
  const [isRegisterPopup, setIsRegisterPopup] = useState(false);
  const [boxUserPopup, setBoxUserPopup] = useState(false);

  const [searchInput, setSearchInput] = useState("");

  const [currentUser, setCurrentUser] = useState();

  const [messageApi, contextHolder] = message.useMessage();
  const userData = JSON.parse(localStorage.getItem("user"));

  const success = () => {
    messageApi.open({
      type: "success",
      content: "Đăng nhập thành công",
    });
  };

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Đăng nhập thất bại",
    });
  };

  const handleSearchVisible = (e) => {
    setSearchVisible(!searchVisible);
    setSearchInput("");
  };

  const handleSearchClose = () => {
    setSearchInput("");
    setSearchVisible(!searchVisible);
  };

  const handleLogout = () => {
    localStorage.removeItem("user");
    navigate("/login");
    // window.location.reload();
  };

  useEffect(() => {
    setCurrentUser(JSON.parse(localStorage.getItem("user")));
    // console.log(JSON.parse(localStorage.getItem('user')));
  }, []);

  return (
    <>
      {contextHolder}
      {/* {
                
                isLoginPopup && <Login setIsLoginPopup={setIsLoginPopup} setIsRegisterPopup={setIsRegisterPopup} success={success} errorMessage={errorMessage} />
            }
            {
                isRegisterPopup && <Register setIsLoginPopup={setIsLoginPopup} setIsRegisterPopup={setIsRegisterPopup} success={success} errorMessage={errorMessage} />
            } */}

      <div className="Header">
        {!searchVisible ? (
          <div className="Header_logo">
            <h2>SHOES SHOP</h2>
          </div>
        ) : (
          <div className="Header_back" onClick={() => handleSearchClose()}>
            <i className="fa-solid fa-arrow-left"></i>
          </div>
        )}
        {!searchVisible && (
          <div className="Header_link">
            <Link
              to="/client"
              className={location.pathname === "/client" ? "active_link" : ""}
            >
              TRANG CHỦ
            </Link>
            <Link
              to="/client/product"
              className={
                location.pathname.startsWith("/product") ? "active_link" : ""
              }
            >
              SẢN PHẨM
            </Link>
            <Link
              to="/client/male"
              className={
                location.pathname.startsWith("/male") ? "active_link" : ""
              }
            >
              GIÀY NAM
            </Link>
            <Link
              to="/client/female"
              className={
                location.pathname.startsWith("/female") ? "active_link" : ""
              }
            >
              GIÀY NỮ
            </Link>
            <Link
              to="/client/exchangeShoes"
              className={
                location.pathname.startsWith("/exchangeShoes")
                  ? "active_link"
                  : ""
              }
            >
              THU CŨ ĐỔI MỚI
            </Link>
            {/* <Link to="/about-us" className={location.pathname.startsWith('/about-us') ? 'active_link' : ''}>
                            VỀ CHÚNG TÔI
                        </Link> */}
          </div>
        )}
        {searchVisible && (
          <div className="Header_searchBox">
            <input
              type="text"
              placeholder="Tìm kiếm"
              value={searchInput}
              onChange={(e) => setSearchInput(e.target.value)}
              autoFocus
            />
            <button>
              <i className="fa-solid fa-magnifying-glass"></i>
            </button>
          </div>
        )}
        <div className="Header_option">
          <div className="HO_main_option">
            <Chatbot />
          </div>
          {/* <div className="HO_main_option" onClick={(e) => handleSearchVisible(e)}><i className="fa-solid fa-magnifying-glass"></i></div> */}
          {!currentUser ? (
            <div
              className="HO_main_option"
              onClick={() => setIsLoginPopup(!isLoginPopup)}
            >
              <i className="fa-solid fa-user"></i>
            </div>
          ) : (
            <div
              className="HO_main_option"
              onClick={() => setBoxUserPopup(!boxUserPopup)}
            >
              <i className="fa-solid fa-user"></i>
            </div>
          )}
          <Link to="cart" className="Header_cart_icon HO_main_option">
            <i className="fa-solid fa-cart-shopping"></i>
          </Link>
          {boxUserPopup && (
            <div className="Header-user-box">
              <div className="User-box-top">
                <h4>{currentUser?.username}</h4>
                <span>{currentUser?.role}</span>
              </div>
              <hr></hr>
              <div className="User-box-option">
              <Link to={`/client/customerDetail/${userData.id}`}>
                <button>Thông tin tài khoản</button>
                </Link>
                {(currentUser?.role === "ADMIN" ||
                  currentUser?.role === "EMPLOYEE") && (
                  <Link to="/admin">
                    <button>Quản lý</button>
                  </Link>
                )}
                {currentUser?.role === "CUSTOMER" ? (
                  <Link to="/client/order">
                    <button>Đơn hàng của bạn</button>
                  </Link>
                ) : (
                  ""
                )}
                <button onClick={handleLogout}>Đăng xuất</button>
              </div>
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default Header;

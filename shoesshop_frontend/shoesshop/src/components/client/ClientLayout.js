import React from "react";
import "./ClientLayout.css";
import Header from "../Header/Header";
import Footer from "../Footer/Footer";
import { Route, Routes } from "react-router-dom";
import AboutShop from "./AboutShop/AboutShop";
import Home from "./Home/Home";
import Product from "./Product/Product";
import GenderFill from "./FillterGender/GenderFill";
import FemaleFilter from "./FillterGender/FemaleFilter";
import ProductDetail from "./ProductDetail/ProductDetail";
import Cart from "./Cart/Cart";
import Order from "./Order/Order";
import OrderDetail from "./Order/OrderDetail";
import Checkout from "./Checkout/Checkout";
import ExchangeShoesForm from "./ExchangeShoes/ExchangeShoesForm";
import ExchangeShoesClient from "./ExchangeShoes/ExchangeShoesClient";
import ExchangeShoesClientDetail from "./ExchangeShoes/ExchangeShoesClientDetail";
import CustomerDetail from "./CustomerDetail/CustomerDetail";

const ClientLayout = () => {
  return (
    <div className="ClientLayout">
      <Header />
      <div className="Content">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/product" element={<Product />} />
          <Route path="/product/:id" element={<ProductDetail />} />
          <Route path="/cart" element={<Cart />} />
          <Route path="/order" element={<Order />} />
          <Route path="/order/:id" element={<OrderDetail />} />
          <Route path="/checkout" element={<Checkout />} />
          <Route path="/male" element={<GenderFill />} />
          <Route path="/female" element={<FemaleFilter />} />
          <Route path="/about-us" element={<AboutShop />} />
          <Route path="/exchangeShoes" element={<ExchangeShoesClient/>} />
          <Route path="/exchange-shoes-form" element={<ExchangeShoesForm />} />
          <Route path="/exchangeShoes/:id" element={<ExchangeShoesClientDetail/>}/>
          <Route path="/customerDetail/:id" element={<CustomerDetail/>}/>
        </Routes>
      </div>
      <Footer />
    </div>
  );
};

export default ClientLayout;

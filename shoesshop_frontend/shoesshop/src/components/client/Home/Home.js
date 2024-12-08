import React, { useEffect } from "react";
import sneaker from "../../../images/sneaker.png";
import "./Home.css";
import { Link } from "react-router-dom";
import TopSellingProducts from "../Product/TopSellingProducts";
import TopRatingProducts from "../Product/TopRatingProducts";

const Home = () => {
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const shoeses = [
    {
      imgSrc:
        "https://saigonsneaker.com/wp-content/uploads/2019/08/giay-converse-chuck-1.jpg.webp",
      name: "Converse Chuck 70 Low Top Black (1970s)",
      brand: "NIKE",
      price: "1.160.000",
      old_price: "1.500.000",
    },
  ];

  return (
    <div className="Home">
      <div className="Home_cover">
        <div className="Home_cover_left">
          <span>NIKE</span>
          <h2>NIKE AIR MAX SNEAKER RUNNING</h2>
        </div>
        <img src={sneaker} alt="" />
        <h1>SPORT</h1>
        <div className="Home_cover_top">
          <span className="sale_tags">NEW</span>
          <div>
            <i class="fa-solid fa-star"></i>
            <i class="fa-solid fa-star"></i>
            <i class="fa-solid fa-star"></i>
            <i class="fa-solid fa-star"></i>
            <i class="fa-solid fa-star"></i>
          </div>
        </div>
        <div className="Home_cover_right">
          <div></div>
        </div>
      </div>

      <div className="Home_feature">
        <div>
          <i class="fa-solid fa-truck-fast"></i>
          <span>Giao hàng toàn quốc</span>
        </div>
        <div>
          <i class="fa-solid fa-crown"></i>
          <span>Chất lượng cao</span>
        </div>
        <div>
          <i class="fa-solid fa-tags"></i>
          <span>Mẫu mã đa dạng</span>
        </div>
        <div>
          <i class="fa-solid fa-thumbs-up"></i>
          <span>Support tận tình</span>
        </div>
      </div>

      <div className="Home_bestSeller">
        {/* <h2>Bán chạy nhất</h2>
        <div className="Shop_right">
          {shoeses.map((shoes, index) => {
            return (
              <Link
                className="Shop_right_shoes"
                key={index}
                to={`/product/${index}`}
              >
                <div className="shoes_top">
                  <div className="shoes_top_img">
                    <span className="shoes_tags">-15%</span>
                    <img src={shoes.imgSrc} alt="" />
                  </div>
                  <div className="shoes_top_info">
                    <h3>{shoes.name}</h3>
                    <span>{shoes.brand}</span>
                  </div>
                </div>
                <div className="shoes_bottom">
                  <h4>{shoes.price}đ</h4>
                  <span>{shoes.old_price}đ</span>
                </div>
              </Link>
            );
          })}
        </div> */}
        <TopSellingProducts/>

        <hr></hr>

        <TopRatingProducts/>
      </div>
    </div>
  );
};

export default Home;

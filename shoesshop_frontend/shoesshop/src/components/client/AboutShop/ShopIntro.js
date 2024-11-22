import React from 'react'

const ShopIntro = () => {
    const shopInfo = {
        name: "Shop Giày DKHV",
        slogan: "Mang đến cho bạn những bước đi phong cách!",
        products: [
          "Giày thể thao nam/nữ",
          "Giày chạy bộ công nghệ cao",
          "Phụ kiện đi kèm: tất, dây giày, ..."
        ],
        commitment: [
          "Chất lượng hàng đầu",
          "Thiết kế đa dạng, phong phú",
          "Dịch vụ hỗ trợ tận tâm"
        ],
        contact: {
          website: "www.shopgiayjsreact.com",
          email: "info@shopgiayjsreact.com",
          phone: "(+84) 123 456 789"
        }
      };
    
      return (
        <div className="shop-intro">
          <h1>{shopInfo.name}</h1>
          <p>{shopInfo.slogan}</p>
          
          <div className="section">
            <h2>Sản phẩm chính:</h2>
            <ul>
              {shopInfo.products.map((product, index) => (
                <li key={index}>{product}</li>
              ))}
            </ul>
          </div>
          
          <div className="section">
            <h2>Cam kết của chúng tôi:</h2>
            <ul>
              {shopInfo.commitment.map((commitment, index) => (
                <li key={index}>{commitment}</li>
              ))}
            </ul>
          </div>
          
          <div className="section">
            <h2>Liên hệ:</h2>
            <p>- Website: {shopInfo.contact.website}</p>
            <p>- Email: {shopInfo.contact.email}</p>
            <p>- Điện thoại: {shopInfo.contact.phone}</p>
          </div>
        </div>
      );
}

export default ShopIntro
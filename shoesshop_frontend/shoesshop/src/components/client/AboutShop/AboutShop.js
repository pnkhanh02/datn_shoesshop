import React, { useEffect } from 'react'
import ShopIntro from './ShopIntro';

const AboutShop = () => {
    useEffect(() => {
        window.scrollTo(0, 0); // Scroll to top when component mounts
      }, []);
    
      return (
        <div className="about-shop-container">
          <ShopIntro />
        </div>
      );
}

export default AboutShop
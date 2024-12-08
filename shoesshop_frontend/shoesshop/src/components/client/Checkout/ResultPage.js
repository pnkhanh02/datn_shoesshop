import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Card, Typography, Row, Col, Button } from "antd";

const { Title, Paragraph } = Typography;

const ResultPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const queryParams = new URLSearchParams(location.search);
  const status = queryParams.get("status");

  const handleBackToCheckout = () => {
    navigate("/client/cart");
  };

  return (
    <Row justify="center" align="middle" style={{ height: "70vh", backgroundColor: "white" }}>
      <Col>
        <Card
          style={{
            width: 450,
            textAlign: "center",
            borderRadius: 8,
            boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
          }}
        >
          {status === "success" ? (
            <div>
              <Title level={2} style={{ color: "#52c41a" }}>Thanh toán thành công!</Title>
              <Paragraph>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</Paragraph>
            </div>
          ) : (
            <div>
              <Title level={2} style={{ color: "#ff4d4f" }}>Thanh toán thất bại</Title>
              <Paragraph>Vui lòng thử lại hoặc liên hệ hỗ trợ khách hàng.</Paragraph>
              <Button 
                type="primary" 
                onClick={handleBackToCheckout} 
                style={{ marginTop: "20px" }}
              >
                Quay lại
              </Button>
            </div>
          )}
        </Card>
      </Col>
    </Row>
  );
};

export default ResultPage;

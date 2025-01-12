import React, { useEffect, useState } from "react";
import "./Order.css";
import {
  Button,
  ConfigProvider,
  Flex,
  Image,
  message,
  Popconfirm,
  Table,
  Tabs,
  Tag,
} from "antd";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const userData = JSON.parse(localStorage.getItem("user"));

function convertDateTimeFormat(originalDateTimeString) {
  // Chuyển đổi thành đối tượng Date
  const originalDate = new Date(originalDateTimeString);

  // Lấy các thành phần ngày tháng năm, giờ phút
  const day = originalDate.getDate().toString().padStart(2, "0");
  const month = (originalDate.getMonth() + 1).toString().padStart(2, "0");
  const year = originalDate.getFullYear();
  const hours = originalDate.getHours().toString().padStart(2, "0");
  const minutes = originalDate.getMinutes().toString().padStart(2, "0");

  // Định dạng ngày giờ mới
  const formattedDateTime = `${day}/${month}/${year}`;

  return formattedDateTime;
}

const Order = () => {
  const [messageApi, contextHolder] = message.useMessage();
  const [tabKey, setTabKey] = useState("1");

  const errorMessage = () => {
    messageApi.open({
      type: "error",
      content: "Vui lòng đăng nhập để thực hiện tính năng này!",
    });
  };

  const onChange = (key) => {
    setTabKey(key);
  };

  const items = [
    {
      key: "1",
      label: "Chờ xác nhận",
      children: (
        <div className="ContentOfMyOrder">{tabKey === "1" && <TO_PAY />}</div>
      ),
    },
    {
      key: "2",
      label: "Đang giao hàng",
      children: (
        <div className="ContentOfMyOrder">
          {tabKey === "2" && <TO_RECEIVE />}
        </div>
      ),
    },
    {
      key: "3",
      label: "Đã giao",
      children: (
        <div className="ContentOfMyOrder">
          {tabKey === "3" && <COMPLETED />}
        </div>
      ),
    },
    {
      key: "4",
      label: "Đã huỷ",
      children: (
        <div className="ContentOfMyOrder">{tabKey === "4" && <CANCELED />}</div>
      ),
    },
  ];

  return (
    <div className="MyOrder">
      <ConfigProvider
        theme={{
          components: {
            Tabs: {
              cardGutter: "10px",
              inkBarColor: "black",
              itemActiveColor: "black",
              itemHoverColor: "black",
              itemSelectedColor: "black",
            },
          },
        }}
      >
        <Tabs
          defaultActiveKey="1"
          items={items}
          centered
          size="large"
          onChange={onChange}
        />
      </ConfigProvider>
    </div>
  );
};

function TO_PAY() {
  const [ordereds, setOrdereds] = useState([]);
  const [loading, setLoading] = useState(false);

  const [messageApi, contextHolder] = message.useMessage();

  const successMessage = (msg) => {
    messageApi.open({
      type: "success",
      content: msg,
    });
  };

  const errorMessage = (msg) => {
    messageApi.open({
      type: "error",
      content: msg,
    });
  };

  const handleCancelOrder = (_orderId) => {
    axios
      .put(
        `http://localhost:8080/api/v1/orders/cancel/${_orderId}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
          },
        }
      )
      .then((response) => {
        console.log(response.data);
        fetchData();
        successMessage("Huỷ đơn hàng thành công");
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        errorMessage("Huỷ đơn hàng thất bại");
      });
  };

  const handleViewDetail = (orderId) => {
    navigate(`/client/order/${orderId}`);
  };

  const columns = [
    {
      title: "Đơn hàng",
      dataIndex: "group",
      key: "group",
      render: (record) => (
        <Flex align="center" gap={15}>
          <Image
            width={60}
            src={record.img_url}
            style={{ borderRadius: "5px" }}
          />
          <Flex vertical>
            <h3>{record.productName}</h3>
            <span>{record.subQuantity} sản phẩm</span>
          </Flex>
        </Flex>
      ),
    },
    {
      title: "Ngày đặt hàng",
      dataIndex: "orderDate",
      key: "orderDate",
      render: (text) => convertDateTimeFormat(text),
    },
    {
      title: "Tổng tiền",
      dataIndex: "totalAmount",
      key: "totalAmount",
      render: (text) => (
        <h3>{text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + "đ"}</h3>
      ),
    },
    {
      title: "Phương thức thanh toán",
      dataIndex: "paymentName",
      key: "paymentName",
    },
    {
      title: "Trạng thái",
      key: "status",
      dataIndex: "status",
      render: (text) => <Tag color="blue">{text.toUpperCase()}</Tag>,
    },
    {
      title: "Tuỳ chọn",
      key: "action",
      dataIndex: "action",
      render: (text, record) => (
        <div>
          <div style={{ marginBottom: 15 }}>
            <Button onClick={() => handleViewDetail(record.key)}>
              Xem chi tiết
            </Button>
          </div>
          <Popconfirm
            title="Huỷ đơn hàng này"
            placement="topRight"
            description="Bạn muốn huỷ đơn hàng này?"
            onConfirm={() => handleCancelOrder(text)}
            okText="Huỷ đơn"
            cancelText="Quay lại"
          >
            <Button>Huỷ đơn</Button>
          </Popconfirm>
        </div>
      ),
    },
  ];

  const fetchData = async () => {
    setLoading(true);

    axios
      .get(`http://localhost:8080/api/v1/orders/status/${userData.id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        console.log(response.data);

        let dataForState = response.data.map((pd) => {
          return {
            group: {
              img_url: pd.img_url,
              subQuantity: pd.subQuantity,
              productName: pd.productName,
            },
            status: pd.orderStatus,
            totalAmount: pd.totalAmount,
            key: pd.idOrder,
            orderDate: pd.orderDate,
            paymentName: pd.paymentName,
            action: pd.idOrder,
          };
        });

        dataForState = dataForState.filter((pd) => pd.status === "TO_PAY"
        ).sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));

        console.log(dataForState);

        setOrdereds(dataForState);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };
  const navigate = useNavigate();
  useEffect(() => {
    const userData = JSON.parse(localStorage.getItem("user"));
    if (userData?.role === "CUSTOMER") {
      fetchData();
    } else {
      navigate("/");
    }
  }, []);

  return (
    <div className="TO_PAY">
      {contextHolder}
      <Table
        columns={columns}
        dataSource={ordereds}
        size="large"
        loading={loading}
      />
    </div>
  );
}

function TO_RECEIVE() {
  const [ordereds, setOrdereds] = useState([]);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleViewDetail = (orderId) => {
    navigate(`/client/order/${orderId}`);
  };

  const columns = [
    {
      title: "Đơn hàng",
      dataIndex: "group",
      key: "group",
      render: (record) => (
        <Flex align="center" gap={15}>
          <Image
            width={60}
            src={record.img_url}
            style={{ borderRadius: "5px" }}
          />
          <Flex vertical>
            <h3>{record.productName}</h3>
            <span>{record.subQuantity} sản phẩm</span>
          </Flex>
        </Flex>
      ),
    },
    {
      title: "Ngày đặt hàng",
      dataIndex: "orderDate",
      key: "orderDate",
      render: (text) => convertDateTimeFormat(text),
    },
    {
      title: "Tổng tiền",
      dataIndex: "totalAmount",
      key: "totalAmount",
      render: (text) => (
        <h3>{text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + "đ"}</h3>
      ),
    },
    {
      title: "Phương thức thanh toán",
      dataIndex: "paymentName",
      key: "paymentName",
    },
    {
      title: "Trạng thái",
      key: "status",
      dataIndex: "status",
      render: (text) => <Tag color="orange">{text.toUpperCase()}</Tag>,
    },
    {
      title: "Tuỳ chọn",
      key: "action",
      dataIndex: "action",
      render: (text, record) => (
        <div style={{ marginBottom: 15 }}>
          <Button onClick={() => handleViewDetail(record.key)}>
            Xem chi tiết
          </Button>
        </div>
      ),
    },
  ];

  const fetchData = async () => {
    setLoading(true);

    axios
      .get(`http://localhost:8080/api/v1/orders/status/${userData.id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        console.log(response.data);

        let dataForState = response.data.map((pd) => {
          return {
            group: {
              img_url: pd.img_url,
              subQuantity: pd.subQuantity,
              productName: pd.productName,
            },
            status: pd.orderStatus,
            totalAmount: pd.totalAmount,
            key: pd.idOrder,
            orderDate: pd.orderDate,
            paymentName: pd.paymentName,
          };
        });

        dataForState = dataForState.filter((pd) => {
          return pd.status === "TO_RECEIVE";
        }).sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));;

        console.log(dataForState);

        setOrdereds(dataForState);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="TO_RECEIVE">
      <Table
        columns={columns}
        dataSource={ordereds}
        size="large"
        loading={loading}
      />
    </div>
  );
}

function COMPLETED() {
  const [ordereds, setOrdereds] = useState([]);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleViewDetail = (orderId) => {
    navigate(`/client/order/${orderId}`);
  };

  const columns = [
    {
      title: "Đơn hàng",
      dataIndex: "group",
      key: "group",
      render: (record) => (
        <Flex align="center" gap={15}>
          <Image
            width={60}
            src={record.img_url}
            style={{ borderRadius: "5px" }}
          />
          <Flex vertical>
            <h3>{record.productName}</h3>
            <span>{record.subQuantity} sản phẩm</span>
          </Flex>
        </Flex>
      ),
    },
    {
      title: "Ngày đặt hàng",
      dataIndex: "orderDate",
      key: "orderDate",
      render: (text) => convertDateTimeFormat(text),
    },
    {
      title: "Tổng tiền",
      dataIndex: "totalAmount",
      key: "totalAmount",
      render: (text) => (
        <h3>{text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + "đ"}</h3>
      ),
    },
    {
      title: "Phương thức thanh toán",
      dataIndex: "paymentName",
      key: "paymentName",
    },
    {
      title: "Trạng thái",
      key: "status",
      dataIndex: "status",
      render: (text) => <Tag color="green">{text.toUpperCase()}</Tag>,
    },
    {
      title: "Tuỳ chọn",
      key: "action",
      dataIndex: "action",
      render: (text, record) => (
        <div style={{ marginBottom: 15 }}>
          <Button onClick={() => handleViewDetail(record.key)}>
            Xem chi tiết
          </Button>
        </div>
      ),
    },
  ];

  const fetchData = async () => {
    setLoading(true);

    axios
      .get(`http://localhost:8080/api/v1/orders/status/${userData.id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        console.log(response.data);

        let dataForState = response.data.map((pd) => {
          return {
            group: {
              img_url: pd.img_url,
              subQuantity: pd.subQuantity,
              productName: pd.productName,
            },
            status: pd.orderStatus,
            totalAmount: pd.totalAmount,
            key: pd.idOrder,
            orderDate: pd.orderDate,
            paymentName: pd.paymentName,
          };
        });
        console.log(dataForState);

        dataForState = dataForState.filter((pd) => {
          return (pd.status === "COMPLETED" || pd.status === "FEEDBACK_COMPLETED") ;
        }).sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));;

        console.log(dataForState);

        setOrdereds(dataForState);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="COMPLETED">
      <Table
        columns={columns}
        dataSource={ordereds}
        size="large"
        loading={loading}
      />
    </div>
  );
}

function CANCELED() {
  const [ordereds, setOrdereds] = useState([]);
  const [loading, setLoading] = useState(false);

  const columns = [
    {
      title: "Đơn hàng",
      dataIndex: "group",
      key: "group",
      render: (record) => (
        <Flex align="center" gap={15}>
          <Image
            width={60}
            src={record.img_url}
            style={{ borderRadius: "5px" }}
          />
          <Flex vertical>
            <h3>{record.productName}</h3>
            <span>{record.subQuantity} sản phẩm</span>
          </Flex>
        </Flex>
      ),
    },
    {
      title: "Ngày đặt hàng",
      dataIndex: "orderDate",
      key: "orderDate",
      render: (text) => convertDateTimeFormat(text),
    },
    {
      title: "Tổng tiền",
      dataIndex: "totalAmount",
      key: "totalAmount",
      render: (text) => (
        <h3>{text.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + "đ"}</h3>
      ),
    },
    {
      title: "Phương thức thanh toán",
      dataIndex: "paymentName",
      key: "paymentName",
    },
    {
      title: "Trạng thái",
      key: "status",
      dataIndex: "status",
      render: (text) => <Tag color="red">{text.toUpperCase()}</Tag>,
    },
  ];

  const fetchData = async () => {
    setLoading(true);

    axios
      .get(`http://localhost:8080/api/v1/orders/status/${userData.id}`, {
        headers: {
          Authorization: `Bearer ${userData.token}`, // Đính kèm token vào header
        },
      })
      .then((response) => {
        console.log(response.data);

        let dataForState = response.data.map((pd) => {
          return {
            group: {
              img_url: pd.img_url,
              subQuantity: pd.subQuantity,
              productName: pd.productName,
            },
            status: pd.orderStatus,
            totalAmount: pd.totalAmount,
            key: pd.idOrder,
            orderDate: pd.orderDate,
            paymentName: pd.paymentName,
          };
        });

        dataForState = dataForState.filter((pd) => {
          return pd.status === "CANCELED";
        });

        console.log(dataForState);

        setOrdereds(dataForState);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="CANCELED">
      <Table
        columns={columns}
        dataSource={ordereds}
        size="large"
        loading={loading}
      />
    </div>
  );
}

export default Order;

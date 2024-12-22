import React, { useState, useRef, useEffect } from "react";
import axios from "axios";
import { Button, Input } from "antd";
import { SendOutlined } from "@ant-design/icons";
import "./Chatbot.css";
import { marked } from "marked";

const Chatbot = () => {
  const [messages, setMessages] = useState([]);
  const [userInput, setUserInput] = useState("");
  const [isChatOpen, setIsChatOpen] = useState(false);
  const chatBoxRef = useRef(null);
  const userData = JSON.parse(localStorage.getItem("user"));
  const customerId = userData ? userData.id : null;

  // Hàm gọi API để lấy lịch sử chat của user
  const fetchChatHistory = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/chatbot/getAllByCustomerId/${customerId}`,
        {
          headers: {
            Authorization: `Bearer ${userData.token}`,
          },
        }
      );
      const chatHistory = response.data;

      // Chuyển đổi dữ liệu chat history thành format phù hợp để hiển thị
      const formattedMessages = [];
      // Lặp qua tất cả các bản ghi chat để thêm vào formattedMessages
      chatHistory.forEach((chat) => {
        const userMessage = {
          role: "user",
          content: chat.message, // Tin nhắn người dùng
        };
        const botMessage = {
          role: "assistant",
          content: chat.response, // Tin nhắn chatbot
        };

        // Thêm tin nhắn người dùng và chatbot vào cùng một cặp
        formattedMessages.push(userMessage);
        formattedMessages.push(botMessage);
      });

      // Cập nhật messages với lịch sử chat
      setMessages(formattedMessages);
    } catch (error) {
      console.error("Error fetching chat history:", error);
    }
  };

  const handleSendMessage = async () => {
    if (!userInput.trim()) return;

    const userMessage = { role: "user", content: userInput };
    setMessages([...messages, userMessage]);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/chatbot/chat",
        {
          messages: userInput,
        },
        {
          headers: {
            Authorization: `Bearer ${userData.token}`,
          },
        }
      );

      setUserInput("");

      const botMessage = {
        role: "assistant",
        content: response.data.candidates[0].content.parts[0].text,
      };
      setMessages((prevMessages) => [...prevMessages, botMessage]);
    } catch (error) {
      console.error("Error communicating with the chatbot API:", error);
      setMessages((prevMessages) => [
        ...prevMessages,
        {
          role: "assistant",
          content: "Error: Could not fetch response from the chatbot.",
        },
      ]);
    }
  };

  const toggleChat = () => {
    setIsChatOpen((prev) => !prev);

    // Đặt thanh cuộn xuống cuối khi mở khung chat
    // if (!isChatOpen && chatBoxRef.current) {
    //   setTimeout(() => {
    //     chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
    //   }, 0); 
    // }
  };

  // Cuộn xuống cuối mỗi khi tin nhắn thay đổi
  useEffect(() => {
    if (chatBoxRef.current) {
      console.log("hello");
      chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
    }
  }, [messages.length, isChatOpen]);


  // Gọi API khi component mount để lấy lịch sử chat
  useEffect(() => {
    if (customerId) {
      fetchChatHistory();
    }
  }, [customerId]);

  return (
    <div className="chatbot-header-container">
      <div className="header-chat-icon" onClick={toggleChat}>
        <i className="fa-solid fa-message"></i>
      </div>

      {isChatOpen && (
        <div className="chatbot-container">
          <div className="chat-box" ref={chatBoxRef}>
            {messages.map((message, index) => (
              <div
                key={index}
                className={`message ${
                  message.role === "user" ? "user-message" : "assistant-message"
                }`}
              >
                <div
                  className="message-content"
                  dangerouslySetInnerHTML={{
                    __html: marked.parse(message.content),
                  }}
                />
              </div>
            ))}
          </div>

          <div className="input-container">
            <Input
              value={userInput}
              onChange={(e) => setUserInput(e.target.value)}
              placeholder="Type your message..."
              onPressEnter={handleSendMessage}
            />
            <Button
              type="primary"
              icon={<SendOutlined />}
              onClick={handleSendMessage}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default Chatbot;

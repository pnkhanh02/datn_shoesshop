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
  };

  // Cuộn xuống cuối mỗi khi tin nhắn thay đổi
  useEffect(() => {
    if (chatBoxRef.current) {
      console.log("hello");
      chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
    }
  }, [messages.length]);

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

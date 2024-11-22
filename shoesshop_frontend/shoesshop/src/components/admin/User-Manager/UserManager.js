import React, { useEffect, useState } from 'react';
import "./UserManager.css";
import axios from 'axios';
import { Input, Table } from 'antd';

const userData = JSON.parse(localStorage.getItem("user"));

const columns = [
  {
    title: "Id",
    dataIndex: "id",
  },
  {
    title: "Name",
    dataIndex: "fullName",
  },
  {
    title: "Gender",
    dataIndex: "gender",
  },
  {
    title: "Email",
    dataIndex: "email",
  },
];

const UserManager = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [searchText, setSearchText] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
  
    const fetchData = (page = 1, search = "") => {
      setLoading(true);
      axios
        .get(`http://localhost:8080/api/v1/accounts/getAll`, {
          params: {
            page: page - 1,
            size: 10,
            sort: "id,desc",
            search: search,
          },
        //   auth: {
        //     username: userData.username,
        //     password: userData.password,
        //   },
        })
        .then((response) => {
          console.log(response.data);
          const { content, totalPages, totalElements } = response.data;
          const accountFormatted = content.map((account) => ({
            id: account.id,
            fullName: account.firstName + " " + account.lastName,
            gender: account.gender,
            email: account.email,
          }));
          setData(accountFormatted);
          setTotalPages(totalPages);
          setTotalElements(totalElements);
          setLoading(false);
        })
        .catch((error) => {
          console.error("Error", error);
          setLoading(false);
        });
    };
  
    useEffect(() => {
      window.scrollTo(0, 0);
      fetchData(currentPage, searchText);
    }, [currentPage, searchText]);
  
    const handleSearch = () => {
      setCurrentPage(1);
      fetchData(1, searchText);
    };
  
    const handlePageChange = (newPage) => {
      setCurrentPage(newPage);
    };
  
    return (
      <div className="UserManager">
        <Input
          placeholder="Search by name"
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
          style={{ marginBottom: 20, width: 300 }}
          onPressEnter={handleSearch}
        />
        <Table
          columns={columns}
          rowKey={(record) => record.id}
          dataSource={data}
  
        />
    
        <div style={{ marginTop: 20, textAlign: "center" }}>
          <div className="Paginate">
            <button
              onClick={() => handlePageChange(currentPage - 1)}
              disabled={currentPage === 1}
            >
              Previous
            </button>
            <span>
              {currentPage} of {totalPages}
            </span>
            <button
              onClick={() => handlePageChange(currentPage + 1)}
              disabled={currentPage === totalPages}
            >
              Next
            </button>
          </div>
  
        </div>
      </div>
    );
  };
  
  export default UserManager;
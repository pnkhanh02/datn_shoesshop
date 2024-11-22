import React from 'react';
import "./Pagination.css";

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
    const numbers = [...Array(totalPages).keys()].map((i) => i + 1);
  
    return (
      <nav className="pagination-container">
        <ul className="pagination">
          <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
            <button
              className="page-link"
              onClick={() => onPageChange(currentPage - 1)}
              disabled={currentPage === 1}
            >
              Prev
            </button>
          </li>
          {numbers.map((n) => (
            <li
              key={n}
              className={`page-item ${currentPage === n ? "active" : ""}`}
            >
              <button
                className={`page-link ${currentPage === n ? "active" : ""}`}
                onClick={() => onPageChange(n)}
              >
                {n}
              </button>
            </li>
          ))}
          <li
            className={`page-item ${
              currentPage === totalPages ? "disabled" : ""
            }`}
          >
            <button
              className="page-link"
              onClick={() => onPageChange(currentPage + 1)}
              disabled={currentPage === totalPages}
            >
              Next
            </button>
          </li>
        </ul>
      </nav>
    );
  };
  
  export default Pagination;
  
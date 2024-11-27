import logo from './logo.svg';
import './App.css';
import AdminLayout from './components/admin/AdminLayout';
import ClientLayout from './components/client/ClientLayout';
import { Navigate, Route, Routes } from 'react-router-dom';
import Login from './components/Login/Login';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />

        <Route path="/admin/*" element={<AdminLayout />} />
        <Route path="/client/*" element={<ClientLayout />} />
        <Route path= "/login" element={<Login/>} />
      </Routes>
    </div>
  );
}

export default App;

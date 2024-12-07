import logo from './logo.svg';
import './App.css';
import AdminLayout from './components/admin/AdminLayout';
import ClientLayout from './components/client/ClientLayout';
import { Navigate, Route, Routes } from 'react-router-dom';
import Login from './components/Login/Login';
import Signup from './components/Signup/Signup';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />

        <Route path="/admin/*" element={<AdminLayout />} />
        <Route path="/client/*" element={<ClientLayout />} />
        <Route path= "/login" element={<Login/>} />
        <Route path= "/register" element={<Signup/>} />
      </Routes>
    </div>
  );
}

export default App;

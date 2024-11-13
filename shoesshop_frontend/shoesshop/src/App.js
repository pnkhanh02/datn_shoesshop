import logo from './logo.svg';
import './App.css';
import AdminLayout from './components/admin/AdminLayout';
import ClientLayout from './components/client/ClientLayout';
import { Route, Routes } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/admin/*" element={<AdminLayout />} />
        <Route path="/*" element={<ClientLayout />} />
      </Routes>
    </div>
  );
}

export default App;

// import Header from './components/Header';
import Footer from './components/Footer';
import Submain from './components/Submain';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import "./css/App.css";

function App() {
    return (
        <div className="app">
            {/* <Header /> */}
            <Routes>
                <Route path="/*" element={<HomePage />} />
                <Route path="/submain" element={<Submain />} />
            </Routes>
            <Footer />
        </div>
    );
}

export default App;

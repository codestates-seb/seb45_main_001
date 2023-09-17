// import Header from './components/Header';
import Footer from './components/Footer';
import Submain from './components/Submain';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import Mypage from './pages/Mypage';
import './css/App.css';
import Authguard from './components/Authguard';
import KoreaPage from './pages/KoreaPage';
import ForeignPage from './pages/ForeignPage';

function App() {
    return (
        <div className="app">
            {/* <Header /> */}
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/korea" element={<KoreaPage />} />
                <Route path="/foreign" element={<ForeignPage />} />
                <Route path="/submain" element={<Submain />} />
                <Route path="/mypage" element={<Authguard />}>
                
                
                </Route>
            </Routes>
            <Footer />
        </div>
    );
}

export default App;

import Footer from './components/Footer';
import Submain from './components/Submain';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import Mypage from './pages/Mypage';
import Authguard from './components/Authguard';
import KoreaPage from './pages/KoreaPage';
import ForeignPage from './pages/ForeignPage';
import './css/App.css';

function App() {
    return (
        <div className="app">
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/korea" element={<KoreaPage />} />
                <Route path="/foreign" element={<ForeignPage />} />
                <Route path="/Submain:/movieId" element={<Submain />} />
                <Route path="/mypage" element={<Authguard />}>
                    <Route index element={<Mypage />} />
                </Route>
            </Routes>
            <Footer />
        </div>
    );
}

export default App;

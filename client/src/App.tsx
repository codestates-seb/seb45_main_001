// import Header from './components/Header';
import Footer from './components/Footer';
import Content from './components/Content';
import Submain from './components/Submain';
import { Routes, Route } from 'react-router-dom';

function App() {
    return (
        <>
        {/* <Header /> */}
            <Routes>
                <Route path="/*" element={<Content/>}/>
                <Route path="/submain" element={<Submain/>}/>
            </Routes>
            <Footer />
         </>
    );
}

export default App;

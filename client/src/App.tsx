import Header from './components/Header';
import Footer from './components/Footer';
<<<<<<< HEAD

function App() {
  return (
    <>
      <Header />
      <div>메인입니다.</div>
      <Footer />
    </>
=======
import Content from './components/Content';

function App() {
  return (
    <div className="layout">
      <Header />
      <Content/>
      <Footer />
    </div>
>>>>>>> 301167a4c1839fddefbbf279720db8c59e7ed56a
  );
}

export default App;

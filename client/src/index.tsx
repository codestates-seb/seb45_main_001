import ReactDOM from 'react-dom/client';
import App from './App';
import { GlobalStyle } from './styled';
import { BrowserRouter } from 'react-router-dom';
import { Provider } from 'react-redux';
import { authstore } from './store/authstore';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
    <Provider store={authstore}>
        <GlobalStyle />
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </Provider>,
);

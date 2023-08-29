import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { GlobalStyle } from './styled';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement,
);
root.render(
  <>
  <GlobalStyle />
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  </>
);

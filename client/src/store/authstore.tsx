import { configureStore } from '@reduxjs/toolkit';
import dataReducer from '../slice/authslice';

export const authstore = configureStore({
    reducer: {
        data: dataReducer,
    },
});

export type AppDispatch = typeof authstore.dispatch;
export type RootState = ReturnType<typeof authstore.getState>;

//import { RootState } from '../store/authstore';
//import type { AppDispatch } from '../store/authstore';

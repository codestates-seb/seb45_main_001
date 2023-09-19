import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';

// any는 나중에 api의 data 들어오는 거 보고 바꿀 것

export interface DataState {
    // items: any[];
    // question: any | null;
    // users: { [key: string]: any };
    // answers: any[];
    status: 'idle' | 'loading' | 'succeeded' | 'failed';
    globalname: string;
    globalmail: string;
    isLogin: boolean,
}

// interface FetchPayload {
//     path: string;
//     data?: any; // post, patch 등에서 사용될 데이터
// }

// export const fetchData = createAsyncThunk<any, FetchPayload>('data/fetchData', async (payload) => {
//     return await apiCall({
//         method: 'GET',
//         url: payload.path,
//     });
// });

// export const postData = createAsyncThunk<any, FetchPayload>('data/postData', async (payload) => {
//     return await apiCall({
//         method: 'POST',
//         url: payload.path,
//         data: payload.data,
//     });
// });

// export const deleteData = createAsyncThunk<any, FetchPayload>('data/deleteData', async (payload) => {
//     return await apiCall({
//         method: 'DELETE',
//         url: payload.path,
//         data: payload.data,
//     });
// });

// export const patchData = createAsyncThunk<any, FetchPayload>('data/patchData', async (payload) => {
//     return await apiCall({
//         method: 'POST',
//         url: payload.path,
//         data: payload.data,
//     });
// });

// export const fetchUserById = createAsyncThunk('data/fetchUserById', async (memberId: string) => {
//     const response = await apiCall({
//         method: 'GET',
//         url: `users/${memberId}`,
//     });
//     return { memberId, user: response.data };
// });

// export const fetchAnswersByQuestionId = createAsyncThunk(
//     'data/fetchAnswersByQuestionId',
//     async (questionId: string) => {
//         const response = await apiCall({
//             method: 'GET',
//             url: `questions/${questionId}/answers`,
//         });
//         return response;
//     },
// );

const initialState: DataState = {
    // items: [],
    // question: null,
    // users: {},
    // answers: [],
    status: 'idle',
    globalname: '비로그인',
    globalmail: 'none@nope.com',
    isLogin: false,
};

export const DataState = createSlice({
    name: 'data',
    initialState: initialState,
    reducers: {
        updateName: (state, action: PayloadAction<string>) => {
            state.globalname = action.payload;
        },
        updateMail: (state, action: PayloadAction<string>) => {
            state.globalmail = action.payload;
        },
        updateLogin: (state, action: PayloadAction<boolean>) => {
            state.isLogin = action.payload;
        },
    },
    // extraReducers: (builder) => {
    //     builder
    //         .addCase(fetchUserById.fulfilled, (state, action: PayloadAction<any>) => {
    //             const { memberId, user } = action.payload;
    //             state.users[memberId] = user; // 사용자 ID를 키로 사용하여 사용자 정보 저장
    //         })
    //         .addCase(fetchData.pending, (state) => {
    //             state.status = 'loading';
    //         })
    //         .addCase(fetchData.fulfilled, (state, action: PayloadAction<any>) => {
    //             state.status = 'succeeded';
    //             if (Array.isArray(action.payload)) {
    //                 state.items = action.payload; // 배열 응답을 items에 저장
    //             } else {
    //                 state.question = action.payload; // 객체 응답을 question에 저장
    //             }
    //         })
    //         .addCase(fetchAnswersByQuestionId.fulfilled, (state, action: PayloadAction<any>) => {
    //             state.answers = action.payload;
    //         })
    //         .addCase(patchData.fulfilled, (state, action: PayloadAction<any>) => {
    //             const updatedPoint = action.payload.point;
    //             state.question.point = updatedPoint;
    //         })
    //         .addCase(fetchData.rejected, (state) => {
    //             state.status = 'failed';
    //         })
    //         .addCase(deleteData.pending, (state) => {
    //             state.status = 'loading';
    //         })
    //         .addCase(deleteData.fulfilled, (state, action: PayloadAction<any>) => {
    //             state.status = 'succeeded';
    //         })
    //         .addCase(deleteData.rejected, (state, action: PayloadAction<any>) => {
    //             state.status = 'failed';
    //         });
    // },
});

export const { updateName, updateMail, updateLogin } = DataState.actions;


export default DataState.reducer;



// import { useDispatch, useSelector } from 'react-redux';
// import { updateName } from '../slice/authslice';
// const dispatch = useDispatch();
// const globalName = useSelector((state) => state.data.name); 업데이트네임 사용예

// -----------------------------------------
// const [isNameEditing, setIsNameEditing] = useState<boolean>(false);  
// onChange={(e) => dispatch(updateName(e.target.value))} 업데이트 네임 수정하려면 dispatch 사용
// onBlur={() => setIsNameEditing(false)} 
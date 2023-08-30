import axios, { AxiosResponse, AxiosError, Method } from 'axios';

export const localapiUrl = ``;
export const apiUrl = ``;
export const jsonUrl = `http://localhost:5002`;

function getToken() {
    return localStorage.getItem('jwt');
}

const token = getToken();
const headers = {
    Authorization: `${token}`,
};

type ApiConfig<D> = {
    method: Method;
    url: string;
    data?: D;
};

// T와 D의 any는 아직 data가 확정되지 않았기 때문에 나중에 구체적인 type으로 바꿀 것

// export function apiCall<T = any, D = any>(config: ApiConfig<D>): Promise<T> {
//     const fullUrl = `${jsonUrl}/${config.url}`;
//     console.log('api:', fullUrl);

//     return axios({
//         method: config.method,
//         url: fullUrl,
//         data: config.data,
//         headers,
//     })
//         .then((response: AxiosResponse<T>) => {
//             console.log('Response Data:', response.data);
//             console.log('Response received:', response);
//             return response.data;
//         })
//         .catch((error: AxiosError) => {
//             console.log('Error occurred:', error);
//             throw error;
//         });
// }

export function apiCall<T = any, D = any>(config: ApiConfig<D>): Promise<AxiosResponse<T>> {
    const fullUrl = `${jsonUrl}/${config.url}`;
    console.log('api:', fullUrl);

    return axios({
        method: config.method,
        url: fullUrl,
        data: config.data,
        headers,
    })
        .then((response: AxiosResponse<T>) => {
            console.log('Response Data:', response.data);
            console.log('Response received:', response);
            return response; 
        })
        .catch((error: AxiosError) => {
            console.log('Error occurred:', error);
            throw error;
        });
}

// GET 예시

// apiCall<{ id: number; name: string }>({
//     method: 'GET',
//     url: 'endpoint',
// });

// POST 예시

// apiCall<{ id: number }, { email: string; password: string }>({
//     method: 'POST',
//     url: 'another_endpoint',
//     data: { email: 'test@example.com', password: 'password123' },
// });

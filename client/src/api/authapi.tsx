import axios, { AxiosResponse, AxiosError, Method } from 'axios';

export const localapiUrl = `http://52.79.212.94:8080`;
export const apiUrl = `http://13.209.157.148:8080`;
export const apilongurl = `http://ec2-13-209-157-148.ap-northeast-2.compute.amazonaws.com`;

export const jsonUrl = `http://localhost:5002`;

export const lastUrl = `${apiUrl}`;

type ApiConfig<D> = {
    method: Method;
    url: string;
    data?: D;
    headers?: Record<string, string>;
};

export function apiCall<T = any, D = any>(config: ApiConfig<D>,usePrefix = true): Promise<AxiosResponse<T>> {
    const fullUrl = usePrefix ? `${lastUrl}/${config.url}` : config.url;
    console.log('api:', fullUrl);
    console.log('origin', location.origin);
    // console.log('Request Headers:', headers);

    return axios({
        method: config.method,
        url: fullUrl,
        data: config.data,
        headers: config.headers,
        withCredentials: true,
    })
        .then((response: AxiosResponse<T>) => {
            // console.log('Response Data:', response.data);
            // console.log('Response received:', response);
            return response;
        })
        .catch((error: AxiosError) => {
            console.log('Error', error);
            throw error;
        });
}

// 사용예시

// apiCall<{ id: number; name: string }>({
//     method: 'GET',
//     url: 'endpoint',
// }, false false가 입력되었을 경우 url 초기화);

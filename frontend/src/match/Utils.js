import tokenService from "../services/token.service";

const jwt = tokenService.getLocalAccessToken();

// apiFetch function
export const apiFetch = async (url, method = 'GET', body = null) => {
    try {
        console.log('Request URL:', url);
        console.log('Request Method:', method);
        const response = await fetch(url, {
            method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwt}`,
            },
            body: body ? JSON.stringify(body) : null,
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error in apiFetch:', error);
        return null;
    }
};
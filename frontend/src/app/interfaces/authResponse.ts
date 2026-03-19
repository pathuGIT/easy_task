export interface AuthResponse {
    message: string,
    data: {
        active_token: string;
        refresh_token: string;
    };
}
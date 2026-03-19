export interface TaskResponse {
    message: string;
    data: {
        "id": number,
        "title": string,
        "description": string,
        "status": string,
        "createdAt": Date,
    }[];
}
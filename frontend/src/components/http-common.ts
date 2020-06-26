import axios from "axios";

export default axios.create({
    baseURL: "/api/v1.0",
    headers: {
        'Access-Control-Allow-Origin': ['http://localhost:8888', 'http://localhost:8081'],
        'Access-Control-Allow-Methods': 'GET,POST,DELETE,PUT,OPTIONS',
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Credentials': true
    }
});
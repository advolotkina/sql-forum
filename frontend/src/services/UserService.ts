import http from "../components/http-common";

class UserService{
    get(id: string) {
        return http.get(`/users/${id}`);
    }
}

export default new UserService();
import http from "../components/http-common";

class UserService {
  get(id: string) {
    return http.get(`/users/${id}`);
  }
  update(id: number, data: any) {
    return http.patch(`/users/${id}`, data);
  }
}

export default new UserService();

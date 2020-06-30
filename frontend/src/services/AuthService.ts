import http from "../components/http-common";

class AuthService {
  authenticate(data: any) {
    return http.post("/auth/signin", data);
  }

  logout() {
    return http.post("auth/logout");
  }

  register(data: any) {
    return http.post("/auth/signup", data);
  }

}

export default new AuthService();

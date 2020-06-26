import http from "../components/http-common";

class AuthService {
    authenticate(data: any) {
        return http.post("/auth/signin", data);
    }

    logout(){
        return http.post('auth/logout');
    }
    register(data: any) {
        return http.post("/auth/signup", data);
    }
    // get(id: string) {
    //     return http.get(`/tutorials/${id}`);
    // }
    //
    // create(data: any) {
    //     return http.post("/tutorials", data);
    // }
    //
    // update(id: string, data: any) {
    //     return http.put(`/tutorials/${id}`, data);
    // }
    //
    // delete(id: string) {
    //     return http.delete(`/tutorials/${id}`);
    // }
    //
    // deleteAll() {
    //     return http.delete(`/tutorials`);
    // }
    //
    // findByTitle(title: string) {
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new AuthService();
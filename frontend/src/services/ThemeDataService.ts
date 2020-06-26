import http from "../components/http-common";

class ThemeDataService {
    getAll() {
        return http.get("/themes");
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

export default new ThemeDataService();
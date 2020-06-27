import http from "../components/http-common";

class ThemeDataService {
    getAll() {
        return http.get("/themes");
    }
    get(id: string) {
        return http.get(`/themes/${id}`);
    }
    create(data: any){
        return http.post("/themes", data);
    }
    getGroups(){
        return http.get("/groups");
    }
    delete(id: number) {
        return http.delete(`/themes/${id}`);
    }
}

export default new ThemeDataService();
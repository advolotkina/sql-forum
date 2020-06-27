import http from "../components/http-common";

class TopicService{
    get(id: string) {
        console.log(id);
        return http.get(id);
    }

    create(data: any, path: string){
        return http.post(path, data);
    }



}

export default new TopicService();
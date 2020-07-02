import http from "../components/http-common";

class TopicService {
  get(id: string) {
    return http.get(id);
  }

  create(data: any, path: string) {
    return http.post(path, data);
  }

  delete(path: string) {
    return http.delete(path);
  }

  update(path: string, data: any) {
    return http.patch(path, data);
  }
}

export default new TopicService();

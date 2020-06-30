import http from "../components/http-common";

class CommentsService {
  create(data: any, path: string) {
    return http.post(path, data);
  }
}

export default new CommentsService();

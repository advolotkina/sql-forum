import http from "../components/http-common";

class CommentsService {
  create(data: any, path: string) {
    return http.post(path, data);
  }
  update(id: number, data:any){
    return http.patch(`/comments/${id}`, data)
  }
  delete(id: number) {
    return http.delete(`/comments/${id}`);
  }
}

export default new CommentsService();

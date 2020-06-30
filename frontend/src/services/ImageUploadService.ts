import http from "../components/http-common";
const CLOUDINARY_URL = 'https://api.cloudinary.com/v1_1/zhblnd/image/upload';
class ImageUploadService {

    upload(file: any) {
        const formData = new FormData();
        formData.append('file', file);
        return http.post(CLOUDINARY_URL, formData);
    }
}

export default new ImageUploadService();
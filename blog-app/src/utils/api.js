import axios from "axios";

const baseUrl = `http://localhost:8080`
const Api = {
    login: `${baseUrl}/api/public/login`,
    postArticle: `${baseUrl}/api/authorize/post-article`,
    refreshToken: `${baseUrl}/api/public/refresh`,
    getCategories: `${baseUrl}/api/authorize/category/`
}
export default Api
String.prototype.format = function() {
    let formatted = this;
    for (let i = 0; i < arguments.length; i++) {
        let regexp = new RegExp('\\{'+i+'\\}', 'gi');
        formatted = formatted.replace(regexp, arguments[i]);
    }
    return formatted;
};
const baseUrl = `http://localhost:8080`
const Api = {
    login: `${baseUrl}/api/public/login`,
    postArticle: `${baseUrl}/api/authorize/post-article`,
    refreshToken: `${baseUrl}/api/public/refresh`,
    getCategories: `${baseUrl}/api/authorize/category`,
    getImages: `${baseUrl}/api/authorize/image?page={0}`,
    image: `${baseUrl}/image/{0}`,
    deleteImage: `${baseUrl}/api/authorize/image/{0}`,
    uploadImage: `${baseUrl}/api/authorize/image`,
    csrfToken: `${baseUrl}/api/public/csrf`,
}
export default Api
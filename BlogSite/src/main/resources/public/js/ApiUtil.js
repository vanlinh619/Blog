
const ApiUtil = {
    commentUrl: '/api/authorize/comment',
    getAllCommentUrl: (postSlug, page) => `/api/public/comment/${postSlug}?page=${page}`
}

export default ApiUtil
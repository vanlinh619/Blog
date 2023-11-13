
const ApiUtil = {
    socketUrl: 'ws://localhost:8440/comment',
    commentUrl: '/api/authorize/comment',
    getAllCommentUrl: (postSlug, page) => `/api/public/comment/${postSlug}?page=${page}`,
    getAllChildCommentUrl: (postSlug, supperCommentId, page) => `/api/public/comment/${postSlug}/${supperCommentId}?page=${page}`
}

export default ApiUtil
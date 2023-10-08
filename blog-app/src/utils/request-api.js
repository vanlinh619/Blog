import axios from "axios";
import Api from "@/utils/api";
import Key from "@/utils/contain";
import Redirect from "@/utils/redirect";

const reLogin = (error, callback) => {
    if (error?.response?.status === 401) {
        let refresh = localStorage.getItem(Key.token)
        if (!refresh) {
            Redirect.login()
            return false
        }
        axios.post(Api.refreshToken, {
            token: refresh
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem(Key.accessToken)}`
            }
        })
            .then(response => {
                localStorage.setItem(Key.accessToken, response.data[Key.accessToken])
                if(callback) callback()
            })
            .catch(error => {
                console.log(error)
                Redirect.login()
            })
        return false
    }
    return true
}

const RequestApi = {
    executeRequest: (request, callback, success, failed) => {
        request
            .then(response => {
                if(success) success(response)
            })
            .catch(error => {
                if(!reLogin(error, callback)) return
                if(failed) failed(error)
            })
    },
    postRequest: (postUrl, body) => {
        return axios.post(postUrl, body, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem(Key.accessToken)}`
            }
        })
    },
    getRequest: (getUrl) => {
        return axios.get(getUrl, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem(Key.accessToken)}`
            }
        })
    },
    deleteRequest: (getUrl) => {
        return axios.delete(getUrl, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem(Key.accessToken)}`
            }
        })
    },
    hasAuthorize: (error, callback) => {
        return reLogin(error, callback)
    }
}
export default RequestApi
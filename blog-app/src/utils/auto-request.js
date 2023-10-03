import axios from "axios";
import Api from "@/utils/api";
import Key from "@/utils/contain";
import Redirect from "@/utils/redirect";

const AutoRequest = {
    hasAuthorize: (error, callback) => {
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
                    if(callback) {
                        callback()
                    }
                })
                .catch(error => {
                    console.log(error)
                    Redirect.login()
                })
            return false
        }
        return true
    }
}
export default AutoRequest
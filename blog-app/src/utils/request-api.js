import axios from "axios";
import Api from "@/utils/api";
import Key from "@/utils/contain";

const RequestApi = {
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
    }
}
export default RequestApi
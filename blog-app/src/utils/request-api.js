import axios from "axios";
import Api from "@/utils/api";
import Key from "@/utils/contain";

const RequestApi = {
    postRequest: (body) => {
        return axios.post(Api.postArticle, body, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem(Key.accessToken)}`
            }
        })
    }
}
export default RequestApi
import axios from "axios";
import Api from "@/utils/api";
import Key from "@/utils/contain";

const AutoRequest = {
    refreshToken: (callback) => {
        let refresh = localStorage.getItem(Key.token)
        if(!refresh) {
            document.location.href="/login";
            return
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
                callback()
            })
            .catch(error => {
                console.log(error)
                document.location.href="/login";
            })
    }
}
export default AutoRequest
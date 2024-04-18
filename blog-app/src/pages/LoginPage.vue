<template>
  <main class="flex justify-center m-10">
    <div class="w-full max-w-xs">
      <form class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <div class="mb-4">
          <label class="block text-gray-700 text-sm font-bold mb-2" for="username">
            Username
          </label>
          <input v-model="username"
                 class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                 id="username" type="text" placeholder="Username">
        </div>
        <div class="mb-6">
          <label class="block text-gray-700 text-sm font-bold mb-2" for="password">
            Password
          </label>
          <input v-model="password"
                 class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
                 id="password" type="password" placeholder="******************">
          <!--        <p class="text-red-500 text-xs italic">Please choose a password.</p>-->
        </div>
        <div class="flex items-center justify-between">
          <button @click="submit"
                  class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                  type="button">
            Sign In
          </button>
          <router-link class="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800" to="/">
            Forgot Password?
          </router-link>
        </div>
      </form>
    </div>
  </main>
</template>
<script>
import axios from 'axios';
import Api from "../utils/api";
import Key from "../utils/contain";
import RequestApi from "@/utils/request-api";

export default {
  data() {
    return {
      username: 'admin',
      password: 'admin'
    }
  },
  methods: {
    submit() {
      console.log(this.username + ": " + this.password)
      let login = (token) => {
        axios.post( Api.login, {
          username: this.username,
          password: this.password
        }, {
          headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token,
          }
        })
            .then(response => {
              console.log(response)
              this.storeKey(response.data)
              this.$router.push({name: 'home'});
            })
            .catch(error => {
              console.log(error)
            })
      }
      RequestApi.getCsrfToken(login)
    },
    storeKey(data) {
      localStorage.setItem(Key.token, data[Key.token])
      localStorage.setItem(Key.accessToken, data[Key.accessToken])
      localStorage.setItem(Key.uuid, data[Key.uuid])
    }
  },
}
</script>

<style scoped>

</style>
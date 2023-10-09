<script setup>

</script>

<template>
  <div class="font-semibold">
    <div v-if="data" class="flex items-center w-2/3">
      <img :src="data" class="rounded border object-cover h-10 w-10" alt="data"/>
      <span class="ml-3 truncate">{{ name }}</span>
      <button v-if="!uploading" @click="upload"
              class="animate-bounce stroke-slate-700 fill-slate-700 hover:stroke-emerald-600 hover:fill-emerald-700">
        <svg xmlns="http://www.w3.org/2000/svg" focusable="false" viewBox="0 0 24 24"
             class="ml-4 w-6 h-6 min-w-[1rem] min-h-[1rem]">
          <path fill-rule="evenodd" clip-rule="evenodd" stroke-width="0"
                d="M8 10C8 7.79086 9.79086 6 12 6C14.2091 6 16 7.79086 16 10V11H17C18.933 11 20.5 12.567 20.5 14.5C20.5 16.433 18.933 18 17 18H16C15.4477 18 15 18.4477 15 19C15 19.5523 15.4477 20 16 20H17C20.0376 20 22.5 17.5376 22.5 14.5C22.5 11.7793 20.5245 9.51997 17.9296 9.07824C17.4862 6.20213 15.0003 4 12 4C8.99974 4 6.51381 6.20213 6.07036 9.07824C3.47551 9.51997 1.5 11.7793 1.5 14.5C1.5 17.5376 3.96243 20 7 20H8C8.55228 20 9 19.5523 9 19C9 18.4477 8.55228 18 8 18H7C5.067 18 3.5 16.433 3.5 14.5C3.5 12.567 5.067 11 7 11H8V10ZM15.7071 13.2929L12.7071 10.2929C12.3166 9.90237 11.6834 9.90237 11.2929 10.2929L8.29289 13.2929C7.90237 13.6834 7.90237 14.3166 8.29289 14.7071C8.68342 15.0976 9.31658 15.0976 9.70711 14.7071L11 13.4142V19C11 19.5523 11.4477 20 12 20C12.5523 20 13 19.5523 13 19V13.4142L14.2929 14.7071C14.6834 15.0976 15.3166 15.0976 15.7071 14.7071C16.0976 14.3166 16.0976 13.6834 15.7071 13.2929Z"></path>
        </svg>
      </button>
      <button v-if="!uploading" @click="deleteImage"
              class="stroke-slate-700 hover:stroke-emerald-600">
        <svg xmlns="http://www.w3.org/2000/svg" focusable="false"
             fill="none" viewBox="0 0 24 24"
             class="ml-4 w-6 h-6 min-w-[1rem] min-h-[1rem]">
          <path
              d="M6 7V18C6 19.1046 6.89543 20 8 20H16C17.1046 20 18 19.1046 18 18V7M6 7H5M6 7H8M18 7H19M18 7H16M10 11V16M14 11V16M8 7V5C8 3.89543 8.89543 3 10 3H14C15.1046 3 16 3.89543 16 5V7M8 7H16"
              stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
        </svg>
      </button>
      <button v-if="uploading" @click="deleteImage" class="stroke-slate-700 fill-slate-700">
        <svg xmlns="http://www.w3.org/2000/svg" focusable="false" viewBox="0 0 24 24"
             class="absolute ml-4 w-6 h-6 min-w-[1rem] min-h-[1rem]">
          <path d="M8 1.5a6.5 6.5 0 100 13 6.5 6.5 0 000-13zM0 8a8 8 0 1116 0A8 8 0 010 8z" opacity=".2"></path>
        </svg>
        <svg xmlns="http://www.w3.org/2000/svg" focusable="false" viewBox="0 0 24 24"
             class="ml-4 w-6 h-6 min-w-[1rem] min-h-[1rem] stroke-emerald-600">
          <path d="M7.25.75A.75.75 0 018 0a8 8 0 018 8 .75.75 0 01-1.5 0A6.5 6.5 0 008 1.5a.75.75 0 01-.75-.75z"></path>
        </svg>
      </button>
    </div>

    <label v-else for="file" class="flex items-center stroke-slate-700 hover:text-emerald-600 hover:stroke-emerald-600">
      <svg xmlns="http://www.w3.org/2000/svg" focusable="false"
           fill="none" viewBox="0 0 24 24" class="w-6 h-6 min-w-[1rem] min-h-[1rem]">
        <path stroke-width="2" stroke-linecap="round"
              d="M17 9.00195C19.175 9.01406 20.3529 9.11051 21.1213 9.8789C22 10.7576 22 12.1718 22 15.0002V16.0002C22 18.8286 22 20.2429 21.1213 21.1215C20.2426 22.0002 18.8284 22.0002 16 22.0002H8C5.17157 22.0002 3.75736 22.0002 2.87868 21.1215C2 20.2429 2 18.8286 2 16.0002L2 15.0002C2 12.1718 2 10.7576 2.87868 9.87889C3.64706 9.11051 4.82497 9.01406 7 9.00195"></path>
        <path d="M12 15L12 2M12 2L15 5.5M12 2L9 5.5" stroke-width="2" stroke-linecap="round"
              stroke-linejoin="round"></path>
      </svg>
      <span class="ml-3">Tải ảnh lên</span>
      <input type="file" id="file" accept="image/png, image/jpeg, image/jpg, image/svg" @change="loadImage" hidden>
    </label>
  </div>
</template>
<script>
import * as url from "url";
import RequestApi from "@/utils/request-api";
import AlertType from "@/utils/alert-type";

export default {
  props: {
    urlUpload: String,
    modelValue: Object
  },
  emits: ['update:modelValue'],
  data() {
    return {
      data: null,
      name: '',
      image: null,
      uploading: false
    }
  },
  methods: {
    loadImage(e) {
      this.image = e.target.files[0];
      this.name = this.image.name
      const reader = new FileReader();
      reader.onload = e => {
        this.data = e.target.result;
      };
      reader.readAsDataURL(this.image);
    },
    deleteImage() {
      this.data = null
    },
    upload() {
      this.uploading = true
      let form = new FormData()
      form.append('image', this.image)
      RequestApi.executeRequest(
          RequestApi.postFormRequest(this.urlUpload, form),
          this.upload,
          (response) => {
            this.uploading = false
            this.data = null
            this.$emit('update:modelValue', response?.data)
          },
          (error) => {
            this.uploading = false
            this.data = null
            this.$emit('update:modelValue', error?.response?.data)
          }
      )
    }
  }
}
</script>
<style scoped>

</style>
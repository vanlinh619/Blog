<script setup>
import Header from "@/design/Header.vue";
import CardImage from "@/components/image/CardImage.vue";
import ModelConfirm from "@/components/ModelConfirm.vue";
import Alert from "@/components/AlertComponent.vue";
import ImageUpload from "@/components/image/ImageUpload.vue";
</script>

<template>
  <Header/>
  <Alert :response="response"/>
  <ModelConfirm :modal="modal"/>
  <main class="mt-10">
    <article class="pl-[19.5rem]">
      <div class="mx-auto max-w-none ml-0 mr-[15.5rem] pr-16 text-slate-700">
        <div class="m-2 pb-2 pt-5">
          <ImageUpload v-model="uploadResponse" :url-upload="urlUpload"></ImageUpload>
        </div>
        <div class="grid grid-cols-2 mb-10">
          <div v-for="image in images">
            <CardImage @click="data => deleteImage(data)" :id="image.id" :src="Api.image.format(image.id)"
                       :name="image.name" :date="image.date"></CardImage>
          </div>
        </div>
        <div class="flex justify-center mb-10">
          <button v-if="page < totalPage - 1" @click="page = page + 1"
                  class="bg-emerald-500 hover:bg-emerald-600 text-white font-bold py-2 px-3 rounded">
            Xem thêm
          </button>
        </div>
      </div>
    </article>
  </main>
</template>

<script>
import RequestApi from "@/utils/request-api";
import Api from "@/utils/api";
import AlertType from "@/utils/alert-type";

export default {
  data() {
    return {
      modal: {},
      response: {},
      images: [],
      page: 0,
      totalPage: 0,
      uploadResponse: null,
      urlUpload: '',
    }
  },
  methods: {
    getImages() {
      RequestApi.executeRequest(
          RequestApi.getRequest(Api.getImages.format(this.page)),
          this.getImages,
          (response) => {
            this.images.push(...response?.data?.data?.content)
            this.page = response?.data?.data?.page
            this.totalPage = response?.data?.data?.totalPage
            console.log(response)
          },
          (error) => {
          }
      )
    },
    deleteRequest(id) {
      RequestApi.executeRequest(
          RequestApi.deleteRequest(Api.deleteImage.format(id)),
          () => {
            this.deleteRequest(id)
          },
          (response) => {
            this.response = response?.data
            this.modal = {
              show: false
            }
            let ind = 0;
            if (this.images.find((value, index) => {
              if (value.id === id) {
                ind = index
                return value
              }
            })) {
              this.images.splice(ind, 1)
            }
            console.log(response)
          },
          (error) => {
            this.response = error?.response
            this.modal = {
              show: false
            }
            console.log(error)
          }
      )
    },
    deleteImage({id, name}) {
      this.modal = {
        show: true,
        id: id,
        title: 'Xóa hình ảnh này',
        message: name,
        callback: (id) => {
          this.deleteRequest(id)
        }
      }
    }
  },
  mounted() {
    this.getImages()
    this.urlUpload = Api.uploadImage
  },
  watch: {
    page(newData) {
      this.getImages()
    },
    uploadResponse(response) {
      if (response.code === 'SUCCESS') {
        this.images.unshift(response?.data)
      }
      this.response = response
      console.log('newData: ', response)
    }
  }
}
</script>

<style scoped>

</style>
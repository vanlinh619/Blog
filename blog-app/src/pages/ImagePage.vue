<script setup>
import Header from "@/design/Header.vue";
import CardImage from "@/components/CardImage.vue";
import ModelConfirm from "@/components/ModelConfirm.vue";
import Alert from "@/components/AlertComponent.vue";
</script>

<template>
  <Header/>
  <Alert :message="message"/>
  <ModelConfirm :modal="modal"/>
  <main class="mt-10">
    <article class="pl-[19.5rem]">
      <div class="mx-auto max-w-none ml-0 mr-[15.5rem] pr-16 text-slate-700">
        <div class="m-2 pb-2 pt-5 font-semibold stroke-slate-700 hover:text-emerald-600 hover:stroke-emerald-600">
          <label for="file" class="flex items-center">
            <svg xmlns="http://www.w3.org/2000/svg" focusable="false"
                 fill="none" viewBox="0 0 24 24" class="w-6 h-6 min-w-[1rem] min-h-[1rem]">
              <path stroke-width="2" stroke-linecap="round"
                    d="M17 9.00195C19.175 9.01406 20.3529 9.11051 21.1213 9.8789C22 10.7576 22 12.1718 22 15.0002V16.0002C22 18.8286 22 20.2429 21.1213 21.1215C20.2426 22.0002 18.8284 22.0002 16 22.0002H8C5.17157 22.0002 3.75736 22.0002 2.87868 21.1215C2 20.2429 2 18.8286 2 16.0002L2 15.0002C2 12.1718 2 10.7576 2.87868 9.87889C3.64706 9.11051 4.82497 9.01406 7 9.00195"></path>
              <path d="M12 15L12 2M12 2L15 5.5M12 2L9 5.5" stroke-width="2" stroke-linecap="round"
                    stroke-linejoin="round"></path>
            </svg>
            <span class="ml-3">Tải ảnh lên</span>
          </label>
          <input type="file" id="file" hidden>
        </div>
        <div class="grid grid-cols-2 mb-10">
          <div v-for="image in images">
            <CardImage @click="data => deleteImage(data)" :id="image.id" :src="Api.image.format(image.id)"
                       :name="image.name" :used="image.used"
                       :date="image.date"></CardImage>
          </div>
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
      message: {},
      images: [],
    }
  },
  methods: {
    getImages() {
      RequestApi.executeRequest(
          RequestApi.getRequest(Api.getImages.format(0)),
          this.getImages,
          (response) => {
            this.images.push(...response?.data?.content)
            console.log(response)
          },
          (error) => {
            console.log('heo')
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
            this.message = {
              role: AlertType.SUCCESS,
              code: response?.data?.code,
              description: response?.data?.message
            }
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
            this.message = {
              code: error?.response?.data?.code,
              description: error?.response?.data?.message,
              role: AlertType.ERROR
            }
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
        title: name,
        message: 'Xóa hình ảnh này',
        callback: (id) => {
          this.deleteRequest(id)
        }
      }
    }
  },
  mounted() {
    this.getImages()
  }
}
</script>

<style scoped>

</style>
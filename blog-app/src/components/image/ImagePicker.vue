<script setup>

import ImageItem from "@/components/image/ImageItem.vue";
</script>

<template>
  <nav class="text-cyan-950">
    <template v-for="image in images">
      <div class="mt-1">
        <ImageItem :id="image.id" :src="Api.image.format(image.id)" :name="image.name"/>
      </div>
    </template>
    <button @click="page = page + 1" v-if="page < totalPage - 1"
            class="text-sm mt-2 hover:text-emerald-600 border-b">Tải thêm
    </button>
  </nav>
</template>
<script>
import RequestApi from "@/utils/request-api";
import Api from "@/utils/api";

export default {
  data() {
    return {
      images: [],
      page: 0,
      totalPage: 0,
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
  },
  mounted() {
    this.getImages()
  },
  watch: {
    page() {
      this.getImages()
    },
  }
}
</script>
<style scoped>

</style>
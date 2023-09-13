<script setup>
import EditorComponent from "../components/EditorComponent.vue";
import NavigationComponent from "../components/NavigationComponent.vue";
import InputView from "../components/InputView.vue";
</script>

<template>
  <navigation-component></navigation-component>
  <main class="mt-5 mx-52">
    <div class="grid grid-cols-2 gap-2">
      <InputView label="Title" v-model="title" class="mt-3"></InputView>
      <InputView label="Slug" v-model="slug" class="mt-3"></InputView>
    </div>
    <InputView label="Meta Title" v-model="metaTitle" class="mt-3"></InputView>
    <editor-component v-model="content" class="mt-10 mb-10"></editor-component>
    <button @click="save">Save</button>
  </main>
</template>
<script>
import axios from "axios";
import Api from "@/utils/api";
import Key from "@/utils/contain";

export default {
  data() {
    return {
      title: '',
      slug: '',
      metaTitle: '',
      content: '',
      autoSaveWord: null
    }
  },
  methods: {
    save() {
      axios.post( Api.postArticle, {
        title: this.title,
        slug: this.slug,
        metaTitle: this.metaTitle,
        content: this.content,
        author: localStorage.getItem(Key.uuid)
      }, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem(Key.accessToken)}`
        }
      })
          .then(response => {
            console.log(response)
          })
          .catch(error => {
            console.log(error)
          })
    },
  },
  watch: {
    content(newData, oldData) {
      console.log(newData)
    },
    title(newData, oldData) {
      this.slug = newData
          .normalize('NFKD') // split accented characters into their base characters and diacritical marks
          .replace(/[\u0300-\u036f]/g, '') // remove all the accents, which happen to be all in the \u03xx UNICODE block.
          .trim() // trim leading or trailing whitespace
          .toLowerCase() // convert to lowercase
          .replace(/[^a-z0-9 -]/g, '') // remove non-alphanumeric characters
          .replace(/\s+/g, '-') // replace spaces with hyphens
          .replace(/-+/g, '-')
    }
  },
}
</script>

<style scoped>

</style>
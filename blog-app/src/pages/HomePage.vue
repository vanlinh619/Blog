<script setup>
import EditorComponent from "../components/EditorComponent.vue";
import NavigationComponent from "../components/NavigationComponent.vue";
import InputView from "../components/InputView.vue";
import Header from "@/design/Header.vue";
import Index from "@/design/Index.vue";
import Aside from "@/design/Aside.vue";
import Detail from "@/design/Detail.vue";
import Footer from "@/design/Footer.vue";
</script>

<template>
  <!--  <navigation-component></navigation-component>-->
  <!--  <main class="mt-5 mx-52">-->

  <!--  </main>-->
  <Header></Header>
  <div class="max-w-8xl mx-auto px-4">
    <Aside></Aside>
    <main class="mt-10">
      <article class="pl-[19.5rem]">
        <div class="mx-auto max-w-none ml-0 mr-[15.5rem] pr-16 text-slate-700">
          <button @click="preview=!preview">Preview</button>
          <div class="
            [&_h2]:text-3xl [&_h2]:mt-5 [&_h3]:text-[1.65rem] [&_h3]:mt-5 [&_h4]:text-[1.4rem] [&_h4]:mt-5
            [&_a]:text-emerald-600 [&_a:hover]:text-emerald-800
            [&_ul]:list-disc [&_ul]:ml-[1.1rem] [&>ul]:mt-4 [&>ul>li>ul]:ml-3
            [&_ol]:list-decimal [&_ol]:ml-[1.1rem] [&>ol]:mt-4 [&>ol>li>ol]:ml-3 [&>ol>li>ol]:list-disc
            [&_li]:mt-3
            [&_code]:bg-slate-100 [&_code]:rounded [&_code]:p-[0.15rem]
            [&_blockquote]:bg-slate-100 [&_blockquote]:rounded [&_blockquote]:border [&_blockquote]:border-emerald-300
            [&_blockquote]:p-4 [&_blockquote]:mt-4 [&_blockquote_p]:mt-3
            [&_blockquote_:first-child]:mt-0
            [&_p]:mt-4">
            <div v-if="preview" v-html="content"></div>
            <div id="editor" v-else>
              <div class="grid grid-cols-2 gap-2">
                <InputView label="Title" v-model="title" class="mt-3"></InputView>
                <InputView label="Slug" v-model="slug" class="mt-3"></InputView>
              </div>
              <InputView label="Meta Title" v-model="metaTitle" class="mt-3"></InputView>
              <editor-component v-model="content" class="mt-10 mb-10"></editor-component>
              <button @click="save">Save</button>
            </div>

          </div>

        </div>
      </article>
      <Index :list-index="listIndex"></Index>
    </main>
  </div>
<!--  <Footer></Footer>-->
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
      listIndex: [],
      preview: false,
      autoSaveWord: null
    }
  },
  methods: {
    save() {
      axios.post(Api.postArticle, {
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
    content(newData) {
      let hTags = [...document.getElementById('editor')?.querySelectorAll('h2, h3, h4')]

      this.listIndex.splice(0, this.listIndex.length)
      hTags?.forEach((tag, index) => {
        this.listIndex.push({
          tag: tag.nodeName.toLowerCase(),
          content: tag.outerText,
          id: toSlug(tag.outerText) + `_${index + 1}`
        })
      })
      console.log('home: ', this.listIndex)
    },
    title(newData) {
      this.slug = toSlug(newData)
    },
  },
}

let toSlug = (data) => {
  return data.normalize('NFKD') // split accented characters into their base characters and diacritical marks
      .replace(/[\u0300-\u036f]/g, '') // remove all the accents, which happen to be all in the \u03xx UNICODE block.
      .trim() // trim leading or trailing whitespace
      .toLowerCase() // convert to lowercase
      .replace(/[^a-z0-9 -]/g, '') // remove non-alphanumeric characters
      .replace(/\s+/g, '-') // replace spaces with hyphens
      .replace(/-+/g, '-')
}
</script>

<style scoped>

</style>
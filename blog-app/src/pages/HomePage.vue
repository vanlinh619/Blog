<script setup>
import EditorComponent from "../components/home/EditorComponent.vue";
import InputView from "../components/InputView.vue";
import Header from "@/design/Header.vue";
import Index from "@/design/Index.vue";
import Detail from "@/design/Detail.vue";
import CategoryPicker from "@/components/home/CategoryPicker.vue";
import ImagePicker from "@/components/image/ImagePicker.vue";
import AlertComponent from "@/components/AlertComponent.vue";
import SectionItem from "@/components/SectionItem.vue";
</script>

<template>
  <Header></Header>
  <AlertComponent :response="response"/>
  <div class="max-w-8xl mx-auto px-4">
    <div
        class="lg:block fixed z-20 inset-0 top-16 left-[max(0px,calc(50%-45rem))] right-auto w-[19rem] pb-10 pl-8 pr-6 overflow-y-auto">
      <SectionItem title="Bộ sưu tập" url="/category" expand="true">
        <CategoryPicker v-model="categories"></CategoryPicker>
      </SectionItem>

      <SectionItem title="Ảnh" url="/image">
        <ImagePicker></ImagePicker>
      </SectionItem>
    </div>
    <main class="mt-10">
      <article class="pl-[19.5rem]">
        <div class="mx-auto max-w-none ml-0 mr-[15.5rem] pr-16 text-slate-700">
          <div class="
            [&_h2]:text-3xl [&_h2]:mt-10 [&_h2]:mb-3 [&_h2]:py-3 [&_h2]:scroll-mt-20 [&_h2]:font-semibold
            [&_h3]:text-[1.65rem] [&_h3]:mt-8 [&_h3]:mb-2 [&_h3]:py-3 [&_h3]:scroll-mt-20 [&_h3]:font-semibold
            [&_h4]:text-[1.4rem] [&_h4]:mt-5 [&_h4]:mb-2 [&_h4]:py-3 [&_h4]:scroll-mt-20 [&_h4]:font-semibold
            [&_a]:text-emerald-600 [&_a:hover]:text-emerald-800
            [&_ul]:list-disc [&_ul]:ml-[1.1rem] [&>ul]:mt-4 [&>ul>li>ul]:ml-3
            [&_ol]:list-decimal [&_ol]:ml-[1.1rem] [&>ol]:mt-4 [&>ol>li>ol]:ml-3 [&>ol>li>ol]:list-disc
            [&_li]:mt-3
            [&_code]:bg-slate-100 [&_code]:rounded [&_code]:p-[0.15rem]
            [&_blockquote]:bg-slate-100 [&_blockquote]:rounded [&_blockquote]:border [&_blockquote]:border-emerald-300
            [&_blockquote]:p-4 [&_blockquote]:mt-4 [&_blockquote_p]:mt-3
            [&_blockquote_:first-child]:mt-0
            [&_pre]:rounded-lg [&_pre]:border [&_pre]:px-5 [&_pre]:py-2 [&_pre]:bg-slate-800 [&_pre]:text-slate-200
            [&_pre]:mt-4 [&_pre]:overflow-x-auto [&_pre_code]:bg-slate-800 [&_pre_code]:p-0
            [&_figure]:grid [&_figure]:grid-cols-1 [&_figure]:justify-items-center [&_figure]:mt-5
            [&_figure_figcaption]:text-sm [&_figure_figcaption]:italic [&_figure_figcaption]:mt-2
            [&_th]:border [&_th]:p-2 [&_th]:bg-slate-100
            [&_td]:border [&_td]:p-2
            [&_p]:mt-4">
            <Detail v-if="preview" :content="content" :list-index="listIndex"></Detail>
            <div id="editor" v-else>
              <div class="grid grid-cols-2 gap-2">
                <InputView label="Title" v-model="title" class="mt-3"></InputView>
                <InputView label="Slug" v-model="slug" class="mt-3"></InputView>
              </div>
              <InputView label="Meta Title" v-model="metaTitle" class="mt-3"></InputView>
              <div>
                <label class="block text-gray-700 text-sm font-bold mb-2 mt-3" for="introduction">Introduction
                  {{ introduction.trim().length }}/1000</label>
                <textarea v-model="introduction" id="introduction" placeholder="Introduction" type="text"
                          class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"></textarea>
              </div>

              <editor-component v-model="content" class="mt-10 mb-10"></editor-component>
            </div>

          </div>

        </div>
      </article>
      <div class="fixed z-20 top-16 bottom-0 right-[max(0px,calc(50%-45rem))] w-[19.5rem] py-10 overflow-y-auto block">
        <button class="bg-emerald-500 hover:bg-emerald-600 text-white font-bold py-2 px-3 rounded"
                @click="preview=!preview">
          Preview
        </button>
        <button class="ml-2 bg-emerald-500 hover:bg-emerald-600 text-white font-bold py-2 px-3 rounded" @click="save">
          Save
        </button>
        <div class="mt-5">
          <Index :list-index="listIndex"></Index>
        </div>
      </div>
    </main>
  </div>
  <!--  <Footer></Footer>-->
</template>
<script>
import Api from "@/utils/api";
import Key from "@/utils/contain";
import AlertType from "@/utils/alert-type";
import RequestApi from "@/utils/request-api";

export default {
  data() {
    return {
      title: '',
      slug: '',
      metaTitle: '',
      introduction: '',
      content: '',
      categories: [],
      listIndex: [],
      preview: false,

      // alert
      response: {},
    }
  },
  methods: {
    save() {
      console.log('cate: ', this.categories)
      RequestApi.postRequest(Api.postArticle, {
        title: this.title,
        slug: this.slug,
        metaTitle: this.metaTitle,
        introduction: this.introduction,
        content: this.content,
        tags: [],
        categories: this.categories,
        author: localStorage.getItem(Key.uuid)
      })
          .then(response => {
            this.response = response.data
            console.log(response)
          })
          .catch(error => {
            if (!RequestApi.hasAuthorize(error, this.save)) return
            this.response = error?.response.data
            console.log(error)
          })
    },
  },
  watch: {
    content() {
      let hTags = [...document.getElementById('editor')?.querySelectorAll('h2, h3, h4')]

      this.listIndex.splice(0, this.listIndex.length)
      hTags?.forEach((tag, index) => {
        if (tag.outerText.trim()) {
          this.listIndex.push({
            tag: tag.nodeName.toLowerCase(),
            content: tag.outerText,
            id: toSlug(tag.outerText) + `-${index + 1}`
          })
        }
      })
      console.log('home: ', this.listIndex)
    },
    title(newData) {
      this.slug = toSlug(newData)
    },
  },
}

let toSlug = (data) => {
  return data.normalize('NFD') // split accented characters into their base characters and diacritical marks
      .replace(/[\u0300-\u036f]/g, '') // remove all the accents, which happen to be all in the \u03xx UNICODE block.
      .trim() // trim leading or trailing whitespace
      .toLowerCase() // convert to lowercase
      .replace(/đ+/g, 'd')
      .replace(/[^a-z0-9 -]/g, '') // remove non-alphanumeric characters
      .replace(/\s+/g, '-') // replace spaces with hyphens
      .replace(/^[^a-z]+/g, '')
}
</script>

<style scoped>

</style>
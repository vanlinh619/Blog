<script setup>
import Header from "@/design/Header.vue";
import Nav from "@/design/Nav.vue";
import Aside from "@/design/Aside.vue";
import Detail from "@/design/Detail.vue";
import Index from "@/design/Index.vue";
import Footer from "@/design/Footer.vue";
import Comment from "@/design/Comment.vue";
</script>

<template>
  <Header></Header>
<!--  <div class="max-w-8xl mx-auto px-4">-->
    <div
        class="lg:block fixed z-20 inset-0 top-16 left-[max(0px,calc(50%-45rem))] right-auto w-[19rem] pb-10 pl-8 pr-6 overflow-y-auto">
      <Aside></Aside>
    </div>
    <main class="mt-10">
      <article class="pl-[19.5rem]">
        <div class="mx-auto max-w-none ml-0 mr-[15.5rem] pr-16 text-slate-700">
          <!--          <Detail></Detail>-->
          <Comment></Comment>
        </div>
      </article>
      <!--      <Index></Index>-->
    </main>


<!--  </div>-->
<!--  <Footer></Footer>-->
</template>

<style scoped>

</style>
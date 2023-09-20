<script setup>
import Header from "@/design/Header.vue";
import Nav from "@/design/Nav.vue";
import Aside from "@/design/Aside.vue";
import Detail from "@/design/Detail.vue";
import Index from "@/design/Index.vue";
import Footer from "@/design/Footer.vue";
</script>

<template>
  <Header></Header>
  <div class="max-w-8xl mx-auto px-4">
    <Aside></Aside>
    <main class="mt-10">
      <article class="pl-[19.5rem]">
        <div class="mx-auto max-w-none ml-0 mr-[15.5rem] pr-16 text-slate-700">
          <Detail></Detail>
        </div>
      </article>
      <Index></Index>
    </main>
  </div>
  <Footer></Footer>
</template>

<style scoped>

</style>
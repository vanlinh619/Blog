<script setup>
import Header from "@/design/Header.vue";
import AlertComponent from "@/components/AlertComponent.vue";
</script>

<template>
  <Header/>
  <AlertComponent v-if="alertType !== AlertType.HIDE" v-model="alertType" :code="alert.code"
                  :description="alert.description"/>
  <main class="mt-10">
    <article class="pl-[19.5rem]">
      <div class="mx-auto max-w-none ml-0 mr-[15.5rem] pr-16 text-slate-700">

      </div>
    </article>
  </main>
</template>

<script>
import AlertType from "@/utils/alert-type";

export default {
  data() {
    return {

      // alert
      alert: {
        code: '',
        description: '',
      },
      alertType: AlertType.HIDE
    }
  }
}
</script>

<style scoped>

</style>
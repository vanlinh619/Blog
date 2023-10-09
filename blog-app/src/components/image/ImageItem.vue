<script setup>

import IconCopy from "@/components/icons/IconCopy.vue";
import IconComplete from "@/components/icons/IconComplete.vue";
</script>

<template>
  <div class="rounded border">
    <div class="flex items-center">
      <img :src="src" alt="image" class="flex-none w-8 h-8 rounded-l">
      <span class="grow ml-2 truncate">{{ name }}</span>
      <button @click="copyUrl" class="flex-none pr-2 stroke-slate-700 fill-slate-700 hover:stroke-emerald-600 hover:fill-emerald-600">
        <IconCopy v-if="!copied"/>
        <IconComplete v-else/>
      </button>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    id: String,
    src: String,
    name: String,
  },
  data() {
    return {
      copied: false
    }
  },
  methods: {
    copyUrl() {
      this.copied = true
      navigator.clipboard.writeText(this.src)
    },
  },
}
</script>
<style scoped>

</style>
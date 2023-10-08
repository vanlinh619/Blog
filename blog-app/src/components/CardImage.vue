<script setup>

</script>

<template>
  <div class="m-2 p-2 rounded-lg shadow-md border">
    <img :src="src" class="rounded border object-cover h-48 w-full">
    <div class="grid grid-cols-2 mt-2">
      <label class="truncate text-xs">{{ dateFormat(date) }}</label>
      <label class="truncate text-xs text-right">Sử dụng: {{ used }}</label>
    </div>
    <label class="truncate text-xs font-semibold">{{ name }}</label>
    <div class="flex justify-between mt-2">
      <button v-if="used === 0" @click="$emit('click', {id, name})"
              class="order-first font-semibold stroke-slate-700 fill-slate-700 hover:text-emerald-600 hover:stroke-emerald-600 hover:fill-emerald-600">
        <svg xmlns="http://www.w3.org/2000/svg" focusable="false" viewBox="0 0 24 24" fill="none"
             class="w-4 h-4 min-w-[1rem] min-h-[1rem]">
          <path
              d="M12 19H5C3.89543 19 3 18.1046 3 17V7C3 5.89543 3.89543 5 5 5H9.58579C9.851 5 10.1054 5.10536 10.2929 5.29289L12 7H19C20.1046 7 21 7.89543 21 9V11"
              stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
          <path d="M16 15L18.5 17.5M21 20L18.5 17.5M18.5 17.5L21 15M18.5 17.5L16 20"
                stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
        </svg>
      </button>
      <button @click="copyUrl(src)"
              class="order-last font-semibold stroke-slate-700 fill-slate-700 hover:text-emerald-600 hover:stroke-emerald-600 hover:fill-emerald-600">
        <svg v-if="!copied" xmlns="http://www.w3.org/2000/svg" focusable="false" viewBox="0 0 24 24"
             class="w-4 h-4 min-w-[1rem] min-h-[1rem]">
          <path fill-rule="evenodd" stroke-width="0.1"
                d="M21 8C21 6.34315 19.6569 5 18 5H10C8.34315 5 7 6.34315 7 8V20C7 21.6569 8.34315 23 10 23H18C19.6569 23 21 21.6569 21 20V8ZM19 8C19 7.44772 18.5523 7 18 7H10C9.44772 7 9 7.44772 9 8V20C9 20.5523 9.44772 21 10 21H18C18.5523 21 19 20.5523 19 20V8Z"></path>
          <path fill-rule="evenodd" stroke-width="0.1"
                d="M6 3H16C16.5523 3 17 2.55228 17 2C17 1.44772 16.5523 1 16 1H6C4.34315 1 3 2.34315 3 4V18C3 18.5523 3.44772 19 4 19C4.55228 19 5 18.5523 5 18V4C5 3.44772 5.44772 3 6 3Z"></path>
        </svg>
        <svg v-else xmlns="http://www.w3.org/2000/svg" focusable="false" viewBox="0 0 24 24"
             class="w-4 h-4 min-w-[1rem] min-h-[1rem]">
            <path fill-rule="evenodd"
                  d="M20.6097 5.20743C21.0475 5.54416 21.1294 6.17201 20.7926 6.60976L10.7926 19.6098C10.6172 19.8378 10.352 19.9793 10.0648 19.9979C9.77765 20.0166 9.49637 19.9106 9.29289 19.7072L4.29289 14.7072C3.90237 14.3166 3.90237 13.6835 4.29289 13.2929C4.68342 12.9024 5.31658 12.9024 5.70711 13.2929L9.90178 17.4876L19.2074 5.39034C19.5441 4.95258 20.172 4.87069 20.6097 5.20743Z"></path>
        </svg>
      </button>
    </div>
  </div>
</template>
<script>
import Api from "@/utils/api";

export default {
  props: {
    id: String,
    src: String,
    name: String,
    used: Number,
    date: String
  },
  emits: ['click'],
  data() {
    return {
      copied: false
    }
  },
  methods: {
    dateFormat(value) {
      let options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};
      let date = new Date(value);
      return date.toLocaleString("vi-VN", options)
    },
    copyUrl(src) {
      this.copied = true
      navigator.clipboard.writeText(src)
    },
  }
}
</script>

<style scoped>

</style>
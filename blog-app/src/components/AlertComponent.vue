<script setup>
</script>

<template>
  <div class="sticky top-20 z-50 flex justify-center">
    <div class="w-2/4">
      <div :class="containAlert" class="border px-4 py-3 rounded relative" role="alert">
        <div class="mr-5">
          <strong class="font-bold">{{ code }}: </strong>
          <span>{{ description }}</span>
        </div>
        <button class="absolute top-0 bottom-0 right-0 px-4 py-3" @click="$emit('update:modelValue', 0)">
          <svg class="fill-current h-6 w-6" xmlns="http://www.w3.org/2000/svg"
               viewBox="0 0 20 20"><title>Close</title>
            <path
                d="M14.348 14.849a1.2 1.2 0 0 1-1.697 0L10 11.819l-2.651 3.029a1.2 1.2 0 1 1-1.697-1.697l2.758-3.15-2.759-3.152a1.2 1.2 0 1 1 1.697-1.697L10 8.183l2.651-3.031a1.2 1.2 0 1 1 1.697 1.697l-2.758 3.152 2.758 3.15a1.2 1.2 0 0 1 0 1.698z"/>
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import AlertType from "@/utils/alert-type";

export default {
  props: {
    code: String,
    description: String,
    modelValue: Number
  },
  emits: ['update:modelValue'],
  computed: {
    containAlert() {
      switch (this.modelValue) {
        case AlertType.ERROR: {
          return 'bg-red-100 border-red-400 text-red-700'
        }
        case AlertType.SUCCESS: {
          return 'bg-emerald-100 border-emerald-400 text-emerald-700'
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
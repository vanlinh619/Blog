<script setup>
</script>
<template>
  <div>
    <label v-if="label" class="block text-gray-700 text-sm font-bold mb-2" for="username">{{ label }}</label>
    <input
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
        class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        id="username" type="text" :placeholder="label">
  </div>
</template>
<script>
export default {
  props: {
    label: '',
    modelValue: ''
  },
  emits: ['update:modelValue']
}
</script>
<style scoped>

</style>
<script setup>
</script>

<template>
  <div>
    <span class="text-sm font-semibold px-8 text-cyan-950">NỘI DUNG</span>
    <div class="px-8">
      <ul class="space-y-0.5 mt-3 text-cyan-950">
        <li v-for="index in list" class="block hover:text-cyan-950 hover:[&_a]:font-medium">
            <a v-if="index.tag === 'h2'" class="text-sm font-normal"
               :href="'#'+index.id">{{ index.content }}</a>
          <ul v-else-if="index.tag === 'h3'" class="ml-4">
            <li class="block">
                <a class="text-sm font-normal" :href="'#'+index.id">{{ index.content }}</a>
            </li>
          </ul>
          <ul v-else class="ml-8">
            <li class="block">
                <a class="text-sm font-normal" :href="'#'+index.id">{{ index.content }}</a>
            </li>
          </ul>
        </li>

      </ul>

    </div>
  </div>
</template>
<script>
export default {
  props: {
    listIndex: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      list: this.listIndex
    }
  }
}
</script>
<style scoped>

</style>
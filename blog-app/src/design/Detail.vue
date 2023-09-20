<script setup>

</script>

<template>
<div v-html="content"></div>
</template>
<script>
export default {
  props: {
    listIndex: {
      type: Array,
      default: () => []
    },
    content: String
  },
  data() {
    return {
      list: this.listIndex
    }
  },
  mounted() {
    this.$el.querySelectorAll('h2, h3, h4').forEach((node, index) => {
      node.setAttribute('id', this.list[index]?.id)
    })
  }
}
</script>

<style scoped>

</style>
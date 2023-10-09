<script setup>

import IconRight from "@/components/icons/IconRight.vue";
import IconDown from "@/components/icons/IconDown.vue";
import IconGo from "@/components/icons/IconLink.vue";
</script>

<template>
  <div>
    <div class="mt-5 pb-2 border-b mb-2">
      <div class="grid grid-cols-5 items-center">
        <button @click="expand"
           class="col-span-4 flex items-center hover:text-emerald-600 stroke-slate-700 fill-slate-700 hover:stroke-emerald-600 hover:fill-emerald-600">
          <label>
            <IconDown v-if="expanded"/>
            <IconRight v-else/>
          </label>
          <span class="ml-1 truncate font-semibold">{{ title }}</span>
        </button>
        <a :href="url"
           class="flex justify-end hover:text-emerald-600 stroke-slate-700 fill-slate-700 hover:stroke-emerald-600 hover:fill-emerald-600">
          <div class="w-4"><IconGo/></div>
        </a>
      </div>

    </div>
    <div v-if="expanded" class="ml-5">
      <slot/>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: '',
    url: '',
    expand: false
  },
  data() {
    return {
      expanded: this.expand
    }
  },
  methods: {
    expand() {
      this.expanded = !this.expanded
    }
  }
}
</script>
<script setup>

</script>

<template>
  <nav class="text-cyan-950">
    <ul class="space-y-1.5">
      <li v-for="categoryL1 in categories">
        <button @click="pickCategory(categoryL1.id)"
           class="flex items-center font-semibold stroke-slate-700 hover:text-emerald-600 hover:stroke-emerald-600">
          <svg xmlns="http://www.w3.org/2000/svg" focusable="false" fill="none"
               viewBox="0 0 24 24" class="absolute w-4 h-4 min-w-[1rem] min-h-[1rem]">
            <path v-if="categoryId === categoryL1.id"
                  d="M8 12H12M12 12H16M12 12V16M12 12V8M4 16.8002V7.2002C4 6.08009 4 5.51962 4.21799 5.0918C4.40973 4.71547 4.71547 4.40973 5.0918 4.21799C5.51962 4 6.08009 4 7.2002 4H16.8002C17.9203 4 18.4801 4 18.9079 4.21799C19.2842 4.40973 19.5905 4.71547 19.7822 5.0918C20.0002 5.51962 20.0002 6.07967 20.0002 7.19978V16.7998C20.0002 17.9199 20.0002 18.48 19.7822 18.9078C19.5905 19.2841 19.2842 19.5905 18.9079 19.7822C18.4805 20 17.9215 20 16.8036 20H7.19691C6.07899 20 5.5192 20 5.0918 19.7822C4.71547 19.5905 4.40973 19.2842 4.21799 18.9079C4 18.4801 4 17.9203 4 16.8002Z"
                  stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            <path v-else
                  d="M8 12H16M4 16.8002V7.2002C4 6.08009 4 5.51962 4.21799 5.0918C4.40973 4.71547 4.71547 4.40973 5.0918 4.21799C5.51962 4 6.08009 4 7.2002 4H16.8002C17.9203 4 18.4801 4 18.9079 4.21799C19.2842 4.40973 19.5905 4.71547 19.7822 5.0918C20.0002 5.51962 20.0002 6.07967 20.0002 7.19978V16.7998C20.0002 17.9199 20.0002 18.48 19.7822 18.9078C19.5905 19.2841 19.2842 19.5905 18.9079 19.7822C18.4805 20 17.9215 20 16.8036 20H7.19691C6.07899 20 5.5192 20 5.0918 19.7822C4.71547 19.5905 4.40973 19.2842 4.21799 18.9079C4 18.4801 4 17.9203 4 16.8002Z"
                  stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>

          </svg>
          <h5 class="ml-5">{{ categoryL1.title }}</h5>
        </button>
        <ul v-if="categoryL1.children?.length !== 0" class="mt-3 ml-4 space-y-1.5">
          <li v-for="categoryL2 in categoryL1.children">
            <button @click="pickCategory(categoryL2.id)"
               class="flex items-center font-semibold stroke-slate-700 hover:text-emerald-600 hover:stroke-emerald-600">
              <svg xmlns="http://www.w3.org/2000/svg" focusable="false"
                   fill="none" viewBox="0 0 24 24" class="absolute w-4 h-4 min-w-[1rem] min-h-[1rem]">
                <path v-if="categoryId === categoryL2.id"
                      d="M8 12H12M12 12H16M12 12V16M12 12V8M4 16.8002V7.2002C4 6.08009 4 5.51962 4.21799 5.0918C4.40973 4.71547 4.71547 4.40973 5.0918 4.21799C5.51962 4 6.08009 4 7.2002 4H16.8002C17.9203 4 18.4801 4 18.9079 4.21799C19.2842 4.40973 19.5905 4.71547 19.7822 5.0918C20.0002 5.51962 20.0002 6.07967 20.0002 7.19978V16.7998C20.0002 17.9199 20.0002 18.48 19.7822 18.9078C19.5905 19.2841 19.2842 19.5905 18.9079 19.7822C18.4805 20 17.9215 20 16.8036 20H7.19691C6.07899 20 5.5192 20 5.0918 19.7822C4.71547 19.5905 4.40973 19.2842 4.21799 18.9079C4 18.4801 4 17.9203 4 16.8002Z"
                      stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path v-else
                      d="M8 12H16M4 16.8002V7.2002C4 6.08009 4 5.51962 4.21799 5.0918C4.40973 4.71547 4.71547 4.40973 5.0918 4.21799C5.51962 4 6.08009 4 7.2002 4H16.8002C17.9203 4 18.4801 4 18.9079 4.21799C19.2842 4.40973 19.5905 4.71547 19.7822 5.0918C20.0002 5.51962 20.0002 6.07967 20.0002 7.19978V16.7998C20.0002 17.9199 20.0002 18.48 19.7822 18.9078C19.5905 19.2841 19.2842 19.5905 18.9079 19.7822C18.4805 20 17.9215 20 16.8036 20H7.19691C6.07899 20 5.5192 20 5.0918 19.7822C4.71547 19.5905 4.40973 19.2842 4.21799 18.9079C4 18.4801 4 17.9203 4 16.8002Z"
                      stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>

              </svg>
              <h5 class="ml-5">{{ categoryL2.title }}</h5>
            </button>
            <ul v-if="categoryL2.children?.length !== 0" class="mt-3 ml-4 space-y-1.5">
              <li v-for="categoryL3 in categoryL2.children">
                <button @click="pickCategory(categoryL3.id)"
                   class="flex items-center font-semibold stroke-slate-700 hover:text-emerald-600 hover:stroke-emerald-600">
                  <svg v-if="categoryL1.children?.length !== 0" xmlns="http://www.w3.org/2000/svg" focusable="false"
                       fill="none" viewBox="0 0 24 24" class="absolute w-4 h-4 min-w-[1rem] min-h-[1rem]">
                    <path v-if="categoryId === categoryL3.id"
                          d="M8 12H12M12 12H16M12 12V16M12 12V8M4 16.8002V7.2002C4 6.08009 4 5.51962 4.21799 5.0918C4.40973 4.71547 4.71547 4.40973 5.0918 4.21799C5.51962 4 6.08009 4 7.2002 4H16.8002C17.9203 4 18.4801 4 18.9079 4.21799C19.2842 4.40973 19.5905 4.71547 19.7822 5.0918C20.0002 5.51962 20.0002 6.07967 20.0002 7.19978V16.7998C20.0002 17.9199 20.0002 18.48 19.7822 18.9078C19.5905 19.2841 19.2842 19.5905 18.9079 19.7822C18.4805 20 17.9215 20 16.8036 20H7.19691C6.07899 20 5.5192 20 5.0918 19.7822C4.71547 19.5905 4.40973 19.2842 4.21799 18.9079C4 18.4801 4 17.9203 4 16.8002Z"
                          stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <path v-else
                          d="M8 12H16M4 16.8002V7.2002C4 6.08009 4 5.51962 4.21799 5.0918C4.40973 4.71547 4.71547 4.40973 5.0918 4.21799C5.51962 4 6.08009 4 7.2002 4H16.8002C17.9203 4 18.4801 4 18.9079 4.21799C19.2842 4.40973 19.5905 4.71547 19.7822 5.0918C20.0002 5.51962 20.0002 6.07967 20.0002 7.19978V16.7998C20.0002 17.9199 20.0002 18.48 19.7822 18.9078C19.5905 19.2841 19.2842 19.5905 18.9079 19.7822C18.4805 20 17.9215 20 16.8036 20H7.19691C6.07899 20 5.5192 20 5.0918 19.7822C4.71547 19.5905 4.40973 19.2842 4.21799 18.9079C4 18.4801 4 17.9203 4 16.8002Z"
                          stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>

                  </svg>
                  <h5 class="ml-5">{{ categoryL3.title }}</h5>
                </button>
              </li>
            </ul>
          </li>
        </ul>
      </li>
    </ul>

  </nav>
</template>
<script>
import RequestApi from "@/utils/request-api";
import Api from "@/utils/api";

export default {
  data() {
    return {
      categories: [],
      categoryId: Number
    }
  },
  methods: {
    getCategories() {
      RequestApi.getRequest(Api.getCategories)
          .then(response => {
            this.categories.push(...response?.data?.data)
            console.log(response?.data)
          })
          .catch(error => {
            if (!RequestApi.hasAuthorize(error, this.getCategories)) return
          })
    },
    pickCategory(id) {
      this.categoryId = id
      console.log(this.categoryId)
      this.$emit("change", id)
    },
  },
  mounted() {
    this.getCategories()
  },
}
</script>

<style scoped>

</style>
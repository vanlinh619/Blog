<script setup>

</script>

<template>
  <header class="sticky top-0 z-50">
    <div class="flex items-center justify-between flex-wrap bg-emerald-600/80 backdrop-blur-md p-4">
      <div class="flex items-center flex-shrink-0 text-white mr-6">
        <svg class="fill-current h-8 w-8 mr-2" width="54" height="54" viewBox="0 0 54 54"
             xmlns="http://www.w3.org/2000/svg">
          <path
              d="M13.5 22.1c1.8-7.2 6.3-10.8 13.5-10.8 10.8 0 12.15 8.1 17.55 9.45 3.6.9 6.75-.45 9.45-4.05-1.8 7.2-6.3 10.8-13.5 10.8-10.8 0-12.15-8.1-17.55-9.45-3.6-.9-6.75.45-9.45 4.05zM0 38.3c1.8-7.2 6.3-10.8 13.5-10.8 10.8 0 12.15 8.1 17.55 9.45 3.6.9 6.75-.45 9.45-4.05-1.8 7.2-6.3 10.8-13.5 10.8-10.8 0-12.15-8.1-17.55-9.45-3.6-.9-6.75.45-9.45 4.05z"/>
        </svg>
        <span class="font-semibold text-xl tracking-tight">Dip Code</span>
      </div>
      <div class="w-full block flex-grow lg:flex lg:items-center lg:w-auto">
        <div class="text-sm lg:flex-grow">
          <a href="#responsive-header"
             class="block mt-4 lg:inline-block lg:mt-0 text-white mr-4 hover:text-cyan-950">
            Docs
          </a>
          <a href="#responsive-header"
             class="block mt-4 lg:inline-block lg:mt-0 text-white mr-4 hover:text-cyan-950">
            Examples
          </a>
          <a href="#responsive-header" class="block mt-4 lg:inline-block lg:mt-0 text-white hover:text-cyan-950">
            About
          </a>
        </div>
        <div>
          <router-link to="/login"
                       class="inline-block text-sm px-4 py-2 leading-none border rounded text-white border-white hover:border-transparent hover:text-teal-500 hover:bg-white mt-4 lg:mt-0">
            Login
          </router-link>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped>

</style>
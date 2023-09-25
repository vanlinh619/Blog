<script setup>

import Nav from "@/design/Nav.vue";
</script>

<template>
  <div
      class="lg:block fixed z-20 inset-0 top-16 left-[max(0px,calc(50%-45rem))] right-auto w-[19rem] pb-10 pl-8 pr-6 overflow-y-auto">
    <div class="sticky top-0 -ml-0.5 pointer-events-none">
      <div class="h-10 bg-white dark:bg-slate-900"></div>
      <div class="bg-white dark:bg-slate-900 relative pointer-events-auto">
        <button type="button"
                class="w-full lg:flex items-center text-sm leading-6 text-slate-400 rounded-md ring-1 ring-slate-900/10 shadow-sm py-1.5 pl-2 pr-3 hover:ring-slate-300 dark:bg-slate-800 dark:highlight-white/5 dark:hover:bg-slate-700">
          <svg width="24" height="24" fill="none" aria-hidden="true" class="mr-3 flex-none">
            <path d="m19 19-3.5-3.5" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                  stroke-linejoin="round"></path>
            <circle cx="11" cy="11" r="6" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                    stroke-linejoin="round"></circle>
          </svg>
          Quick search...<span class="ml-auto pl-3 flex-none text-xs font-semibold">Ctrl K</span></button>
      </div>
      <div class="h-8 bg-gradient-to-b from-white dark:from-slate-900"></div>
    </div>
    <nav class="text-cyan-950">
      <ul>
        <li>
          <span class="flex items-center font-semibold">
            <svg xmlns="http://www.w3.org/2000/svg" aria-hidden="true" focusable="false" viewBox="0 0 24 24"
                 class="w-4 h-4 min-w-[1rem] min-h-[1rem]">
              <path
                  d="M12,16c-0.3,0-0.5-0.1-0.7-0.3l-6-6c-0.4-0.4-0.4-1,0-1.4s1-0.4,1.4,0l5.3,5.3l5.3-5.3c0.4-0.4,1-0.4,1.4,0s0.4,1,0,1.4l-6,6C12.5,15.9,12.3,16,12,16z"></path>
            </svg>

            <h5 class="ml-1">Java</h5>

          </span>
          <ul class="border-l space-y-1.5 mt-3 ml-[0.45rem]">
            <li class="block pl-[0.65rem] border-l -ml-[0.5px] hover:border-slate-400">
              <a class="font-normal hover:text-cyan-950 hover:font-medium" href="#">AA hahds dshdhsdhsh </a>
            </li>
            <li class="block pl-[0.65rem] border-l -ml-[0.5px] hover:border-slate-400">
              <a class="font-normal hover:text-cyan-950 hover:font-medium" href="#">AA hahds </a>
            </li>
            <li class="block pl-[0.65rem] border-l -ml-[0.5px] hover:border-slate-400">
              <a class="font-normal hover:text-cyan-950 hover:font-medium" href="#">AA hahds dshdhsdhsh </a>
            </li>
            <li class="block pl-[0.65rem] border-l -ml-[0.5px] hover:border-slate-400">
              <a class="font-normal hover:text-cyan-950 hover:font-medium" href="#">AA hahds </a>
            </li>
            <li class="block pl-[0.65rem] border-l -ml-[0.5px] hover:border-slate-400">
              <a class="font-normal hover:text-cyan-950 hover:font-medium" href="#">AA hahds dshdhsdhsh </a>
            </li>
            <li class="block pl-[0.65rem] border-l -ml-[0.5px] hover:border-slate-400">
              <a class="font-normal hover:text-cyan-950 hover:font-medium" href="#">AA hahds </a>
            </li>
          </ul>
        </li>
      </ul>

    </nav>
  </div>
</template>

<style scoped>

</style>
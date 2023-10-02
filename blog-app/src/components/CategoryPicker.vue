<script setup>

</script>

<template>
<!--  <nav class="text-cyan-950">-->
<!--    <ul class="space-y-1.5">-->
<!--      <li th:each="categoryL1: ${categories}">-->
<!--          <span class="flex items-center font-semibold">-->
<!--            <svg xmlns="http://www.w3.org/2000/svg" aria-hidden="true" focusable="false" viewBox="0 0 24 24"-->
<!--                 class="w-4 h-4 min-w-[1rem] min-h-[1rem]">-->
<!--              <path-->
<!--                  d="M12,16c-0.3,0-0.5-0.1-0.7-0.3l-6-6c-0.4-0.4-0.4-1,0-1.4s1-0.4,1.4,0l5.3,5.3l5.3-5.3c0.4-0.4,1-0.4,1.4,0s0.4,1,0,1.4l-6,6C12.5,15.9,12.3,16,12,16z"></path>-->
<!--            </svg>-->
<!--            <h5 class="ml-1" th:text="${categoryL1.getTitle()}"></h5>-->
<!--          </span>-->
<!--        <ul class="mt-3 ml-4 space-y-1.5">-->
<!--          <li th:each="categoryL2: ${categoryL1.getChildren()}">-->
<!--              <span class="flex items-center font-semibold">-->
<!--                <svg xmlns="http://www.w3.org/2000/svg" aria-hidden="true" focusable="false" viewBox="0 0 24 24"-->
<!--                     class="w-4 h-4 min-w-[1rem] min-h-[1rem]">-->
<!--                  <path-->
<!--                      d="M12,16c-0.3,0-0.5-0.1-0.7-0.3l-6-6c-0.4-0.4-0.4-1,0-1.4s1-0.4,1.4,0l5.3,5.3l5.3-5.3c0.4-0.4,1-0.4,1.4,0s0.4,1,0,1.4l-6,6C12.5,15.9,12.3,16,12,16z"></path>-->
<!--                </svg>-->
<!--                <h5 class="ml-1" th:text="${categoryL2.getTitle()}"></h5>-->
<!--              </span>-->
<!--            <ul class="mt-3 ml-4 space-y-1.5">-->
<!--              <li th:each="categoryL3: ${categoryL2.getChildren()}">-->
<!--                  <span class="flex items-center font-semibold">-->
<!--                    <svg xmlns="http://www.w3.org/2000/svg" aria-hidden="true" focusable="false" viewBox="0 0 24 24"-->
<!--                         class="w-4 h-4 min-w-[1rem] min-h-[1rem]">-->
<!--                      <path-->
<!--                          d="M12,16c-0.3,0-0.5-0.1-0.7-0.3l-6-6c-0.4-0.4-0.4-1,0-1.4s1-0.4,1.4,0l5.3,5.3l5.3-5.3c0.4-0.4,1-0.4,1.4,0s0.4,1,0,1.4l-6,6C12.5,15.9,12.3,16,12,16z"></path>-->
<!--                    </svg>-->
<!--                    <h5 class="ml-1" th:text="${categoryL3.getTitle()}"></h5>-->
<!--                  </span>-->
<!--                <ul class="mt-3 ml-4 space-y-1.5">-->
<!--                  <li th:each="post: ${categoryL3.getPosts()}" class="ml-1">-->
<!--                    <a class="font-normal hover:text-cyan-950 hover:font-medium" th:href="${post.getSlug()}"-->
<!--                       th:text="${post.getTitle()}"></a>-->
<!--                  </li>-->
<!--                </ul>-->
<!--              </li>-->
<!--              <li th:each="post: ${categoryL2.getPosts()}" class="ml-1">-->
<!--                <a class="font-normal hover:text-cyan-950 hover:font-medium" th:href="${post.getSlug()}"-->
<!--                   th:text="${post.getTitle()}"></a>-->
<!--              </li>-->
<!--            </ul>-->
<!--          </li>-->
<!--          <li th:each="post: ${categoryL1.getPosts()}" class="ml-1">-->
<!--            <a class="font-normal hover:text-cyan-950 hover:font-medium" th:href="${post.getSlug()}"-->
<!--               th:text="${post.getTitle()}"></a>-->
<!--          </li>-->
<!--        </ul>-->
<!--      </li>-->
<!--    </ul>-->

<!--  </nav>-->
</template>
<script>

</script>

<style scoped>

</style>
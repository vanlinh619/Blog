<template>
  <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
</template>
<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
export default {
  data() {
    return {
      editor: ClassicEditor,
      editorData: '<p>Content of the editor.</p>',
      editorConfig: {}
    };
  }
}
</script>
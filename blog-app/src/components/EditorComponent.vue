<script setup>
</script>
<template>
  <div id="app">
    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
  </div>
</template>

<script>
import {ClassicEditor} from '@ckeditor/ckeditor5-editor-classic';

import {Alignment} from '@ckeditor/ckeditor5-alignment';  // Importing the package.
import {Autoformat} from '@ckeditor/ckeditor5-autoformat';
import {Bold, Italic, Strikethrough, Underline, Code, Subscript, Superscript} from '@ckeditor/ckeditor5-basic-styles';
import {BlockQuote} from '@ckeditor/ckeditor5-block-quote';
import {CloudServices} from '@ckeditor/ckeditor5-cloud-services';
import {Essentials} from '@ckeditor/ckeditor5-essentials';
import {Heading} from '@ckeditor/ckeditor5-heading';
import {
  Image,
  ImageCaption,
  ImageStyle,
  ImageToolbar,
  ImageUpload
} from '@ckeditor/ckeditor5-image';
import {Indent} from '@ckeditor/ckeditor5-indent';
import {Link} from '@ckeditor/ckeditor5-link';
import {List} from '@ckeditor/ckeditor5-list';
import {MediaEmbed} from '@ckeditor/ckeditor5-media-embed';
import {Paragraph} from '@ckeditor/ckeditor5-paragraph';
import {PasteFromOffice} from '@ckeditor/ckeditor5-paste-from-office';
import {Table, TableToolbar} from '@ckeditor/ckeditor5-table';
import {TextTransformation} from '@ckeditor/ckeditor5-typing';
import {SourceEditing} from '@ckeditor/ckeditor5-source-editing';
import {FindAndReplace} from '@ckeditor/ckeditor5-find-and-replace';
import {SelectAll} from '@ckeditor/ckeditor5-select-all';
import { RemoveFormat } from '@ckeditor/ckeditor5-remove-format';

export default {
  name: 'app',
  data() {
    return {
      editor: ClassicEditor,
      editorData: '<p>Content of the editor.</p>',
      editorConfig: {
        plugins: [
          Alignment,  // Adding the package to the list of plugins.
          Autoformat,
          FindAndReplace,
            Code,
            Subscript,
            Superscript,
            RemoveFormat,
          SelectAll,
          Strikethrough,
          BlockQuote,
          Bold,
          SourceEditing,
          Underline,
          CloudServices,
          Essentials,
          Heading,
          Image,
          ImageCaption,
          ImageStyle,
          ImageToolbar,
          ImageUpload,
          Indent,
          Italic,
          Link,
          List,
          MediaEmbed,
          Paragraph,
          PasteFromOffice,
          Table,
          TableToolbar,
          TextTransformation
        ],
        toolbar: {
          items: [
            'findAndReplace', 'SelectAll', '|',
            'heading', '|',
            'bold', 'italic', 'strikethrough', 'underline', 'code', 'subscript', 'superscript', 'removeFormat', '|',
            'alignment',
            'bulletedList',
            'numberedList',
            '|',
            'outdent',
            'indent',
            '|',
            'link',
            'imageUpload',
            'blockQuote',
            'insertTable',
            'mediaEmbed',
            'undo',
            'redo',
            '|',
            'SourceEditing',
          ]
        },
        language: 'en',
        image: {
          toolbar: [
            'imageTextAlternative',
            'toggleImageCaption',
            'imageStyle:inline',
            'imageStyle:block',
            'imageStyle:side'
          ]
        },
        table: {
          contentToolbar: [
            'tableColumn',
            'tableRow',
            'mergeTableCells'
          ]
        }
      }
    };
  }
};
</script>
<script setup>
</script>
<template>
  <div>
    <ckeditor :editor="editor"
              :modelValue="editorData"
              @update:modelValue="newValue => $emit('update:modelValue', newValue)"
              :config="editorConfig"/>
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
import {Image, ImageCaption, ImageStyle, ImageToolbar, ImageUpload} from '@ckeditor/ckeditor5-image';
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
import {RemoveFormat} from '@ckeditor/ckeditor5-remove-format';

export default {
  props: {
    modelValue: ''
  },
  emits: ['update:modelValue'],
  watch: {
    editorData(newData, oldData) {

    }
  },
  methods: {},
  mounted() {
    this.editor.conversion?.for('downcast').add(dispatcher => {
      dispatcher.on('element:h2', (evt, data, conversionApi) => {
        const viewWriter = conversionApi.writer;
        viewWriter.setAttribute('id', 'my_unique_id', conversionApi.mapper.toViewElement(data.item))
      });
    });
  },
  data() {
    return {
      editor: ClassicEditor,
      editorData: this.modelValue,
      editorConfig: {
        removePlugins: ['stylesheetparser'],
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
        heading: {
          options: [
            {model: 'paragraph', title: 'Paragraph'},
            {model: 'heading2', view: 'h2', title: 'Heading 1'},
            {model: 'heading3', view: 'h3', title: 'Heading 2'},
            {model: 'heading4', view: 'h4', title: 'Heading 3'},
          ]
        },
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
        },
      }
    };
  }
};
</script>
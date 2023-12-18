<template>
  <Modal :shown="modalShown" title="详细" @modal-closed="mountModalShown">
    <!-- <div v-for="key in data.keys()">{{ key }}:{{ data.get(key) }}</div> -->
    <div class="form-body">
      <!-- <var-row :gutter="[10, 10]">
            <var-col v-for="key in data.keys()" :span="24">
              <var-input :placeholder="key.valueOf()" :model-value="String(data.get(key))" variant="outlined" size="small" style="width: 100%;" readonly/>
            </var-col>
          </var-row> -->
      <var-row :gutter="[10, 10]">
        <var-col :span="12">
          <var-input placeholder="编号" :model-value="String(data.get('id'))" variant="outlined" size="small"
            style="width: 100%" readonly />
        </var-col>
        <var-col :span="12">
          <var-input placeholder="作者" :model-value="String(data.get('author'))" variant="outlined" size="small"
            style="width: 100%" readonly />
        </var-col>
        <var-col :span="24">
          <var-input placeholder="标题" :model-value="String(data.get('title'))" variant="outlined" size="small"
            style="width: 100%" readonly />
        </var-col>
        <var-col :span="24">
          <var-input placeholder="正文" :model-value="String(data.get('content'))" textarea variant="outlined" size="small"
            style="width: 100%" readonly />
        </var-col>
        <var-col :span="12">
          <var-input placeholder="创建时间" :model-value="String(data.get('created_time'))" variant="outlined" size="small"
            style="width: 100%" readonly />
        </var-col>
        <var-col :span="12">
          <var-input placeholder="最近更新时间" :model-value="String(data.get('updated_time'))" variant="outlined" size="small"
            style="width: 100%" readonly />
        </var-col>
      </var-row>
    </div>
    <template #footer>
      <div class="buttons">
        <var-button @click="readed" size="small" type="primary" v-if="readed_btn"
          :disabled="data.get('readed') === undefined || data.get('readed') === 1">已读</var-button>
        <var-button @click="closeModal" size="small">关闭</var-button>
      </div>
    </template>
  </Modal>
</template>

<script lang="ts">
import Modal from '../baseframe/Modal.vue';
export default {
  name: 'AdminManageLongTextInfoModal',
  components: { Modal },
  props: {
    data: {
      type: Map<String, String | Number>,
      required: true
    },
    shown: {
      type: Boolean,
      required: true,
    },
    readed_btn: {
      type: Boolean,
      required: false,
      default: false,
    }
  },
  emits: ['modal-closed', 'readed'],
  data() {
    return {
      modalShown: false,
    };
  },
  methods: {
    closeModal() {
      this.modalShown = false
    },
    readed() {
      this.$emit('readed', this.data.get('id'));
    },
    mountModalShown(newVal: boolean) {
      this.modalShown = newVal;
      this.$emit('modal-closed', false);
    },
  },
  watch: {
    shown(newVal: Boolean) {
      this.modalShown = newVal.valueOf();
    }
  }
}
</script>

<style scoped>
.form-body {
  margin-block-start: 10px;
  padding-inline-end: 10px;
  width: 100%;
}

.buttons {
  >* {
    margin-inline-start: 5px;
  }
}

@media (max-width: 800px) {
  .form-body {
    width: 600px;
  }
}</style>
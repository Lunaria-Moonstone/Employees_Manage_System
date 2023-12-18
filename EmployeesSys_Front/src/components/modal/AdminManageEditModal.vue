<template>
  <Modal :shown="modalShown" title="信息更改" @modal-closed="beforeModalClosed">
    <div class="form-body">
      <var-row :gutter="[10, 10]">
        <var-col :span="12" v-for="column, idx in columns">
          <var-tooltip style="width: 100%;" placement="bottom" type="warning" trigger="click"
            :disabled="column.regulator == undefined || (column.regulator != undefined && String(inputModel[idx]).match(column.regulator) != null)">
            <!-- <var-input v-if="column.type === 'input'" :placeholder="column.label" v-model="inputModel[idx]"
                  variant="outlined" size="small" style="width: 100%;" />
                <var-select v-else v-model="inputModel[idx]" size="small" style="width: 100%;"
                  variant="outlined" :placeholder="column.label"></var-select> -->
            <el-input v-if="column.type === 'input'" v-model="<string>inputModel[idx]" :placeholder="column.label"
              style="width: 100%;" />
            <el-select v-else-if="column.type === 'select'" style="width: 100%;" v-model="inputModel[idx]" placeholder="请选择" filterable>
              <el-option v-for="item in column.dropdownOpt" :key="item['key']" :label="item['label']"
                :value="item['value']" />
            </el-select>
            <el-select v-else style="width: 100%;" v-model="inputModel[idx]" multiple>
              <el-option v-for="item in column.dropdownOpt" :key="item['key']" :label="item['label']"
                :value="item['value']" />
            </el-select>
            <template #content>
              内容格式错误
            </template>
          </var-tooltip>
        </var-col>
      </var-row>
    </div>
    <template #footer>
      <div class="form-buttons">
        <var-button type="primary" @click="save" size="small">保存</var-button>
        <var-button @click="close" size="small">关闭</var-button>
      </div>
    </template>
  </Modal>
</template>

<script lang="ts">
import type { Ref, PropType } from 'vue';
import { ref } from 'vue';
import { ElInput, ElSelect, ElOption } from 'element-plus'

import * as $types from '@/type-regulator/ComponentsType';

import Modal from '../baseframe/Modal.vue';
export default {
  name: 'AdminManageEditModal',
  components: {
    Modal,
    ElInput, ElOption, ElSelect
  },
  props: {
    columns: {
      type: Object as PropType<$types.FormItems>,
      required: true,
    },
    data: {
      type: Object as PropType<$types.RowData>,
      required: true,
    },
    shown: {
      type: Boolean,
      required: true,
      default: false,
    }
  },
  emits: ['modal-closed', 'save-change'],
  data() {
    const inputModel: Ref<Array<string | number | Array<string>>> = ref(this.columns.map(x => ''));
    return {
      modalShown: false,
      inputModel,
    };
  },
  methods: {
    beforeModalClosed(newVal: boolean) {
      this.modalShown = newVal;
      this.$emit('modal-closed', false);
    },
    save() {
      const editData: $types.RowData = new Map();
      this.inputModel.forEach((value, idx, _) => {
        // editData[this.columns[idx].key] = value;
        editData.set(this.columns[idx].key, value)
      })
      this.$emit('save-change', editData);
    },
    close() {
      this.modalShown = false;
      this.inputModel = this.inputModel.map(x => '');
    }
  },
  watch: {
    shown(newVal: Boolean) {
      // update data if shown switch to true
      if (newVal.valueOf()) {
        this.columns.forEach((value, idx, _) => {
          if (typeof this.data.get(value.key) === 'string')
            this.inputModel[idx] = <string>this.data.get(value.key);
          else if (typeof this.data.get(value.key) === 'number')
            this.inputModel[idx] = <number>this.data.get(value.key);
          else
            this.inputModel[idx] = <Array<string>>this.data.get(value.key);
        })
      }
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

.form-buttons {
  >* {
    margin-top: -5px;
    margin-inline-start: 5px;
  }
}
</style>
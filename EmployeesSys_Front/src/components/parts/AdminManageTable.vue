<template>
  <!-- <var-table>
    <thead>
      <th v-for="column in columns">{{ column.label }}</th>
    </thead>
    <tbody>
      <tr v-for="item in data" :key="item.id">
        <td v-for="column in columns">
          {{ item[column.key] }}
        </td>
        <td>
          
        </td>
      </tr>
    </tbody>
    <template #footer>
      <div class="table-pagination">
        <var-pagination :simple="false" :current="1" :total="data.length" @change="getPage" />
      </div>
    </template>
  </var-table> -->

  <div class="data-table-body">
  <el-table :data="data" style="width: 100%;" @selection-change="handleSelectionChange" :max-height="'calc(100vh - 3 * var(--standard-margin) - 30vh - 30px - 45px - 76px - 44px)'">
    <el-table-column fixed type="selection" width="55" />
    <el-table-column fixed prop="id" label="编号" width="320"/>
    <el-table-column v-for="column in columns" :prop="column.key" :label="column.label" :sortable="column.sorted"/>
    <el-table-column label="操作" fixed="right" width="200">
      <template #default="scope">
        <var-button text type="primary" @click="emitRowInfo(scope.row.id)" :disabled="buttonDisabled.has('info')">详细</var-button>
        <var-button text type="primary" @click="emitRowEdit(scope.row.id)" :disabled="buttonDisabled.has('edit')">修改</var-button>
        <var-button text type="primary" @click="emitRowDel(scope.row.id)" :disabled="buttonDisabled.has('delete')">删除</var-button>
      </template>
    </el-table-column>
  </el-table>
  </div>
</template>

<script lang="ts">
import type { Ref } from 'vue';
import { ref } from 'vue';
import { ElTable, ElTableColumn } from 'element-plus';

import * as $types from '@/type-regulator/ComponentsType';

export default {
  name: 'AdminManageTable',
  components: { ElTable, ElTableColumn },
  props: {
    // column names of table
    columns: {
      type: Array<$types.TableColumn>,
      required: true
    },
    // shown data of table
    data: {
      type: Array<{ [key: string]: string | number }>,
      required: false,
      default: []
    },
    buttonDisabled: {
      type: Set<string>,
      required: false,
      default: new Set()
    }
  },
  emits: ['delete-row', 'edit-row', 'select-row', 'select-rows'],
  data() {
    const mutipleSelection: Ref<Array<string>> = ref([])
    return {
      mutipleSelection,
    }
  },
  methods: {
    emitRowInfo(id: string) {
      this.$emit('select-row', id);
    },
    emitRowDel(id: string) {
      this.$emit('delete-row', id);
    },
    emitRowEdit(id: string) {
      this.$emit('edit-row', id);
    },
    handleSelectionChange(val: { [key: string]: string }[]) {
      this.mutipleSelection = val.map(x => x['id']);
      this.$emit('select-rows', this.mutipleSelection);
    }
  },
  created() {
  },
}
</script>

<style lang="scss" scoped>
.table-pagination {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 60px;
  padding: 0 16px;
}
</style>
<template>
  <AdminPanel :activeIdx="0">
    <div class="title-and-search-field">
      <h3>员工管理</h3>
      <AdminManageSearchField :items="searchItems" @post-data="postData" />
    </div>
    <div class="table-field">
      <div class="buttons">
        <var-button type="primary" @click="additionModal = true">新增</var-button>
        <var-button type="danger" @click="deleteRows">删除</var-button>
      </div>

      <AdminManageTable :columns="columnLabels" :data="data" @delete-row="deleteRow" @select-row="infoRow"
        @edit-row="editRow" @select-rows="selectedChange" />
    </div>
  </AdminPanel>

  <AdminManageInfoModal :shown="infoModalShown" :data="rowData"
    @modal-closed="(newVal: boolean) => infoModalShown = newVal"></AdminManageInfoModal>
  <AdminManageAdditionModal :shown="additionModal" :columns="editColumns"
    @modal-closed="(newVal: boolean) => additionModal = newVal" @save-change="additionConfirm" />
  <AdminManageEditModal :shown="editModalShown" :columns="editColumns" :data="rowData"
    @modal-closed="(newVal: boolean) => editModalShown = newVal" @save-change="editRowConfirm"></AdminManageEditModal>
</template>

<script lang="ts">
import type { Ref } from 'vue';
import { ref } from 'vue';
import { Snackbar, Dialog } from '@varlet/ui';

import * as $types from '@/type-regulator/ComponentsType';
import { getUsers, delUser, delUsers, editUser, getUser, addUser } from '@/api/manage/_run';

import AdminPanel from '@/components/panel/AdminPanel.vue';
import AdminManageSearchField from '@/components/parts/AdminManageSearchField.vue';
import AdminManageTable from '@/components/parts/AdminManageTable.vue';
import AdminManageInfoModal from '@/components/modal/AdminManageInfoModal.vue';
import AdminManageEditModal from '@/components/modal/AdminManageEditModal.vue';
import AdminManageAdditionModal from '@/components/modal/AdminManageAdditionModal.vue';

var editId: string | undefined = undefined;

export default {
  components: {
    AdminPanel,
    AdminManageSearchField,
    AdminManageTable,
    AdminManageInfoModal,
    AdminManageEditModal,
    AdminManageAdditionModal
  },
  data() {
    const searchItems: Ref<$types.AdminManageSearchFieldItems> = ref([
      {
        label: '编号',
        key: 'id',
        type: 'input',
        options: [],
      },
      // {
      //   label: '用户名',
      //   key: 'username',
      //   type: 'input',
      //   options: []
      // },
      // {
      //   label: '姓名',
      //   key: 'name',
      //   type: 'input',
      //   options: []
      // },
      // {
      //   label: '年龄',
      //   key: 'age',
      //   type: 'input',
      //   options: []
      // },
      // {
      //   label: '性别',
      //   key: 'gender',
      //   type: 'select',
      //   options: [
      //     {
      //       label: '男',
      //       value: '1',
      //     },
      //     {
      //       label: '女',
      //       value: '0',
      //     },
      //   ]
      // },
    ]);
    const columnLabels: Ref<$types.TableColumns> = ref([
      // {
      //   key: 'id',
      //   label: '编号'
      // },
      {
        key: 'username',
        label: '用户名'
      },
      {
        key: 'name',
        label: '姓名'
      },
      {
        key: 'gender',
        label: '性别'
      },
      {
        key: 'phone',
        label: '电话号码'
      },
    ]);
    const editColumns: Ref<$types.FormItems> = ref([
      {
        key: 'username',
        label: '用户名',
        type: 'input',
      },
      {
        key: 'name',
        label: '姓名',
        type: 'input',
      },
      {
        key: 'gender',
        label: '性别',
        type: 'select',
        dropdownOpt: [
          {
            key: '1',
            label: '男',
            value: '1'
          },
          {
            key: '0',
            label: '女',
            value: '0'
          }
        ]
      },
      {
        key: 'phone',
        label: '电话号码',
        type: 'input'
      }
    ])
    const data: Ref<$types.FormalData> = ref([]);
    const lastData: Ref<{ [key: string]: string | number }> = ref({});
    const rowData: Ref<$types.RowData> = ref(new Map());
    const selectedRows: Ref<Array<string>> = ref([]);
    return {
      searchItems,
      columnLabels,
      editColumns,
      data,
      lastData,
      rowData,
      selectedRows,
      additionModal: false,
      infoModalShown: false,
      editModalShown: false,
    }
  },
  methods: {
    /**
     * get user list
     * @param data 
     */
    async postData(data: { [key: string]: string | number }) {
      data['root_status'] = 0;
      const response = await getUsers(data);
      if (response === null) {
        Snackbar.error('请求用户信息失败');
        return;
      }
      // Snackbar.success('请求成功！');
      console.log(response)
      this.lastData = data;
      this.data = (<Array<any>>response).map(x => {
        return {
          ...x,
          gender: x.gender === '1' ? "男" : "女"
        }
      });
    },
    /**
     * delete row user
     * @param id 
     */
    async deleteRow(id: string) {
      const actions = {
        confirm: async () => {
          var response = await delUser(id);
          if (response) Snackbar.success('删除成功');
          else Snackbar.error('删除失败');
          this.postData(this.lastData);
        },
        close: () => { },
        cancel: () => { }
      }
      actions[await Dialog(
        {
          title: '警告',
          message: '删除后无法恢复,是否确认删除？',
          confirmButtonTextColor: 'red',
        }
      )]();
    },
    /**
     * get row user info 
     */
    async infoRow(id: string) {
      var response = await getUser(id);
      if (response === null) {
        Snackbar.error('请求用户信息失败');
        return;
      }
      try {
        this.rowData = new Map(Object.entries(response[0]));
        this.rowData.delete("password")
        this.rowData.set("gender", response[0].gender === '1' ? "男" : "女");
      } catch (err) {
        Snackbar.error('请求用户信息失败');
        return;
      }
      this.infoModalShown = true;
    },
    async editRow(id: string) {
      var response = await getUser(id);
      if (response === null || response.length === 0) {
        Snackbar.error('请求用户信息失败');
        return;
      }
      const columns = new Set(this.editColumns.map(x => x.key));
      const data = response[0];
      this.rowData.clear();
      Object.keys(data).filter(value => columns.has(value)).forEach(value => this.rowData.set(value, data[value]));
      editId = id;
      this.editModalShown = true;
    },
    async editRowConfirm(data: $types.RowData) {
      if (editId === undefined) {
        Snackbar.error('更改对象ID为空');
        return;
      }
      if (data.get("name") === '') {
        Snackbar.warning('姓名不能为空');
        return;
      }
      if (data.get("username") === '') {
        Snackbar.warning('用户名不能为空');
        return;
      }
      const response = await editUser(editId, Object.fromEntries(data.entries()));
      if (response === null || !response.ok) {
        Snackbar.error('更新对象失败');
        return;
      }
      Snackbar.success('更新对象成功');
      this.editModalShown = false;
      editId = undefined;
      this.postData(this.lastData);
    },
    async additionConfirm(data: $types.RowData) {
      if (data.get("name") === '') {
        Snackbar.warning('姓名不能为空');
        return;
      }
      if (data.get("username") === '') {
        Snackbar.warning('用户名不能为空');
        return;
      }
      const response = await addUser(Object.fromEntries(data.entries()));
      if (response === null || !response.ok) {
        Snackbar.error('添加对象失败');
        return;
      }
      Snackbar.success('添加对象成功');
      this.additionModal = false;
      this.postData(this.lastData);
    },
    selectedChange(id_list: Array<string>) {
      this.selectedRows = id_list;
    },
    async switchRestricted(restrictedStatus: boolean) {
      if (this.selectedRows.length === 0) {
        Snackbar.warning('未选中任何行');
        return;
      }
      var successCnt: number = 0;
      for (var id of this.selectedRows) {
        try {
          var response = await editUser(id, { restricted: restrictedStatus ? 1 : 0 });
          if (response !== null && response) successCnt += 1;
        } catch (err) {
          console.error(err);
        }
      }
      Snackbar.success(`操作${this.selectedRows.length}条用户记录，其中成功${successCnt}条`);
      this.postData(this.lastData);
    },
    async deleteRows() {
      if (this.selectedRows.length === 0) {
        Snackbar.warning('未选中任何行');
        return;
      }
      const actions = {
        confirm: async () => {
          var response = await delUsers(this.selectedRows);
          Snackbar.success(`删除${this.selectedRows.length}条用户记录，其中成功${response}条`);
          this.postData(this.lastData);
        },
        close: () => { },
        cancel: () => { }
      }
      actions[await Dialog(
        {
          title: '警告',
          message: '删除后无法恢复,是否确认删除？',
          confirmButtonTextColor: 'red',
        }
      )]();
    },
  },
  mounted() {
    this.postData({});
  }
}
</script>
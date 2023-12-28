<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="桌位名称" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入桌位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="桌位大小" prop="tableSize">
        <el-input
          v-model="queryParams.tableSize"
          placeholder="请输入最小桌位大小"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否空闲" prop="isFree">
        <el-select v-model="queryParams.isFree" placeholder="请选择是否空闲" clearable>
          <el-option label="空闲" value="0" />
          <el-option label="非空闲" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:table:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:table:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:table:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:table:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange"
              :row-class-name="tableRowClassName">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="桌位id" align="center" prop="tableId" />-->
      <el-table-column label="桌位名称" align="center" prop="tableName" />
      <el-table-column label="桌位大小" align="center" prop="tableSize" />
      <el-table-column label="是否空闲" align="center" prop="isFree" :formatter="formatIsFree" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:table:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:table:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改桌位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="桌位名称" prop="tableName">
          <el-input v-model="form.tableName" placeholder="请输入桌位名称" />
        </el-form-item>
        <el-form-item label="桌位大小" prop="tableSize">
          <el-input v-model="form.tableSize" placeholder="请输入桌位大小" />
        </el-form-item>
        <el-form-item label="是否空闲" prop="isFree">
          <el-select v-model="form.isFree" placeholder="请选择是否空闲">
            <el-option label="空闲" value="0" />
            <el-option label="非空闲" value="1" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>

<script>
import { listTable, getTable, delTable, addTable, updateTable } from "@/api/system/table";

export default {
  name: "Table",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中的桌位名称
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 桌位表格数据
      tableList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: null,
        tableSize: null,
        isFree: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        tableName: [
          { required: true, message: "桌位名称不能为空", trigger: "blur" }
        ],
        tableSize: [
          { required: true, message: "桌位大小不能为空", trigger: "blur" }
        ],
        isFree: [
          { required: true, message: "桌位是否空闲，0为空闲不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 表格行的颜色控制 */
    tableRowClassName({row, rowIndex}) {
      // console.log(row);
      if (row.isFree === 1) {
        return 'warning-row';
      } else if (row.isFree === 0) {
        return 'success-row';
      }
      return '';
    },
    /** 格式化是否空闲 */
    formatIsFree(row, column) {
      if (row.isFree === 0) {
        return '空闲';
      } else if (row.isFree === 1) {
        return '非空闲';
      } else {
        return '未知';
      }
    },
    /** 查询桌位列表 */
    getList() {
      this.loading = true;
      listTable(this.queryParams).then(response => {
        this.tableList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        tableId: null,
        tableName: null,
        tableSize: null,
        isFree: null,
        isDeleted: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tableId)
      this.names = selection.map(item => item.tableName)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.isFree = "0";
      this.open = true;
      this.title = "添加桌位";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tableId = row.tableId || this.ids
      getTable(tableId).then(response => {
        this.form = response.data;
        if (this.form.isFree === 0) {
          this.form.isFree = "0";
        } else {
          this.form.isFree = "1";
        }
        this.open = true;
        this.title = "修改桌位";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tableId != null) {
            updateTable(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTable(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tableIds = row.tableId || this.ids;
      const tableNames = row.tableName || this.names;
      this.$modal.confirm('是否确认删除桌位名称为"' + tableNames + '"的数据项？').then(function() {
        return delTable(tableIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/table/export', {
        ...this.queryParams
      }, `table_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>


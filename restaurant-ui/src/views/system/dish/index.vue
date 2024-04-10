<template>
  <div class="app-container">
    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="菜品名称" prop="dishName">
        <el-input
          v-model="queryParams.dishName"
          placeholder="请输入菜品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="菜品分类" prop="dishName">
        <!--          <el-input v-model="form.dishType" placeholder="请输入菜品名称" />-->
        <el-select clearable  v-model="queryParams.dishType" placeholder="请选择">
          <el-option
            v-for="item in categoryList"
            :key="item.categoryId"
            :label="item.categoryName"
            :value="item.categoryId">
          </el-option>
        </el-select>
      </el-form-item>


<!--      <el-form-item label="菜品价格" prop="dishPrice">
        <el-input
          v-model="queryParams.dishPrice"
          placeholder="请输入菜品价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
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
          v-hasPermi="['system:dish:add']"
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
          v-hasPermi="['system:dish:edit']"
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
          v-hasPermi="['system:dish:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:dish:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:dish:export']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table stripe v-loading="loading" :data="dishList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="菜品id" align="center" prop="dishId" />-->
      <el-table-column label="菜品名称" align="center" prop="dishName" />
      <el-table-column label="菜品分类" align="center" prop="categoryName" />
      <el-table-column label="菜品价格" align="center" prop="dishPrice" />
      <el-table-column label="菜品描述" align="center" prop="dishDescription" />
      <el-table-column label="图片地址" align="center" prop="dishImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.dishImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:dish:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dish:remove']"
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

    <!-- 添加或修改菜品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="菜品名称" prop="dishName">
          <el-input v-model="form.dishName" placeholder="请输入菜品名称" />
        </el-form-item>
        <el-form-item label="菜品分类" prop="dishName">
<!--          <el-input v-model="form.dishType" placeholder="请输入菜品名称" />-->
          <el-select v-model="form.dishType" placeholder="请选择">
            <el-option
              v-for="item in categoryList"
              :key="item.categoryId"
              :label="item.categoryName"
              :value="item.categoryId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜品价格" prop="dishPrice">
          <el-input v-model="form.dishPrice" placeholder="请输入菜品价格" />
        </el-form-item>
        <el-form-item label="菜品描述" prop="dishDescription">
          <el-input v-model="form.dishDescription" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="图片地址" prop="dishImage">
          <image-upload v-model="form.dishImage"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDish, getDish, delDish, addDish, updateDish } from "@/api/system/dish";
import {listCategory} from "@/api/system/category";
import { getToken } from "@/utils/auth";
// 导入模板接口importTemplate
import { importTemplate } from "@/api/system/user";

export default {
  name: "Dish",
  dicts: ['is_deleted'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中数组名称
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 菜品表格数据
      dishList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dishName: null,
        dishType: null,
        dishPrice: null,
        dishDescription: null,
        dishImage: null,
      },
      // 菜品分类
      categoryList:[],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dishName: [
          { required: true, message: "菜品名称不能为空", trigger: "blur" }
        ],
        dishType: [
          { required: true, message: "菜品分类不能为空", trigger: "change" }
        ],
        dishPrice: [
          { required: true, message: "菜品价格不能为空", trigger: "blur" }
        ],
      },// 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_API_BASE_URL + "/system/dish/importData"
      },
    };
  },
  created() {
    this.getList();
    this.getCategoryList();
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/dish/importTemplate', {},`dish_template_${new Date().getTime()}.xlsx`);
    },
// 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
// 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
// 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 查询菜品列表 */
    getList() {
      this.loading = true;
      listDish(this.queryParams).then(response => {
        this.dishList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询类别列表 */
    getCategoryList() {
      this.loading = true;
      listCategory(this.queryParams).then(response => {
        this.categoryList = response.rows;
        // this.total = response.total;
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
        dishId: null,
        dishName: null,
        dishType: null,
        dishPrice: null,
        dishDescription: null,
        dishImage: null,
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
      this.ids = selection.map(item => item.dishId)
      this.names = selection.map(item => item.dishName)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getCategoryList();
      this.reset();
      this.open = true;
      this.title = "添加菜品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getCategoryList();
      this.reset();
      const dishId = row.dishId || this.ids
      getDish(dishId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改菜品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dishId != null) {
            updateDish(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDish(this.form).then(response => {
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
      const dishIds = row.dishId || this.ids;
      const dishNames = row.dishName || this.names;
      this.$modal.confirm('是否确认删除菜品名称为"' + dishNames + '"的数据项？').then(function() {
        return delDish(dishIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/dish/export', {
        ...this.queryParams
      }, `dish_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户姓名" prop="userId">
        <!--        <el-input
                  v-model="queryParams.userId"
                  placeholder="请输入用户id"
                  clearable
                  @keyup.enter.native="handleQuery"
                />-->
        <el-select v-model="queryParams.userId" clearable
                   filterable placeholder="请选择用户姓名">
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单时间" prop="orderTime">
        <el-date-picker clearable
                        v-model="queryParams.orderTime"
                        type="date"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="请选择订单时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="座位" prop="tableId">
        <!--        <el-input
                  v-model="queryParams.tableId"
                  placeholder="请输入座位id"
                  clearable
                  @keyup.enter.native="handleQuery"
                />-->
        <el-select v-model="queryParams.tableId" clearable
                   filterable placeholder="请选择桌位">
          <el-option
            v-for="item in tableList"
            :key="item.tableId"
            :label="item.tableName"
            :value="item.tableId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="配送员" prop="courierId">
        <el-select v-model="queryParams.courierId" clearable
                   filterable placeholder="请选择配送员">
          <el-option
            v-for="item in courierList"
            :key="item.courierId"
            :label="item.courierName"
            :value="item.courierId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus"></el-form-item>
      <el-select v-model="queryParams.orderStatus" clearable
                 filterable placeholder="请选择订单状态">
        <el-option
          v-for="item in dict.type.order_status"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:order:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:order:edit']"
        >修改
        </el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:order:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:order:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList()"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column type="index" label="序号" width="120" align="center">
      </el-table-column>
      <!--      <el-table-column label="订单id" align="center" prop="orderId" />-->
      <el-table-column label="用户姓名" align="center" prop="userName"/>
      <el-table-column label="订单时间" align="center" prop="orderTime" width="180">
      </el-table-column>

      <el-table-column label="订单状态" align="center" prop="orderStatus">
        <template slot-scope="scope">
          <el-tag v-for="item in dict.type.order_status"
                  :key="item.value"
                  v-if="item.value === scope.row.orderStatus"
                  :type="item.raw.cssClass">{{ item.label }}
          </el-tag>
        </template>
      </el-table-column>
      <!--      <el-table-column label="订单状态" align="center" prop="orderStatus" />-->
      <el-table-column label="座位" align="center" prop="tableName">
        <template slot-scope="scope">
          <span>{{ scope.row.tableName || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="配送地址" align="center" prop="userAddress">
        <template slot-scope="scope">
          <span>{{ scope.row.userAddress || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="配送状态" align="center" prop="deliveryStatus">
        <template slot-scope="scope">
          <span v-if="scope.row.deliveryStatus === null">--</span>
          <el-tag v-for="item in dict.type.delivery_status"
                  :key="item.value"
                  v-if="item.value === scope.row.deliveryStatus"
                  :type="item.raw.cssClass">{{ item.label }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="配送员姓名" align="center" prop="courierName">
        <template slot-scope="scope">
          <span>{{ scope.row.courierName || '--' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:order:detail']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:order:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:order:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList()"
    />

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id"/>
        </el-form-item>
        <el-form-item label="订单时间" prop="orderTime">
          <el-date-picker clearable
                          v-model="form.orderTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择订单时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="座位id" prop="tableId">
          <el-input v-model="form.tableId" placeholder="请输入座位id"/>
        </el-form-item>
        <el-form-item label="配送地址id" prop="addressId">
          <el-input v-model="form.addressId" placeholder="请输入配送地址id"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--    订单详情-->
    <el-dialog :title="title2" :visible.sync="open2" width="800px" append-to-body>
      <el-table :data="orderDetailsList" stripe style="width: 100%">
        <el-table-column type="index" label="序号" align="center"/>
        <el-table-column label="菜品名称" align="center" prop="dishName"/>
        <el-table-column label="菜品分类" align="center" prop="categoryName"/>
        <el-table-column label="菜品价格" align="center" prop="dishPrice">
          <template slot-scope="scope">
            <span>￥ {{ scope.row.dishPrice }}</span>
          </template>
        </el-table-column>
        <el-table-column label="菜品数量" align="center" prop="dishQuantity"/>
        <el-table-column label="小计金额" align="center">
          <template slot-scope="scope">
            <span>￥ {{ scope.row.dishPrice * scope.row.dishQuantity }}</span>
          </template>
        </el-table-column>
        <el-table-column label="菜品图片" align="center" prop="dishImg" width="100">
          <template slot-scope="scope">
            <image-preview :src="scope.row.dishImg" :width="50" :height="50"/>
          </template>
        </el-table-column>

      </el-table>

    </el-dialog>
  </div>
</template>

<script>
import {listOrder, getOrder, delOrder, addOrder, updateOrder, listOrderForList} from "@/api/system/order";
import {listUser} from "@/api/system/user";
import {listTable} from "@/api/system/table";
import {listCourier} from "@/api/system/courier";
import {getDetailByOrderId} from "@/api/system/detail";

export default {
  name: "Order",
  dicts: ['is_deleted', 'order_status'],
  data() {
    return {
      // 配送员数据
      courierList: [],
      // 桌位数据
      tableList: [],
      // 用户列表
      userList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 订单详情弹出层标题
      title2: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详情弹出层
      open2: false,
      // 订单详情数据
      orderDetailsList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        orderId: null,
        orderTime: null,
        orderStatus: null,
        tableId: null,
        addressId: null,
        deliveryStatus: null,
        courierId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          {required: true, message: "用户id不能为空", trigger: "blur"}
        ],
        orderTime: [
          {required: true, message: "订单时间不能为空", trigger: "blur"}
        ],
        orderStatus: [
          {required: true, message: "订单状态不能为空", trigger: "change"}
        ],
      }
    };
  },
  created() {
    const orderId = this.$route.params && this.$route.params.orderId;
    this.getUserList();
    this.getTableList();
    this.getCourierList();
    this.getList(orderId);
    // console.log(orderId)
  },
  methods: {
    /** 获取订单详情 */
    handleDetail(row) {
      this.loading = true;
      const orderId = row.orderId
      this.title2 = row.userName + "的订单详情";
      // console.log(row)
      getDetailByOrderId(orderId).then(response => {
        this.orderDetailsList = response.data;
        this.loading = false;
        this.open2 = true;
      });
    },
    /** 获取配送员列表 */
    getCourierList() {
      listCourier({pageNum: 1, pageSize: 1000}).then(response => {
        this.courierList = response.rows;
      });
    },
    /** 获取桌位列表 */
    getTableList() {
      listTable({pageNum: 1, pageSize: 1000}).then(response => {
        this.tableList = response.rows;
      });
    },
    /** 获取用户列表 */
    getUserList() {
      listUser({pageNum: 1, pageSize: 1000}).then(response => {
        this.userList = response.rows;
      });
    },
    /** 查询订单列表 */
    getList(orderId) {
      this.loading = true;
      if (orderId) {
        this.queryParams.orderId = orderId;
      }
      // console.log(this.queryParams)
      listOrderForList(this.queryParams).then(response => {
        this.orderList = response.rows;
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
        orderId: null,
        userId: null,
        orderTime: null,
        orderStatus: null,
        tableId: null,
        addressId: null,
        deliveryStatus: null,
        courierId: null,
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId || this.ids
      getOrder(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除订单编号为"' + orderIds + '"的数据项？').then(function () {
        return delOrder(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

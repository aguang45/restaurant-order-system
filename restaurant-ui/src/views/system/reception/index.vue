<template>
  <div class="recep-tabs">
    <el-tabs type="border-card" @tab-click="tabClick" v-model="activeName">
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-goods"></i> 开始点单 </span>
        <div v-if="activeName === 'first'">
          <!--    结算弹窗  -->
          <el-dialog title="结算订单" :visible.sync="shoppingDialogVisible" center>
            <div>
              <span style="font-size: 18px;margin-left: 20px">购物车列表</span>
              <span style="float: right; margin-right: 20px;font-size: 17px">总价：￥{{ shoppingCartList.reduce((total, item) => total + item.price * item.goodNum, 0) }}</span>
            </div>
            <el-divider></el-divider>
            <div style="display: flex;justify-content: space-between;">
              <div style="height: 60vh;overflow-y:auto;width: 490px;margin-left: 15px;">

                <el-empty v-if="shoppingCartList.length === 0" :image-size="200"></el-empty>
                <el-card v-for="dish in shoppingCartList" :key="dish.id" class="shopping-card" style="width: 97%" shadow="hover">
                  <div class="clearfix">
                    <span style="font-weight: inherit;font-size: 18px">{{ dish.name }}</span>
                    <span style="float: right; margin-right: 20px;font-size: 17px">￥{{ dish.price * dish.goodNum }}</span>
                    <span style="float: right; margin-right: 20px;font-size: 17px">{{ dish.goodNum }} 份</span>
                    <span style="float: right; margin-right: 20px;font-size: 17px">￥{{ dish.price }}</span>
                  </div>
                </el-card>
              </div>

              <div style="width: 490px;margin-top: 30px">
                <!--            右侧选择座位-->
                <el-card style="margin-right: 15px;font-size: 16px;height: 130px" shadow="hover">
                  <div slot="header">
                    <span>选择座位</span>
                  </div>
                  <el-row justify="center">
                    <el-col :span="24">
                      <el-select filterable clearable v-model="tableId" placeholder="请选择座位" style="width: 100%;">
                        <el-option v-for="item in tableList"
                                   :key="item.tableId"
                                   :label="item.tableName+'('+item.tableSize+'人)'"
                                   :value="item.tableId"
                                   :disabled="item.isFree === 0">
                        </el-option>
                      </el-select>
                    </el-col>
                  </el-row>
                </el-card>
                <div style="margin-top: 35px;margin-left: 20px">
                  <el-switch
                    style="display: block"
                    v-model="isPay"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    active-text="已付款"
                    inactive-text="未付款">
                  </el-switch>
                </div>
                <div style="margin: 30px;display: flex;justify-content: space-between;">
                  <el-button type="warning" style="width: 150px;margin-left: 50px" @click="shoppingDialogVisible = false">取消</el-button>
                  <el-button type="success" style="width: 150px;margin-right: 50px" @click="submitOrder()">确认</el-button>
                </div>
              </div>
            </div>
          </el-dialog>

          <!--        左侧选单-->
          <div style="display: flex" v-loading="loading1">
            <el-empty v-if="dishList.length === 0" :image-size="200"></el-empty>
            <el-tabs tab-position="left" class="order-tabs" type="border-card">
              <el-tab-pane :label="item.name" :key="item.id" v-for="item in dishList">
                <el-row type="flex" justify="left" class="dish-row">
                  <!--              <el-col :span="5">-->
                  <el-empty v-if="item.children.length === 0" :image-size="200"></el-empty>
                  <el-card v-for="dish in item.children" :key="dish.id" class="dish-card" shadow="hover">
                    <div style="display: flex">
                      <img :src="baseUrl+dish.logo" class="dish-image" alt="">
                      <span class="dish-description">{{ dish.description }}</span>
                    </div>
                    <div slot="header" class="clearfix">
                      <span style="font-weight: inherit;font-size: 20px">{{ dish.name }}</span>
                      <el-button plain style="float: right; padding: 0;width: 25px;height: 25px;font-size: 20px"
                                 type="text" icon="el-icon-circle-plus-outline" @click="addShoppingCart(dish)"></el-button>
                      <el-button plain style="float: right; padding: 0;width: 25px;height: 25px;font-size: 20px"
                                 type="text" icon="el-icon-remove-outline" @click="subShoppingCart(dish)"></el-button>
                      <span style="float: right; margin-right: 20px;font-size: 19px">￥{{ dish.price }}</span>
                    </div>
                    <div class="text item">
                    </div>
                  </el-card>
                  <!--              </el-col>-->
                </el-row>
              </el-tab-pane>
            </el-tabs>

            <!--          右侧购物车-->
            <el-card style="width: 23%;height: 80vh;overflow-y: auto;" shadow="hover">
              <el-empty v-if="shoppingCartList.length === 0" :image-size="200"></el-empty>
              <div slot="header">
                <span>购物车</span>
                <el-button size="mini" type="warning" style="float: right;font-size: 15px;margin-top: -8px"
                           @click="clearShoppingCart">清空
                </el-button>
                <el-button size="mini" type="success" style="float: right;margin-right: 15px;font-size: 15px;margin-top: -8px"
                           @click="shoppingDialogVisible = true">结算
                </el-button>
                <span style="float: right; margin-right: 20px;font-size: 17px">总价：￥{{ shoppingCartList.reduce((total, item) => total + item.price * item.goodNum, 0) }}</span>

              </div>
              <el-row type="flex" justify="left" class="dish-row">
                <el-card v-for="dish in shoppingCartList" :key="dish.id" class="shopping-card" shadow="hover">
                  <div style="display: flex">
                    <img :src="baseUrl+dish.logo" class="shopping-image" alt="">
                    <span class="dish-description">{{ dish.description }}</span>
                  </div>
                  <div slot="header" class="clearfix">
                    <span style="font-weight: inherit;font-size: 18px">{{ dish.name }}</span>
                    <el-button plain style="float: right; padding: 0;width: 25px;height: 25px;font-size: 20px"
                               type="text" icon="el-icon-circle-plus-outline" @click="addShoppingCart(dish)"></el-button>
                    <el-button plain style="float: right; padding: 0;width: 25px;height: 25px;font-size: 20px"
                               type="text" icon="el-icon-remove-outline" @click="subShoppingCart(dish)"></el-button>
                    <span style="float: right; margin-right: 20px;font-size: 17px">￥{{ dish.price * dish.goodNum }}</span>
                    <span style="float: right; margin-right: 20px;font-size: 17px">{{ dish.goodNum }} 份</span>
                    <span style="float: right; margin-right: 20px;font-size: 17px">￥{{ dish.price }}</span>
                  </div>
                  <div class="text item">
                  </div>
                </el-card>
              </el-row>
            </el-card>
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane name="second">
        <span slot="label"><i class="el-icon-delete"></i> 释放座位 </span>
        <div v-if="activeName === 'second'">
          <el-card shadow="hover">
            <el-form :model="tableWithOrder" ref="tableWithOrder" size="small" :inline="true" label-width="auto">
              <el-form-item label="桌位名称" prop="tableName">
                <el-input
                  v-model="tableWithOrder.tableName"
                  placeholder="请输入桌位名称"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="桌位大小" prop="tableSize">
                <el-input
                  v-model="tableWithOrder.tableSize"
                  placeholder="请输入最小桌位大小"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="是否空闲" prop="isFree">
                <el-select v-model="tableWithOrder.isFree" placeholder="请选择是否空闲" clearable>
                  <el-option label="空闲" value="1"/>
                  <el-option label="非空闲" value="0"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery(1)">重置</el-button>
              </el-form-item>
            </el-form>
            <el-table v-loading="loading2" :data="tableWithOrderList"
                      :row-class-name="tableRowClassName">
              <el-table-column type="index" width="55" align="center"/>
              <!--      <el-table-column label="桌位id" align="center" prop="tableId" />-->
              <el-table-column label="桌位名称" align="center" prop="tableName"/>
              <el-table-column label="桌位大小" align="center" prop="tableSize"/>
              <el-table-column label="是否空闲" align="center" prop="isFree" :formatter="formatIsFree"/>
              <el-table-column label="最近使用时间" align="center" prop="orderTime"/>
              <el-table-column label="订单状态" align="center" prop="orderStatus">
                <template slot-scope="scope">
                  <el-tag v-for="item in dict.type.order_status"
                          :key="item.value"
                          v-if="item.value === scope.row.orderStatus"
                          :type="item.raw.cssClass">{{ item.label }}
                  </el-tag>
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
                    v-if="scope.row.isFree === 0"
                    size="mini"
                    type="text"
                    icon="el-icon-warning-outline"
                    @click="handleFree(scope.row)"
                    v-hasPermi="['system:table:list']"
                  >释放
                  </el-button>
                  <el-button
                    v-if="scope.row.orderStatus === '0'"
                    size="mini"
                    type="text"
                    icon="el-icon-check"
                    @click="handlePay(scope.row)"
                    v-hasPermi="['system:table:list']"
                  >已付款
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </el-tab-pane>

      <el-tab-pane name="third">
        <span slot="label"><i class="el-icon-user"></i> 设置配送 </span>
        <div v-if="activeName === 'third'">
          <el-card shadow="hover">
            <el-form :model="orderWithOther" ref="queryForm" size="small" :inline="true" label-width="auto">
              <el-form-item label="用户姓名" prop="userId">
                <el-select v-model="orderWithOther.userId" clearable
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
                                v-model="orderWithOther.orderTime"
                                type="date"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                placeholder="请选择订单时间">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="配送员" prop="courierId">
                <el-select v-model="orderWithOther.courierId" clearable
                           filterable placeholder="请选择配送员">
                  <el-option
                    v-for="item in courierList"
                    :key="item.courierId"
                    :label="item.courierName"
                    :value="item.courierId">
                  </el-option>
                </el-select>
              </el-form-item>
<!--              <el-form-item label="订单状态" prop="orderStatus"></el-form-item>
              <el-select v-model="orderWithOther.orderStatus" clearable
                         filterable placeholder="请选择订单状态">
                <el-option
                  v-for="item in dict.type.order_status"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>-->
              <el-form-item>
                <el-button type="primary" style="margin-left: 20px" icon="el-icon-search" size="mini" @click="getOrderWithOtherList">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery(2)">重置</el-button>
              </el-form-item>
            </el-form>
            <!--          3 列表-->
            <el-table v-loading="loading3" :data="orderWithOtherList" :row-class-name="songClass">
              <!--            <el-table-column type="selection" width="55" align="center"/>-->
              <el-table-column type="index" align="center">
              </el-table-column>
              <!--      <el-table-column label="订单id" align="center" prop="orderId" />-->
              <el-table-column label="用户名" align="center" prop="userName"/>
              <el-table-column label="订单时间" align="center" prop="orderTime" width="130">
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
              <el-table-column label="收货人姓名" align="center" prop="name">
                <template slot-scope="scope">
                  <span>{{ scope.row.name || '--' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="配送地址" align="center" prop="userAddress">
                <template slot-scope="scope">
                  <span>{{ scope.row.detailAddress || '--' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="收货人手机号" align="center" prop="phone">
                <template slot-scope="scope">
                  <span>{{ scope.row.phone || '--' }}</span>
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
              <el-table-column label="配送员手机号" align="center" prop="courierPhone">
                <template slot-scope="scope">
                  <span>{{ scope.row.courierPhone || '--' }}</span>
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
                    @click="handleSong(scope.row)"
                    v-hasPermi="['system:order:edit']"
                  >配送
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

          </el-card>


          <!--          配送弹窗-->
          <el-dialog title="配送状态" :visible.sync="open4" width="500px" center>
            <el-form :model="peiSong" ref="orderWithOther" size="small" :inline="true" label-width="auto">
              <div>
                <el-form-item label="配送员" prop="courierId" v-if="flag === null">
                  <el-select v-model="peiSong.courierId" clearable
                             filterable placeholder="请选择配送员">
                    <el-option
                      v-for="item in courierList"
                      :key="item.courierId"
                      :label="item.courierName"
                      :value="item.courierId">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
              <el-form-item label="配送状态" prop="deliveryStatus"></el-form-item>
              <el-select v-model="peiSong.deliveryStatus" clearable
                         filterable placeholder="请选择配送状态">
                <el-option
                  v-for="item in dict.type.delivery_status"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-form-item style="margin: 10px 130px">
                <el-button type="primary" style="margin-left: 20px" icon="el-icon-check" size="mini" @click="submitSong">提交</el-button>
                <el-button icon="el-icon-back" size="mini" @click="open4 = false">返回</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
        </div>
      </el-tab-pane>
    </el-tabs>
    <!--    订单详情-->
    <el-dialog v-loading="loading4" :title="title3" :visible.sync="open3" width="1000px" append-to-body>
      <el-table :data="orderDetailsList" stripe style="width: 100%">
        <el-table-column type="index" label="" align="center"/>
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
import {listCategoryWithDish} from "@/api/system/dish";
import {listTable, listTableWithOrder, setTableFree} from "@/api/system/table";
import {addOrder, listOrderWithOther, updateOrder} from "@/api/system/order";
import {addPayment} from "@/api/system/payment";
import {listUser} from "@/api/system/user";
import {listCourier} from "@/api/system/courier";
import {getDetailByOrderId} from "@/api/system/detail";

export default {
  name: "reception",
  dicts: ['order_status', 'delivery_status'],
  data() {
    return {
      // 3
      // 标识是否设置了配送员
      flag: null,
      peiSong: {},
      open4: false,
      // 订单详情弹出层标题
      title3: "",
      // 是否显示详情弹出层
      open3: false,
      // 订单详情数据
      orderDetailsList: [],
      loading4: false,
      loading3: true,
      userList: [],
      courierList: [],
      orderWithOther: {},
      orderWithOtherList: [],
      // 2
      loading2: true,
      tableWithOrder: {},
      tableWithOrderList: [],
      // 1
      loading1: true,
      isPay: false,
      shoppingDialogVisible: false,
      baseUrl: process.env.VUE_APP_BASE_API,
      dishList: [],
      tableId: "",
      shoppingCartList: [],
      tableList: [],
      activeName: 'first',
    }
  },
  created() {
    this.getDishList();
    this.getTableList();
    // this.getTableWithOrder();
  },
  methods: {
    // 3 表格行的颜色控制
    songClass({row, rowIndex}) {
      // console.log(row);
      if (row.deliveryStatus === '0') {
        return 'warning-row';
      } else if (row.deliveryStatus === '1') {
        return 'info-row';
      } else if (row.deliveryStatus === '2') {
        return 'success-row';
      }
      return '';
    },
    // 3 提交配送
    submitSong() {
      // console.log(this.peiSong);
      if (this.flag === null) {
        if (this.peiSong.courierId === "" || this.peiSong.courierId === null) {
          this.$modal.msgError("请选择配送员");
          return;
        }
        const data = {
          orderId: this.peiSong.orderId,
          courierId: this.peiSong.courierId,
          deliveryStatus: this.peiSong.deliveryStatus
        };
        updateOrder(data).then(response => {
          this.getOrderWithOtherList();
          this.$modal.notifySuccess("设置成功");
          this.open4 = false;
        });
      } else {
        const data = {
          orderId: this.peiSong.orderId,
          deliveryStatus: this.peiSong.deliveryStatus
        };
        updateOrder(data).then(response => {
          this.getOrderWithOtherList();
          this.$modal.notifySuccess("设置配送状态成功");
          this.open4 = false;
        });
      }
    },
    // 3 弹出配送弹窗
    handleSong(row) {
      this.open4 = true;
      this.peiSong = JSON.parse(JSON.stringify(row));
      this.flag = row.courierId;
      // console.log(row);
    },
    // 3 获取订单详情
    handleDetail(row) {
      this.loading4 = true;
      const orderId = row.orderId
      this.title3 = (row.userName || row.tableName) + "的订单详情";
      // console.log(row)
      getDetailByOrderId(orderId).then(response => {
        this.orderDetailsList = response.data;
        this.loading4 = false;
        this.open3 = true;
      });
    },
    // 3 获取配送员列表
    getCourierList() {
      listCourier({pageNum: 1, pageSize: 1000}).then(response => {
        this.courierList = response.rows;
      });
    },
    // 3 获取用户列表
    getUserList() {
      listUser({pageNum: 1, pageSize: 1000}).then(response => {
        this.userList = response.rows;
      });
    },
    // 3 获取外卖订单
    getOrderWithOtherList() {
      listOrderWithOther(this.orderWithOther).then(response => {
        this.orderWithOtherList = response.data;
        this.loading3 = false;
        // console.log(response);
      });
    },
    // 2 付款
    handlePay(row) {
      this.$confirm('确定该订单已付款吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          orderId: row.orderId,
          orderStatus: '1'
        };
        updateOrder(data).then(response => {
          this.getTableWithOrder();
          this.$modal.notifySuccess("付款成功");
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消付款'
        });
      });
    },
    // 2 释放座位
    handleFree(row) {
      this.$confirm('确定释放该座位吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          tableId: row.tableId,
          orderId: row.orderId
        };
        setTableFree(data).then(response => {
            this.getTableWithOrder();
            this.$message({
              type: 'success',
              message: '释放成功!'
            });
          }
        );
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消释放'
        });
      });
    },
    // 2 搜索按钮操作
    handleQuery() {
      this.getTableWithOrder();
    },
    // 2 重置按钮操作
    resetQuery(row) {
      if (row === 1){
        this.resetForm("tableWithOrder");
        this.handleQuery();
      } else if (row === 2) {
        this.resetForm("orderWithOther");
        this.getOrderWithOtherList();
      }
    },
    // 2 判断是否空闲
    formatIsFree(row, column) {
      if (row.isFree === 0) {
        return '非空闲';
      } else if (row.isFree === 1) {
        return '空闲';
      } else {
        return '未知';
      }
    },
    // 2 表格行的颜色控制
    tableRowClassName({row, rowIndex}) {
      // console.log(row);
      if (row.isFree === 0) {
        if (row.orderStatus === '0') {
          return 'warning-row';
        }
        return 'info-row';
      } else if (row.isFree === 1) {
        return 'success-row';
      }
      return '';
    },
    // 2 获取带有订单的桌子列表
    getTableWithOrder() {
      this.loading2 = true;
      listTableWithOrder(this.tableWithOrder).then(response => {
        this.tableWithOrderList = response.rows;
        this.loading2 = false;
        if (this.tableList.map(item => item.isFree).every(item => item === 0)) {
          this.$modal.msgError("暂时没有空闲的座位哦");
        }
        // console.log(response);
      });
    },
    // 1 提交订单
    submitOrder() {
      // console.log("submitOrder");
      if (this.shoppingCartList.length === 0) {
        this.$modal.msgError("购物车是空的哦");
        // this.shoppingDialogVisible = false;
        return;
      }
      if (this.tableId === "") {
        this.$modal.msgError("给顾客选个座位哦");
        // this.shoppingDialogVisible = false;
        return;
      }
      // console.log(this.shoppingCartList);
      // console.log(this.tableId);
      const orderData = {
        tableId: this.tableId,
        shoppingCartList: this.shoppingCartList
      };
      this.tableList = [];
      // this.dishList = [];
      this.getTableList();
      this.getDishList();
      addOrder(orderData).then(response => {
        this.$modal.msgSuccess("下单成功");
        this.shoppingDialogVisible = false;
        this.shoppingCartList = [];
        // console.log(response);
        if (this.isPay) {
          const orderId = response.msg;
          // console.log("orderId:   "+orderId);
          addPayment({orderId: orderId}).then(response => {
            // console.log(response);
            this.$modal.notifySuccess("客户已付款成功");
          });
        } else {
          this.$modal.notifyError("客户还未付款，提醒用户及时付款哦");
        }
      });
    },
    // 1 获取桌子列表
    getTableList() {
      listTable().then(response => {
        this.tableList = response.rows;
        if (this.tableList.map(item => item.isFree).every(item => item === 0)) {
          this.$modal.msgError("暂时没有空闲的座位哦");
        }
        // console.log(response);
      });
    },
    // 1 获取菜品列表
    getDishList() {
      listCategoryWithDish().then(response => {
        this.dishList = response;
        this.loading1 = false;
        // console.log(response);
      });
    },
    // 1 添加购物车
    addShoppingCart(dish) {
      if (this.shoppingCartList.map(item => item.id).includes(dish.id)) {
        // 页面上的数量不会变化
        // this.shoppingCartList.map(item => {
        //   if (item.id === dish.id) {
        //     item.goodNum++;
        //   }
        // });

        // 找到菜品在购物车列表中的索引
        const index = this.shoppingCartList.findIndex(item => item.id === dish.id);
        // console.log(index);
        // 直接增加该菜品的数量
        if (index !== -1) {
          // this.shoppingCartList[index].goodNum++;
          let updatedItem = {...this.shoppingCartList[index]};
          updatedItem.goodNum++;

          // 先删除
          this.shoppingCartList.splice(index, 1);
          // 再添加
          this.shoppingCartList.push(updatedItem);
          this.$modal.notifySuccess("添加购物车成功");

        }
        return;
      }
      this.$modal.notifySuccess("添加购物车成功");
      dish.goodNum = 1;
      this.shoppingCartList.push(dish);
      // console.log("addShoppingCart");
      // console.log(dish);
    },
    // 1 减少购物车
    subShoppingCart(dish) {
      if (this.shoppingCartList.map(item => item.id).includes(dish.id)) {
        // this.shoppingCartList.map(item => {
        //   if (item.id === dish.id) {
        //     item.goodNum--;
        //   }
        // });
        // 找到菜品在购物车列表中的索引
        const index = this.shoppingCartList.findIndex(item => item.id === dish.id);
        // 直接增加该菜品的数量
        if (index !== -1 && this.shoppingCartList[index].goodNum > 0) {
          // this.shoppingCartList[index].goodNum--;
          let updatedItem = {...this.shoppingCartList[index]};
          updatedItem.goodNum--;

          // 先删除
          this.shoppingCartList.splice(index, 1);
          if (updatedItem.goodNum > 0) {
            // 再减少
            this.shoppingCartList.push(updatedItem);
            this.$modal.notifyWarning("减少购物车成功");
          }
        }
      }
    },
    // 1 清空购物车
    clearShoppingCart() {
      this.shoppingCartList = [];
      this.$modal.msgWarning("清空购物车成功");
    },
    // 顶部点击tab触发
    tabClick(tab, event) {
      // console.log(tab, event);
      if (tab.index === "0") {
        this.loading1 = true;
        this.getTableList();
        this.getDishList();
      } else if (tab.index === "1") {
        this.loading2 = true;
        this.getTableWithOrder();
      } else if (tab.index === "2") {
        this.getUserList();
        this.getCourierList();
        this.getOrderWithOtherList();
      }
    },
  }
}
</script>

<style scoped lang="scss">
::v-deep .el-form-item--small .el-form-item__label {
  font-size: 16px;
}

::v-deep .el-table th.el-table__cell > .cell {
  font-size: 16px;
}

::v-deep .el-table .fixed-width .el-button--mini {
  font-size: 16px;
}

::v-deep .el-table--enable-row-transition .el-table__body td.el-table__cell {
  font-size: 20px;
}

::v-deep .el-table .warning-row {
  background: oldlace;
}

::v-deep .el-table .info-row {
  background: #f4f4f5;
}

::v-deep .el-table .success-row {
  background: #f0f9eb;
}

::v-deep .el-select-dropdown__item {
  font-size: 16px;
}

::v-deep .el-input__inner {
  font-size: 18px;
}

::v-deep .el-select-dropdown__item.hover {
  font-size: 18px;
}

.shopping-card {
  width: 100%;
  margin: 5px 5px 5px 5px;
}

.shopping-image {
  border-radius: 10px;
  width: 60px;
  height: 60px;
}

.recep-tabs {
  margin: 5px 5px 5px 5px;
}

.order-tabs {
  overflow-y: auto;
  width: 77%;
  height: 80vh;
  margin-right: 10px;
}

.dish-image {
  border-radius: 10px;
  width: 150px;
  height: 150px;
}

.dish-card {
  width: 300px;
  margin: 5px 5px 5px 5px;
}

.dish-row {
  display: flex;
  flex-wrap: wrap;
}


.dish-description {
  //overflow: hidden;
  //text-overflow: ellipsis;
  //white-space: nowrap;
  text-indent: 1em;
  margin: 10px 10px;
  font-size: 17px;
  color: #606266;
  line-height: 25px;
  text-align: left;
  vertical-align: top;
}
</style>

<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <div class="grid-content bg-purple">
          <!-- 首页user信息 -->
          <el-card shadow='hover' v-loading="loading4">
            <div class="userCard">
              <userAvatar/>
              <div class="userInfo">
                <p class="important-font">{{ user.nickName }}</p>
                <p class="secondary-font">{{ this.roleGroup }}</p>
              </div>
            </div>
            <div class="login-info">
              上次登录ip: {{ user.loginIp }}
            </div>
            <div class="login-info">
              上次登录时间: {{ this.lastLoginDate }}
            </div>
          </el-card>
          <!-- 首页表格 -->
          <el-card shadow='hover' class="tableInfo">
            <div slot="header">
              <span class="important-font">热门菜品 Top 6</span>
            </div>
            <div>
              <el-table
                v-loading="loading2"
                :data="tableData"
                stripe
                style="width: 100%">
                <el-table-column
                  type="index"
                  label=""
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="categoryName"
                  label="菜系"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="dishName"
                  label="菜名"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="dishPrice"
                  label="单价"
                  align="center"
                >
                  <template slot-scope="scope">
                    ￥{{ scope.row.dishPrice }}
                  </template>
                </el-table-column>
                <el-table-column
                  prop="count"
                  label="共计卖出"
                  align="center"
                >
                  <template slot-scope="scope">
                    {{ scope.row.count }} 份
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="16">
        <!-- 订单信息 -->
        <div class="num">
          <el-card shadow='hover' v-loading="loading3" v-for="item in countData" :key="item.name" :body-style="{ display: 'flex',padding: 0 }" class="OrderCard">
            <i class="icon" :class="'el-icon-'+item.icon" :style="{ background: item.color}"></i>
            <div>
              <p class="important-font">￥{{ item.value }}</p>
              <p class="secondary-font">{{ item.name }}</p>
            </div>
          </el-card>
        </div>
        <!-- 柱状图  shadow='hover'-->
        <el-card style="height: 400px" v-loading="loading1">
          <div style="height:400px;" ref="barEcharts" v-if="categoryList.length!==0 && todayCategory.length!==0 &&yesterdayCategory.length!==0">{{ initBarEcharts() }}</div>
        </el-card>
        <div class="num graph">
          <!--          <el-card style="width: 34%;height: 265px;marginRight: 1%">
                      <div style="width: 100%;height: 265px;" ref="pieEcharts">{{ initPieEcharts() }}</div>
                    </el-card> shadow='hover'-->
          <el-card>
            <div>
              <el-calendar v-model="value"></el-calendar>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import userAvatar from "@/views/system/user/profile/userAvatar.vue";
import {getUserProfile} from "@/api/system/user";
import {getDishTop} from "@/api/system/dish";
import {getTopData} from "@/api/system/order";
import {listCategory} from "@/api/system/category";
import {getYesterdayCategorySubtotal, getTodayCategorySubtotal} from "@/api/system/payment";

export default {
  name: "Index",
  components: {userAvatar},
  data() {
    return {
      loading4: true,
      loading3: true,
      loading2: true,
      loading1: true,
      lastLoginDate: '',
      todayCategory: [],
      yesterdayCategory: [],
      user: {},
      value: new Date(),
      tableData: [],
      categoryList: [],
      countData: [
        {
          name: '今日支付订单',
          value: 0,
          icon: 'success',
          color: '#2ec7c9'
        },
        {
          name: '本月支付订单',
          value: 0,
          icon: 'star-on',
          color: '#ffb980'
        },
        {
          name: '本月取消订单',
          value: 0,
          icon: 's-goods',
          color: '#5ab1ef'
        }
      ]
    }
  },
  created() {
    this.getUser();
    this.getDishTop();
    this.getTopData();
    this.getCategoryList();
    this.getCategorySubtotal();
  },
  methods: {
    getCategorySubtotal() {
      getTodayCategorySubtotal().then(response => {
        this.todayCategory = response.data;
        this.loading1 = false;
        // console.log(this.todayCategory);
      });
      getYesterdayCategorySubtotal().then(response => {
        this.yesterdayCategory = response.data;
        this.loading1 = false;
        // console.log(this.yesterdayCategory);
      });
    },
    // 获取菜品分类
    getCategoryList() {
      listCategory().then(response => {
        this.categoryList = response.rows.map(item => item.categoryName)
        // console.log(this.categoryList);
      });
    },
    // 获取首页卡片数据
    getTopData() {
      getTopData().then(response => {
        this.countData[0].value = response.data[0] || 0;
        this.countData[1].value = response.data[1] || 0;
        this.countData[2].value = response.data[2] || 0;
        this.loading3 = false;
      });
    },
    // 获取热门菜品
    getDishTop() {
      getDishTop().then(response => {
        // console.log(response.data);
        this.tableData = response.data;
        this.loading2 = false;
      });
    },
    // 获取用户信息
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.roleGroup = response.roleGroup;
        this.postGroup = response.postGroup;
        const date = new Date(this.user.loginDate);
        this.lastLoginDate = date.toLocaleString('zh-CN', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
          hour: '2-digit',
          minute: '2-digit',
          second: '2-digit'
        });
        this.loading4 = false;
      });
    },
    //柱状图
    initBarEcharts() {
      // 新建一个promise对象
      let p = new Promise((resolve) => {
        resolve()
      })
      //然后异步执行echarts的初始化函数
      p.then(() => {
        //	此dom为echarts图标展示dom
        let myChart = echarts.init(this.$refs.barEcharts)
        let option = {
          title: {
            text: '销售额表'
          },
          tooltip: {},
          legend: {
            data: ['今日销售额', '昨日销售额']
          },
          xAxis: {
            data: this.categoryList
          },
          yAxis: {},
          series: [
            {
              name: '今日销售额',
              type: 'bar',
              data: this.todayCategory.map(item => item.subtotal)
            },
            {
              name: '昨日销售额',
              type: 'bar',
              data: this.yesterdayCategory.map(item => item.subtotal)
            }
          ]
        }
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
      })
    },
    //饼图
    initPieEcharts() {
      let p = new Promise((resolve) => {
        resolve()
      })
      //然后异步执行echarts的初始化函数
      p.then(() => {
        let myChart = echarts.init(this.$refs.pieEcharts);
        let option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '0%',
            left: 'left'
          },
          series: [
            {
              name: '订单信息',
              type: 'pie',
              radius: ['20%', '65%'],
              avoidLabelOverlap: false,
              label: {
                show: false,
                position: 'left'
              },
              labelLine: {
                show: false,
              },
              data: [
                {value: 1048, name: '成交订单量'},
                {value: 735, name: '退款订单量'},
                {value: 580, name: '浏览量'},
                {value: 484, name: '加购量'},
                {value: 300, name: '预购量'}
              ]
            }
          ]
        }
        myChart.setOption(option);
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.icon {
  width: 120px;
  height: 120px;
  border-radius: 10%;
  text-align: center;
  line-height: 120px;
}

.el-card__body {
  padding: 10px;
}

.userCard {
  height: 180px;
  display: flex;
  border-bottom: 2px solid #DCDFE6;
  border-radius: 2px;
}

.userInfo {
  width: auto;
  padding: 6% 0 0 6%;
}

.important-font {
  margin-left: 15px;
  font-weight: 900;
  font-size: 25px;
}

.secondary-font {
  margin-left: 15px;
  color: #909399;
}

.login-info {
  padding-left: 5px;
  height: 25px;
  text-align: left;
  color: #909399;
}

.tableInfo {
  margin: 20px 0 0 0;
}

.OrderCard {
  margin: 0 0 30px 30px;
  border: #DCDFE6 2px solid;
  border-radius: 10px;

  i {
    width: 30%;
    line-height: 120px;
    font-size: 30px;
    color: #fff
  }

  div {
    width: 300px;
  }
}

.el-card {
  border: none;
  margin-top: 5px;
}

.num {
  display: flex;
  flex-wrap: wrap;
}

.graph {
  margin: 15px 0 0 0;
}

.el-calendar {
  //height: 265px;
}

.num {
  margin-top: 15px;
}
</style>

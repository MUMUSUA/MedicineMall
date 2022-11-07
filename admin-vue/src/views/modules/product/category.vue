<template>

  <div>


    <el-switch  v-model="draggable" active-text="开启拖拽" inactive-text="关闭拖拽"></el-switch>

    <el-button v-if="draggable" type="success" @click="batchSave" icon="el-icon-check" circle style='position: absolute;left:250px;top:10px;'></el-button>

    <el-button type="primary" style='position: absolute;right:180px;top:10px;' @click="append">添加分类<i class="el-icon-circle-plus el-icon--right"></i></el-button>

    <el-button type="danger"   @click="batchDelete" :disabled="Nodes<=0" style='position: absolute;right:40px;top:10px;' >批量删除<i class="el-icon-delete el-icon--right"></i></el-button>
       
<br/><br/>
    <el-input
      placeholder="输入关键字进行过滤"
      v-model="filterText">
    </el-input>
    <br/><br/>
  <el-tree :data="menus"  @click="getCategories()" :props="defaultProps" 
  class="filter-tree"
  show-checkbox
  node-key="catId"
  :default-expanded-keys="expandedKey"
  :expand-on-click-node="false"
  :filter-node-method="filterNode"
  :draggable="draggable"
  @check-change="handleCheckChange"
  :allow-drop="allowDrop"
  ref="tree">
    <span class="custom-tree-node" slot-scope="{ node, data }">
           
        <span>
          <el-button
          v-if="node.level<=2"
            type="text"
            size="mini"
            @click="() => append(data)">
            <i class="el-icon-circle-plus"></i>
          </el-button>
          <span>{{ node.label }}</span>
          <el-button
            type="text"
            size="mini"
            @click="() => edit(data)">
            <i class="el-icon-edit"></i>
          </el-button>

          <el-button
            v-if="node.childNodes.length==0"
            type="text"
            size="mini"
            @click="() => remove(node, data)">
            <i class="el-icon-delete"></i>
          
          </el-button>
        </span>
      </span>
  </el-tree>

  <el-dialog
  :title="title"
  :visible.sync="dialogVisible"
  width="30%"
  :close-on-click-modal="false"
  >
  <el-form :model="category">
    <el-form-item label="分类名称" >
      <el-input v-model="category.name" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="图标" >
      <el-input v-model="category.icon" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="计量单位" >
      <el-input v-model="category.productUnit" autocomplete="off"></el-input>
    </el-form-item>


  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="submitType">确 定</el-button>
  </span>
</el-dialog>


  </div>
</template>

<script>
export default {
    data() {
      return {
        draggable: false,
        filterText: '',
        title: "",
        dialogType:"",
        category:{
                  name: "",
                  parentCid: 0,
                  catLevel: 0,
                  showStatus: 1,
                  sort: 0,
                  catId: null,
                  icon: "",
                  productUnit: ""

        },
        dialogVisible: false,
        Nodes: 0,
        menus: [],
        expandedKey: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      };
    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
        
      }
    },
    methods: {
      getCategories () {
        this.dataListLoading = true
        this.$http({       
          url: this.$http.adornUrl('/product/category/list/tree'),
          method: 'get',

        }).then(({ data }) => {
        console.log("成功获取到分类数据...", data.data);
        this.menus = data.data;
      })
      },
      handleCheckChange(data, checked, indeterminate) {
        		//获取所有选中的节点 start
                this.Nodes = this.$refs.tree.getCheckedNodes();
                console.log("selected node",this.Nodes);
            },
      filterNode(value, menus) {
        console.log("value监听到的搜索：",value);
        console.log("一开始的data:",menus);
        if (!value) return true;
        return menus.name.indexOf(value) !== -1;
      },

      submitType() {
      if (this.dialogType == "add") {
        this.appendCategory();
      }
      if (this.dialogType == "edit") {
        this.editCategory();
      }
    },
      append(data) {
        console.log("append",data);
        this.dialogType = "add";
        this.title="添加分类"
        this.dialogVisible= true;

        if(typeof(data.catId) == "undefined"){
        this.category.parentCid=0;
        this.category.catLevel=1;
        }else{
        this.category.parentCid=data.catId;
        this.category.catLevel=data.catLevel*1+1;
        }

        this.category.catId = null;
        this.category.name = "";
        this.category.icon = "";
        this.category.productUnit = "";
        this.category.sort = 0;
        this.category.showStatus = 1;
      },

      //添加三级分类
      appendCategory() {
      console.log("提交的三级分类数据", this.category);
      if(this.category.name==null || this.category.name.length==0 ){
        this.$message.error('分类名称不可为空');
      }
else{
      this.$http({
        url: this.$http.adornUrl("/product/category/save"),
        method: "post",
        data: this.$http.adornData(this.category, false)
      }).then(({ data }) => {
        this.$message({
          message: "菜单保存成功",
          type: "success"
        });
        //关闭对话框
        this.dialogVisible = false;
        //刷新出新的菜单
        this.menus=this.getCategories();
        //设置需要默认展开的菜单
        this.expandedKey = [this.category.parentCid];
      });
    }
    }
      
      ,
      remove(node, data) {
          var ids=[data.catId]

          this.$confirm('请确认是否删除菜单['+data.name+']?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/product/category/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
                this.$message({
                message: '删除成功',
                type: 'success'
        });
            this.menus=this.getCategories();
            this.expandedKey=[node.parent.data.catId];
          })
        console.log("delete",node,data);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });

      },

      edit(data){
        console.log("要修改的数据：",data);
        this.dialogType = "edit";
        this.title = "修改分类";
        this.dialogVisible = true;
        
        //获取当前节点最新数据
        this.$http({
        url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
        method: 'get',
        }).then( ({data}) =>{
          //请求成功
        console.log("要回显的数据", data);
        this.category.name = data.category.name;
        this.category.catId = data.category.catId;
        this.category.icon = data.category.icon;
        this.category.productUnit = data.category.productUnit;
        this.category.parentCid = data.category.parentCid;
        // this.category.catLevel = data.category.catLevel;
        // this.category.sort = data.category.sort;
        // this.category.showStatus = data.category.showStatus;


        } )
      },   
      //更改分类详情
      editCategory(){
        //解构
        var {catId,name,icon,productUnit} =this.category;
        this.$http({
        url: this.$http.adornUrl('/product/category/update'),
        method: 'post',
        data: this.$http.adornData({catId,name,icon,productUnit}, false)
         }).then(({data}) => {  
          this.$message({
          message: "分类修改成功",
          type: "success"
        });
        //关闭对话框
        this.dialogVisible = false;
        //刷新出新的菜单
        this.menus=this.getCategories();
        //设置需要默认展开的菜单
        this.expandedKey = [this.category.parentCid];
         })
      }
    ,  
    //拖拽
    allowDrop(draggingNode, dropNode, type) {
      console.log("allowDrop:",draggingNode.data, dropNode.data.name, type);
      if(type=="inner" && draggingNode.data.catLevel == 3){
        return false;
      }  
      
      if(draggingNode.data.catLevel==dropNode.data.catLevel && draggingNode.data.parentCid ==dropNode.data.parentCid)
        return true;

        else
        return false;
      },    
      //批量删除
      batchDelete() {
      let catIds = [];
      let names=[];
      let checkedNodes = this.$refs.tree.getCheckedNodes();
      console.log("被选中的元素", checkedNodes);
      for (let i = 0; i < checkedNodes.length; i++) {
        catIds.push(checkedNodes[i].catId);
        names.push(checkedNodes[i].name)
      }
      this.$confirm(`是否批量删除【${names}】菜单?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(catIds, false)
          }).then(({ data }) => {
            this.$message({
              message: "菜单批量删除成功",
              type: "success"
            });
            this.getCategories();
          });
        })
        .catch(() => {});
    },
    },

   
    created(){
        this.getCategories();
    }
  };
</script>

<style>

</style>
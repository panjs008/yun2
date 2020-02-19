<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function () {
       /* $('#cc').combotree({
            url : 'emkProductTypeController.do?setPOfficeInfo&selfId=${emkProductTypePage.id}',
            panelHeight: 200,
            width: 157,
            onClick: function(node){
                $("#officeId").val(node.id);
            }
        });*/
        gridLoadSuccess();
    });

    /**
     * 展开所有节点
     */
    function expandAll() {
        var node = $('#orgtree').tree('getSelected');
        if (node) {
            $('#orgtree').tree('expandAll', node.target);
        } else {
            var root=$('#orgtree').tree('getRoot');//获取根节点
            $('#orgtree').tree('expand', root.target);//展开根节点
            //$('#orgtree').tree('expandAll');
        }
    }

    function gridLoadSuccess(data) {
        $('#orgtree').tree({
            animate: true,
            //url: 'departController.do?getOrgTree&departid='+$("#departid").val(),
            url : 'emkProductTypeController.do?setPOfficeInfo&selfId=${emkProductTypePage.id}',
            onClick: function (node) {
                if ($('#orgtree').tree('isLeaf', node.target) || $('#orgtree').tree('expand', node.target)) {
                    $('#emkProductList').datagrid("reload", {"proType": node.id});
                    //alert(node.id);
                    //var depart = $('#orgtree').tree('getSelected');
                    //$("#showDepart").text(depart.text);
                }
            },
            onLoadSuccess: function (node) {
                expandAll();
            }
        });
    }
</script>
<div class="easyui-layout" fit="true">
 <div region="west" style="width: 200px;" title="商品类别" split="true">
  <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false">
   <ul id="orgtree">
   </ul>
  </div>
 </div>
  <div region="center" style="padding:0px;border:0px">
      <t:datagrid name="emkProductList" checkbox="true" singleSelect="true"  pageSize="20" sortOrder="asc" sortName="orgCode,proNum"   pagination="true" fitColumns="false" title="商品信息查询" actionUrl="emkProductController.do?datagrid" idField="id" fit="true" btnCls="bootstrap" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <%--  <t:dgCol title="款号"  field="hsCode"  queryMode="single"  width="120"></t:dgCol>
       <t:dgCol title="款号名称"  field="hsName"  queryMode="single"  width="150"></t:dgCol>--%>
          <t:dgCol title="商品类别"  frozenColumn="true" field="proTypeName"  queryMode="single"  width="70"></t:dgCol>
          <t:dgCol title="商品名称" frozenColumn="true"  field="proZnName"  query="true" queryMode="single"  width="140"></t:dgCol>
          <t:dgCol title="商品编号" frozenColumn="true" field="proNum" query="true"  queryMode="single"  width="110"></t:dgCol>
          <t:dgCol title="条码"  query="true" field="barCode"  queryMode="single"  width="80"></t:dgCol>
          <t:dgCol title="助记码" query="true"  field="proZjm"  queryMode="single"  width="80"></t:dgCol>

          <%--<t:dgCol title="比例"  field="precent"  queryMode="single"  width="120"></t:dgCol>--%>
          <%--<t:dgCol title="单件用量"  field="yongliang"  queryMode="single"  width="120"></t:dgCol>--%>
       <%--<t:dgCol title="英文名称"  field="proEnName"  query="true" queryMode="single"  width="120"></t:dgCol>--%>
          <t:dgCol title="规格"  field="standards"  query="true" queryMode="single"  width="100"></t:dgCol>
          <t:dgCol title="型号"  field="brand" query="true"  queryMode="single"  width="140"></t:dgCol>
          <t:dgCol title="单位"  field="unit"  queryMode="single" dictionary="units"  width="50"></t:dgCol>
          <t:dgCol title="零售价"  field="sellPrice"  queryMode="single"  width="60"></t:dgCol>
          <t:dgCol title="成本价"  field="costPrice"  queryMode="single"   width="60"></t:dgCol>
          <c:forEach var="list" items="${categoryEntities}" varStatus="status">
              <c:if test="${list.code eq 'a01a01a08'}">
                  <t:dgCol title="${list.name}"  field="${list.code}" queryMode="single" replace="不积分_0,按金额积分_1,按数量积分_2" query="${list.queryed eq 0 ? 'true':'false'}"  width="80"></t:dgCol>
              </c:if>
              <c:if test="${list.code eq 'a01a01a10'}">
                  <t:dgCol title="${list.name}"  field="${list.code}" queryMode="single" replace="无提成_0,按销售额提成_1,按固定金额提成_2,按利润提成_3" query="${list.queryed eq 0 ? 'true':'false'}"  width="80"></t:dgCol>
              </c:if>
              <c:if test="${list.code ne 'a01a01a08' && list.code ne 'a01a01a10'}">
                  <t:dgCol title="${list.name}"  field="${list.code}" queryMode="single" query="${list.queryed eq 0 ? 'true':'false'}"  width="${list.column_type eq '4' ? '210':(list.column_type eq '0' || list.column_type eq '2') ? '50':'100'}"></t:dgCol>
              </c:if>
          </c:forEach>

          <%--<t:dgCol title="备注"  field="remark"  queryMode="single"  width="100"></t:dgCol>--%>

          <%--<t:dgCol title="增值税率(%)"  field="zzVal"  queryMode="single"  width="80"></t:dgCol>--%>
          <%--<t:dgCol title="退税率(%)"  field="tsVal"  queryMode="single"  width="80"></t:dgCol>--%>

       <%--<t:dgCol title="英文描述"  field="remarkEn"  queryMode="single"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="中文描述"  field="remarkZn"  queryMode="single"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="商品备注" hidden="true" field="proRemark"  queryMode="single"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" operationCode="delete" url="emkProductController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkProductController.do?goAdd&type=0&winTitle=录入商品" funname="add" width="1100" height="550"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkProductController.do?goUpdate&winTitle=编辑商品" funname="update" width="1100" height="550"></t:dgToolBar>
          <t:dgToolBar title="复制" icon="fa fa-copy" operationCode="edit" url="emkProductController.do?goUpdate2&winTitle=复制新增商品" funname="update" width="1100" height="550"></t:dgToolBar>

          <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkProductController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <%--<t:dgToolBar title="查看" icon="fa fa-search" url="emkProductController.do?goUpdate" funname="detail" width="800" height="400"></t:dgToolBar>--%>
          <%--<t:dgToolBar title="录入商品类别" icon="fa fa-plus" operationCode="add" url="emkProductTypeController.do?goAdd" funname="add"></t:dgToolBar>--%>
          <t:dgToolBar title="商品库存" icon="fa fa-shopping-cart" operationCode="productKc" url="emkProductController.do?goUpdateStorage&winTitle=录入库存" funname="update" width="1100" height="550"></t:dgToolBar>
          <t:dgToolBar title="录入商品类别" icon="fa fa-plus"   funname="addProductType" width="1100" height="550"></t:dgToolBar>
          <t:dgToolBar title="编辑商品类别" icon="fa fa-edit"  funname="editPoductType"></t:dgToolBar>
          <t:dgToolBar title="删除商品类别" icon="fa fa-remove" funname="delPoductType"></t:dgToolBar>

          <t:dgToolBar title="导入" operationCode="imp" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
          <t:dgToolBar title="表头列"  icon="fa fa-gear" funname="setHeader"></t:dgToolBar>
          <t:dgToolBar title="模板下载" icon="fa fa-arrow-circle-o-down" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/product/product/emkProductList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function addProductType(){
     $.dialog({
         content: 'url:emkProductTypeController.do?for&goAdd',
         zIndex: getzIndex(),
         title : '添加商品类别',
         cache:false,
         lock : true,
         width: 500,
         height: 300,
         ok: function(){
             iframe = this.iframe.contentWindow;
             saveObj();
             location.reload();
         },
         cancelVal: '关闭',
         cancel: function(){
         },
     });
 }
 function editPoductType(){
     var node = $('#orgtree').tree('getSelected');
     if(node == null) {
         layer.msg('请选择商品类别');
         return;
     }
     $.dialog({
         content: 'url:emkProductTypeController.do?for&goUpdate&id='+node.id,
         zIndex: getzIndex(),
         title : '编辑商品类别',
         cache:false,
         lock : true,
         width: 500,
         height: 300,
         ok: function(){
             iframe = this.iframe.contentWindow;
             saveObj();
             location.reload();
         },
         cancelVal: '关闭',
         cancel: function(){
         },
     });
 }
 function delPoductType(){
     var node = $('#orgtree').tree('getSelected');
     if(node == null) {
         layer.msg('请选择商品类别');
         return;
     }
     $.dialog.confirm('您是否确定删除商品类别?', function(r) {
         var index = layer.load(1, {
             skin:"layui-layer-sys1",
             shade: [0.1,'#fff'] //0.1透明度的白色背景
         });
         if (r) {
             $.ajax({
                 url : "emkProductController.do?doProductTypeDel&id="+node.id,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     layer.close(index);
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         location.reload();
                     }

                 }
             });
         }
     });
 }
 function setHeader(){
     $.dialog({
         content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A01',
         zIndex: getzIndex(),
         title : '列配置',
         cache:false,
         lock : true,
         width: 500,
         height: 500,
         cancelVal: '关闭',
         cancel: function(){
             location.reload();
         },
     });
 }

 function ImportXls() {
     $.dialog({
         content: 'url:emkProductController.do?upload',
         zIndex: getzIndex(),
         title : '导入商品信息',
         cache:false,
         lock : true,
         width: 600,
         height: 300
     });

 }

//导出
function ExportXls() {
	JeecgExcelExport("emkProductController.do?exportXls","emkProductList");
}

//模板下载
function ExportXlsByT() {
    JeecgExcelExport("emkProductController.do?exportXlsByT","emkProductList");
    //window.open("${webRoot}/context/商品信息导入模板.xls");
}

 </script>
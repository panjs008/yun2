<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hmCategoryList" checkbox="false" singleSelect="true"  pageSize="20" pagination="true" fitColumns="true" title="查询" sortOrder="asc" sortName="orderNum" actionUrl="hmCategoryController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="上级code"  field="parentCode"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="字段名称"  field="fname" query="true"    queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="显示名称"  field="name" query="true"    queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="字段编码"  field="code"    queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="字段类型"  field="columnType"  replace="短文本_0,长文本_1,整数_2,日期_3,文本域_4,下拉框_5,浮点型_6,字典_7"  queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="序号"  field="orderNum"    queryMode="single"  width="60"></t:dgCol>
      <t:dgCol title="是否必填"  field="required" formatterjs="fmtype"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="是否查询"  field="queryed"  formatterjs="fmtype2" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="是否显示"  field="isShow"  formatterjs="fmtype3"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title=""  field="type"  hidden="true" queryMode="single"  width="70"></t:dgCol>

      <t:dgCol title="操作" field="opt" width="220"></t:dgCol>
      <t:dgFunOpt funname="doPre(code,type)" title="往前" urlclass="ace_button" urlfont="fa fa-arrow-circle-right"></t:dgFunOpt>
      <t:dgFunOpt funname="doNext(code,type)" title="往后" urlclass="ace_button" urlfont="fa fa-arrow-circle-left"></t:dgFunOpt>
      <t:dgFunOpt funname="doTop(code,type)" title="置顶" urlclass="ace_button" urlfont="fa fa-arrow-circle-up"></t:dgFunOpt>
      <t:dgFunOpt funname="doBottom(code,type)" title="置底" urlclass="ace_button" urlfont="fa fa-arrow-circle-down"></t:dgFunOpt>

      <t:dgToolBar title="录入" icon="fa fa-plus" url="hmCategoryController.do?goAdd" funname="add"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" url="hmCategoryController.do?goUpdate" funname="update"></t:dgToolBar>
      <t:dgToolBar title="删除"  icon="fa fa-remove" url="hmCategoryController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="fa fa-search" url="hmCategoryController.do?goUpdate" funname="detail"></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
<div id="tempSearchColums" style="display: none;width:120px">
    <div name="searchColums">
        <select id="parentCode" name="parentCode" datatype="*" >
            <option value="A01A01">商品信息表</option>
            <option value="A01A02">薪酬信息预设表</option>
            <option value="A01A03">采购入库单表</option>
            <option value="A01A09">采购入库明细列配置</option>
            <option value="A01A14">采购订单明细列配置</option>
            <option value="A01A05">订单管理表</option>
            <option value="A01A07">销售开单表</option>
            <option value="A01A10">订单明细列配置</option>
            <option value="A01A11">开单明细列配置</option>
            <option value="A01A12">库存明细列配置</option>
            <option value="A01A18">组合明细列配置</option>
            <option value="A01A13">报价单表</option>
            <option value="A01A15">报价明细列配置</option>
            <option value="A01A16">退货单</option>
            <option value="A01A17">退货单明细列配置</option>
            <option value="A01A19">修改库存列配置</option>

        <%--<option value="A01A06">订单明细表</option>--%>
        </select>
    </div>
</div>
 <script src = "webpage/com/hm/rsgl/category/hmCategoryList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#hmCategoryListtb").find("div[name='searchColums']").find("form#hmCategoryListForm").append($("#tempSearchColums div[name='searchColums']").html());
     $("#tempSearchColums").html('');
     $("#parentCode").css("width","120px");
 });
 function fmtype(val,row,index){
     var s1;
     if(row.fname == '商品名称'){
         s1 = '<input type="checkbox" checked value="0" disabled onchange="doUpdateColState(\''+row.id+'\',1,0)"/>';
     }else{
         if(val == 0){
             s1 = '<input type="checkbox" checked value="0" onchange="doUpdateColState(\''+row.id+'\',1,0)"/>';
         }
         if(val == 1){
             s1 = '<input type="checkbox" value="1" onchange="doUpdateColState(\''+row.id+'\',0,0)"/>';
         }
     }

     return s1;
 }
 function fmtype2(val,row,index){
     var s1;
     if(val == 0){
         s1 = '<input type="checkbox" checked value="0" onchange="doUpdateColState(\''+row.id+'\',1,1)"/>';
     }
     if(val == 1){
         s1 = '<input type="checkbox" value="1" onchange="doUpdateColState(\''+row.id+'\',0,1)"/>';
     }
     return s1;
 }
 function fmtype3(val,row,index){
     var s1;
     if(row.fname == '商品名称'){
         s1 = '<input type="checkbox" disabled checked value="0" onchange="doUpdateColState(\''+row.id+'\',1,2)"/>';

     }else{
         if(val == 0){
             s1 = '<input type="checkbox" checked value="0" onchange="doUpdateColState(\''+row.id+'\',1,2)"/>';
         }
         if(val == 1){
             s1 = '<input type="checkbox" value="1" onchange="doUpdateColState(\''+row.id+'\',0,2)"/>';
         }
     }

     return s1;
 }
 function doUpdateColState(id,state,type) {
     var url = "hmCategoryController.do?doUpdateColState&id="+id+"&required="+state;
     if(type == 0){
         url = "hmCategoryController.do?doUpdateColState&id="+id+"&required="+state;
     }
     if(type ==1){
         url = "hmCategoryController.do?doUpdateColState&id="+id+"&queryed="+state;
     }
     if(type ==2){
         url = "hmCategoryController.do?doUpdateColState&id="+id+"&isShow="+state;
     }
     $.ajax({
         url : url,
         type : 'post',
         cache : false,
         data: null,
         success : function(data) {

         }
     });
 }
 function doPre(code,type) {
     $.ajax({
         url : "hmCategoryController.do?doPre&code="+code+"&type="+type,
         type : 'post',
         cache : false,
         data: null,
         success : function(data) {
             var d = $.parseJSON(data);
             if (d.success) {
                 $('#hmCategoryList').datagrid('reload');
             }else{
                 var msg = d.msg;
                 tip(msg);
             }
         }
     });
 }

 function doNext(code,type) {
     $.ajax({
         url : "hmCategoryController.do?doNext&code="+code+"&type="+type,
         type : 'post',
         cache : false,
         data: null,
         success : function(data) {
             var d = $.parseJSON(data);
             if (d.success) {
                 $('#hmCategoryList').datagrid('reload');
             }else{
                 var msg = d.msg;
                 tip(msg);
             }
         }
     });

 }

 function doTop(code,type) {
     $.ajax({
         url : "hmCategoryController.do?doTop&code="+code+"&type="+type,
         type : 'post',
         cache : false,
         data: null,
         success : function(data) {
             var d = $.parseJSON(data);
             if (d.success) {
                 $('#hmCategoryList').datagrid('reload');
             }else{
                 var msg = d.msg;
                 tip(msg);
             }
         }
     });

 }

 function doBottom(code,type) {
     $.ajax({
         url : "hmCategoryController.do?doBottom&code="+code+"&type="+type,
         type : 'post',
         cache : false,
         data: null,
         success : function(data) {
             var d = $.parseJSON(data);
             if (d.success) {
                 $('#hmCategoryList').datagrid('reload');
             }else{
                 var msg = d.msg;
                 tip(msg);
             }
         }
     });

 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'hmCategoryController.do?upload', "hmCategoryList");
}

//导出
function ExportXls() {
	JeecgExcelExport("hmCategoryController.do?exportXls","hmCategoryList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("hmCategoryController.do?exportXlsByT","hmCategoryList");
}

 </script>
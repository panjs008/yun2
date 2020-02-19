<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <%--<t:datagrid name="hmCategoryList" checkbox="false" singleSelect="true"  pageSize="20" pagination="false" fitColumns="false" title="查询" onClick="editRow" sortOrder="asc" sortName="orderNum" actionUrl="hmCategoryController.do?datagrid&parentCode=${param.parentCode}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">--%>
      <t:datagrid name="hmCategoryList" checkbox="false" singleSelect="true"  pageSize="20" pagination="false" fitColumns="false" title="查询"  sortOrder="asc" sortName="orderNum" actionUrl="hmCategoryController.do?datagrid&parentCode=${param.parentCode}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="上级code"  field="parentCode"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="字段名称"  field="fname"  queryMode="single"  width="110"></t:dgCol>
      <%--<t:dgCol title="显示名称"  field="name" query="true"  extendParams="editor:'text'"    queryMode="single"  width="110"></t:dgCol>--%>
          <t:dgCol title="显示名称"  field="name" query="true" queryMode="single"  width="110"></t:dgCol>

          <t:dgCol title="字段编码"  field="code" hidden="true"   queryMode="single"  width="100"></t:dgCol>
      <%--<t:dgCol title="字段类型"  field="columnType"  replace="短文本_0,长文本_1,整数_2,日期_3,文本域_4,下拉框_5,浮点型_6,字典_7"  queryMode="single"  width="80"></t:dgCol>--%>
      <%--<t:dgCol title="序号"  field="orderNum"    queryMode="single"  width="50"></t:dgCol>--%>
      <t:dgCol title="是否必填"  field="required" formatterjs="fmtype"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="是否查询"  field="queryed"  formatterjs="fmtype2" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="是否显示"  field="isShow"  formatterjs="fmtype3"  queryMode="single"  width="70"></t:dgCol>
          <t:dgCol title=""  field="type"  hidden="true" queryMode="single"  width="70"></t:dgCol>

      <%--<t:dgCol title="操作" field="opt" width="220"></t:dgCol>--%>
      <%--<t:dgFunOpt funname="doPre(code)" title="往前" urlclass="ace_button" urlfont="fa fa-arrow-circle-right"></t:dgFunOpt>
      <t:dgFunOpt funname="doNext(code)" title="往后" urlclass="ace_button" urlfont="fa fa-arrow-circle-left"></t:dgFunOpt>
      <t:dgFunOpt funname="doTop(code)" title="置顶" urlclass="ace_button" urlfont="fa fa-arrow-circle-up"></t:dgFunOpt>
      <t:dgFunOpt funname="doBottom(code)" title="置底" urlclass="ace_button" urlfont="fa fa-arrow-circle-down"></t:dgFunOpt>--%>

      <t:dgToolBar title="录入" icon="fa fa-plus" url="hmCategoryController.do?goAdd" funname="add" width="400" height="400"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" url="hmCategoryController.do?goUpdate" funname="update" width="400" height="400"></t:dgToolBar>
      <t:dgToolBar title="上移" icon="fa fa-arrow-circle-left" url="hmCategoryController.do?goUpdate" funname="doPre"></t:dgToolBar>
      <t:dgToolBar title="下移" icon="fa fa-arrow-circle-right" url="hmCategoryController.do?goUpdate" funname="doNext"></t:dgToolBar>
      <t:dgToolBar title="置顶" icon="fa fa-arrow-circle-up" url="hmCategoryController.do?goUpdate" funname="doTop"></t:dgToolBar>
      <t:dgToolBar title="置底" icon="fa fa-arrow-circle-down" url="hmCategoryController.do?goUpdate" funname="doBottom"></t:dgToolBar>

      <%--<t:dgToolBar title="查看" icon="fa fa-search" url="hmCategoryController.do?goUpdate" funname="detail"></t:dgToolBar>--%>

  </t:datagrid>
  </div>
 </div>

 <script src = "webpage/com/hm/rsgl/category/hmCategoryList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     /*$("#hmCategoryListtb").find("div[name='searchColums']").find("form#hmCategoryListForm").append($("#tempSearchColums div[name='searchColums']").html());
     $("#tempSearchColums").html('');*/
     $("#parentCode").css("width","120px");
 });
 function fmtype(val,row,index){
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

 //编辑行
 function editRow(title,addurl,gname){
     var rows=$('#hmCategoryList').datagrid("getChecked");
     if(rows.length==0){
         tip("请选择条目");
         return false;
     }
     for(var i=0;i<rows.length;i++){
         var index= $('#hmCategoryList').datagrid('getRowIndex', rows[i]);
         $('#hmCategoryList').datagrid('beginEdit', index);
     }

 }

 function endEdit(){
     var  editIndex = $('#hmCategoryList').datagrid('getRows').length-1;
     for(var i=0;i<=editIndex;i++){
         if($('#hmCategoryList').datagrid('validateRow', i))
             $('#hmCategoryList').datagrid('endEdit', i);
         else
             return false;
     }
     return true;
 }

 function doPre() {
     var rowsData = $('#hmCategoryList').datagrid('getSelections');
     if (!rowsData || rowsData.length == 0) {
         tip('请选择行');
         return;
     }
     $.ajax({
         url : "hmCategoryController.do?doPre&code="+rowsData[0].code+"&type="+rowsData[0].type,
         type : 'post',
         cache : false,
         data: null,
         success : function(data) {
             var d = $.parseJSON(data);
             if (d.success) {
                 $('#hmCategoryList').datagrid('reload');
                 location.reload();
             }else{
                 var msg = d.msg;
                 tip(msg);
             }
         }
     });
 }

 function doNext() {
     var rowsData = $('#hmCategoryList').datagrid('getSelections');
     if (!rowsData || rowsData.length == 0) {
         tip('请选择行');
         return;
     }
     $.ajax({
         url : "hmCategoryController.do?doNext&code="+rowsData[0].code+"&type="+rowsData[0].type,
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

 function doTop() {
     var rowsData = $('#hmCategoryList').datagrid('getSelections');
     if (!rowsData || rowsData.length == 0) {
         tip('请选择行');
         return;
     }
     $.ajax({
         url : "hmCategoryController.do?doTop&code="+rowsData[0].code+"&type="+rowsData[0].type,
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

 function doBottom() {
     var rowsData = $('#hmCategoryList').datagrid('getSelections');
     if (!rowsData || rowsData.length == 0) {
         tip('请选择行');
         return;
     }
     $.ajax({
         url : "hmCategoryController.do?doBottom&code="+rowsData[0].code+"&type="+rowsData[0].type,
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
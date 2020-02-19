<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function () {
       $('#orgtree').tree({
            animate: true,
            url : 'departController.do?setPFunctionForStaff&selfId=${depart.orgCode}',
            onClick: function (node) {
                if ($('#orgtree').tree('isLeaf', node.target) || $('#orgtree').tree('expand', node.target)) {
                    $('#hmStaffList').datagrid("reload", {"orgCode": node.id});

                }
            }
        });
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
        }
    }
    function gridLoadSuccess (data) {
        var rowsData = $('#hmStaffList').datagrid('getData');
        $.ajax({
            url : "hmStaffController.do?getSessionStaff&r=gdNamesCode",
            type : 'post',
            cache : false,
            success : function(res) {
                var staffSelectArr = res.substring(1,res.length-1).split(",");
                for ( var i = 0; i < rowsData.rows.length; i++) {
                    for(var id in staffSelectArr){
                        if(rowsData.rows[i].gdNamesCode == staffSelectArr[id]){
                            $('#hmStaffList').datagrid('checkRow',i);
                        }
                    }
                }
            }
        });
        /*var staffSelectArr = '${sessionScope.gdNamesCodeS}'.split(",");
        for ( var i = 0; i < rowsData.rows.length; i++) {
            for(var id in staffSelectArr){
                if(rowsData.rows[i].gdNamesCode == staffSelectArr[id]){
                    $('#hmStaffList').datagrid('checkRow',i);
                }
            }
        }*/
    }
    function doCancelSession(data){
        var rowsData = $('#hmStaffList').datagrid('getData');
        $.ajax({
            url : "hmStaffController.do?doCancelSession&r=gdNamesCodeS&workNo="+rowsData.rows[data].gdNamesCode,
            type : 'post',
            cache : false,
            success : function(data) {
            }
        });
    }
</script>
<div class="easyui-layout" fit="true">
 <div region="west" style="width: 180px;" title="组织机构" split="true">
  <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false">
   <ul id="orgtree">
   </ul>
  </div>
 </div>
  <div region="center" style="padding:0px;border:0px">
      <t:datagrid name="hmStaffList" onLoadSuccess="gridLoadSuccess" pageSize="20"  onClick="doCancelSession" checkbox="true" pagination="true" fitColumns="false" title="" actionUrl="hmStaffController.do?datagridG&state=0" idField="id" fit="true" btnCls="bootstrap" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
          <t:dgCol title="工号" query="true"  field="gdNamesCode"  queryMode="single"  width="70"></t:dgCol>
          <t:dgCol title="姓名" query="true"  field="gdNames"  queryMode="single"  width="80"></t:dgCol>
          <t:dgCol title="部门"   field="deptName"  queryMode="single"  width="100"></t:dgCol>
          <t:dgCol title="部门代码"  hidden="true" field="deptCode"  queryMode="single"  width="120"></t:dgCol>
          <t:dgCol title="车间"  field="workName"  queryMode="single"  width="100"></t:dgCol>
          <t:dgCol title="车间代码" hidden="true"  field="workCode"  queryMode="single"  width="120"></t:dgCol>
          <t:dgCol title="班组"  field="groupName"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title="班组代码"  hidden="true"  field="groupCode"  queryMode="single"  width="120"></t:dgCol>
          <t:dgCol title="职务"  field="job"  dictionary="job" queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title="级别"  field="jb"  dictionary="jb" queryMode="single"  width="70"></t:dgCol>
          <t:dgCol title="薪酬类别"  field="xclb"  dictionary="xclb" queryMode="single"  width="80"></t:dgCol>
          <t:dgCol title="性别"  field="sex" dictionary="sex"  queryMode="single"  width="50"></t:dgCol>
          <t:dgCol title="员工类别"  field="yglb" dictionary="yglb" queryMode="single"  width="80"></t:dgCol>
          <t:dgCol title="带工"  field="taker" dictionary="dg"  queryMode="single"  width="80"></t:dgCol>

          <t:dgCol title="固定/临时"  field="workType" dictionary="gdls"  queryMode="single"  width="80"></t:dgCol>
          <t:dgCol title="入职日期"  field="rzDate"  queryMode="single"  width="90"></t:dgCol>
          <t:dgToolBar title="保存"  icon="fa fa-save" funname="saveSession();"></t:dgToolBar>

      </t:datagrid>
</div>
</div>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 function saveSession() {
     var rowsData = $('#hmStaffList').datagrid('getSelections');
     var ids = [];
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].gdNamesCode);
     }
     $.ajax({
         url : "hmStaffController.do?doSaveSession&r=gdNamesCodeS&ids="+ids,
         type : 'post',
         cache : false,
         data: null,
         success : function(data) {
             var d = $.parseJSON(data);
             if (d.success) {
                 var msg = d.msg;
                 tip(msg);
             }
         }
     });
 }


//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkProductController.do?upload', "hmStaffList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkProductController.do?exportXls","hmStaffList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkProductController.do?exportXlsByT","hmStaffList");
}

 </script>
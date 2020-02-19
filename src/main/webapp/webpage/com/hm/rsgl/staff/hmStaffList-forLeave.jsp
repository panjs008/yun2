<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    var checkids = [];

    $(function () {
       $('#orgtree').tree({
            animate: true,
            //url: 'departController.do?getOrgTree&departid='+$("#departid").val(),
            url : 'departController.do?setPFunctionForStaff&selfId=${depart.orgCode}',
            onClick: function (node) {
                if ($('#orgtree').tree('isLeaf', node.target) || $('#orgtree').tree('expand', node.target)) {
                    $('#hmStaffList').datagrid("reload", {"orgCode": node.id});
                    //var depart = $('#orgtree').tree('getSelected');
                    //$("#showDepart").text(depart.text);
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
            //$('#orgtree').tree('expandAll');
        }
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
      <t:datagrid name="hmStaffList"  pagination="true" fitColumns="false" title="" actionUrl="hmStaffController.do?datagrid&state=0" idField="id" fit="true" btnCls="bootstrap" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
          <t:dgCol title="工号"  query="true" field="workNo"  queryMode="single"  width="70"></t:dgCol>
          <t:dgCol title="姓名"  query="true"  field="realName"  queryMode="single"  width="80"></t:dgCol>
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
          <t:dgCol title=""  field="zzDate" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="telphone" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="idCard" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="birthDay" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="workYear" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="sleepDays" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="mz" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="xueli" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="housePhone" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="jjlxr" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="zzDays" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="zzmm" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="isNs" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="hjszd" hidden="true"  queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title=""  field="email" hidden="true"  queryMode="single"  width="90"></t:dgCol>

          <%--<t:dgToolBar title="录入" icon="fa fa-plus" url="hmStaffController.do?goAdd&winTitle=录入员工信息" funname="add" width="1200" height="600"></t:dgToolBar>--%>

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

 </script>
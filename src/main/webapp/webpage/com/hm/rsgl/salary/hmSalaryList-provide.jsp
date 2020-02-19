<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hmSalaryList" checkbox="false"  sortName="deptName,workName,groupName" pageSize="20" pagination="true" fitColumns="false" title="" actionUrl="hmSalaryController.do?datagrid&month=${param.month}&xclbV=${param.xclb}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="工号"  frozenColumn="true" query="true"  field="workNo"  queryMode="single"  width="80"></t:dgCol>--%>
      <t:dgCol title="月份"  field="month" query="true"   hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="" rowspan="3" field="jiab" hidden="true" formatterjs="setCqsj" queryMode="single"  width="100"></t:dgCol>

      <t:dgCol title="部门" rowspan="3" frozenColumn="true" query="true" field="deptName"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="车间"  rowspan="3"  frozenColumn="true" query="true" field="workName"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="班组" rowspan="3"   frozenColumn="true" query="true" field="groupName"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="姓名" rowspan="3"  frozenColumn="true" query="true" field="realName"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="出勤时间" rowspan="3" field="zcb" frozenColumn="true"  formatterjs="setCqsj" queryMode="single"  width="100"></t:dgCol>

      <t:dgCol title="应发金额" colspan="${zSize+6}"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="应扣金额" colspan="${fSize+4}" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="实发工资" field="money" rowspan="3" formatterjs="setPoint" newColumn="true"   queryMode="single"  width="80"></t:dgCol>

      <t:dgCol title="基本部分" colspan="${zSize+1}"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="合计" field="baseHj" rowspan="2" formatterjs="setPoint" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="出勤" colspan="3"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="应发合计" field="yfHj" rowspan="2"  formatterjs="setPoint" queryMode="single"  width="70"></t:dgCol>

      <t:dgCol title="应扣部分" colspan="${fSize+3}"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="应扣合计" field="ykHj" rowspan="2"  formatterjs="setPoint" newColumn="true"    queryMode="single"  width="70"></t:dgCol>

      <c:forEach var="list" items="${zcategoryEntities}" varStatus="status">
            <t:dgCol formatterjs="setPoint" title="${list.name eq '基本工资' ? (param.xclb ne '01' ? '计时工资':'基本工资'):(list.name eq '岗位补贴' ? (param.xclb ne '01' ? '计件工资':'岗位补贴'):list.name)}"  field="${list.code}"  queryMode="single" query="${list.queryed eq 0 ? 'true':'false'}"  width="${list.column_type eq '4' ? '210':(list.column_type eq '0' || list.column_type eq '2') ? '50':'70'}"></t:dgCol>
        </c:forEach>
      <t:dgCol title="其他补贴" field="otherBt" formatterjs="setPoint" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="迟到早退" field="cdzt" formatterjs="setPoint" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="病事假" field="bsj" formatterjs="setPoint" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="旷工无打卡" field="kgwdk" queryMode="single"  width="70"></t:dgCol>
      <c:forEach var="list" items="${fcategoryEntities}" varStatus="status">
          <t:dgCol formatterjs="setPoint" title="${list.name}"  field="${list.code}"  queryMode="single" query="${list.queryed eq 0 ? 'true':'false'}"  width="${list.column_type eq '4' ? '210':(list.column_type eq '0' || list.column_type eq '2') ? '50':'70'}"></t:dgCol>
          <c:if test="${status.index eq 0}">
              <t:dgCol title="劳保" formatterjs="setPoint" field="lb" queryMode="single"  width="70"></t:dgCol>
              <t:dgCol title="奖罚" formatterjs="setPoint" field="jf" queryMode="single"  width="70"></t:dgCol>
              <t:dgCol title="其他扣回" formatterjs="setPoint" field="qtkh" queryMode="single"  width="70"></t:dgCol>
          </c:if>
      </c:forEach>

  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
     $("#hmSalaryListtb").find("input[name='month']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
     $("#hmSalaryListtb").find("input[name='month']").val('${month}');
 });

 function setPoint(val,row,index){
     if(val != null && val != ''){
         if(row.realName != null && row.realName != ''){
             return parseInt(val);
         }else{
             return '<font color="blue">'+parseInt(val)+'</font>';
         }
     }else{
         return '';
     }
 }
 function setCqsj(val,row,index){
     if(row.zcb != null && row.zcb != ''&& row.jiab != null && row.jiab != ''){
         return '正常:'+parseInt(row.zcb)+",加:"+parseInt(row.jiab);
     }else{
         return '';
     }
 }
 function doSc() {
     $.dialog.confirm('您是否确定生成月度工资表?', function (r) {
         if (r) {
             $.ajax({
                 url : "hmSalaryController.do?doAdd&month="+$("#hmSalaryListtb").find("input[name='month']").val(),
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     tip(d.msg);
                     if (d.success) {
                         $('#hmSalaryList').datagrid('reload');
                     }
                 }
             });
         }
     });
 }
 //导入
 function ImportXls() {
     $.dialog({
         content: 'url:hmBaseSalaryController.do?upload',
         zIndex: getzIndex(),
         title : 'Excel导入',
         cache:false,
         lock : true,
         width: 700,
         height: 300
     });

 }

//导出
function ExportXls() {
	JeecgExcelExport("hmBaseSalaryController.do?exportXls","hmSalaryList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("hmBaseSalaryController.do?exportXlsByT","hmSalaryList");
}

 </script>

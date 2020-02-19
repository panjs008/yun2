<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="tools"></t:base>
<t:datagrid name="emkSampleTotalList" checkbox="false" pagination="false" fitColumns="false" title="" actionUrl="hmSalaryController.do?detailList&month=${param.month}&day=${param.day}&workNo=${param.workNo}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="车间"   rowspan="2" field="mainWorkName" queryMode="single"  width="90"></t:dgCol>
    <t:dgCol title="计时公班"   colspan="3" queryMode="single"  width="90"></t:dgCol>
    <%--<t:dgCol title="计时工资"   colspan="3" queryMode="single"  width="90"></t:dgCol>--%>
    <t:dgCol title="计件工资"  colspan="4"  newColumn="true" queryMode="single"  width="90" ></t:dgCol>

    <%--<t:dgCol title="正常班"   colspan="2" queryMode="single"  width="90"></t:dgCol>
    <t:dgCol title="加班"    colspan="2" queryMode="single"  width="90"></t:dgCol>--%>
    <t:dgCol title="正常班"  formatterjs="setPoint1" field="hj5zcb" queryMode="single"  width="60"></t:dgCol>
    <t:dgCol title="加班"   formatterjs="setPoint1" field="hj5jiab" queryMode="single"  width="50" ></t:dgCol>
    <t:dgCol title="合计"   formatterjs="setPoint"  field="hj5"  queryMode="single"  width="50" ></t:dgCol>

    <%--<t:dgCol title="正常班"   formatterjs="setPoint" field="hj6zcb"  queryMode="single"  width="60"></t:dgCol>--%>
    <%--<t:dgCol title="加班"  formatterjs="setPoint" field="hj6jiab"  queryMode="single"  width="50"></t:dgCol>--%>
    <%--<t:dgCol title="合计"  formatterjs="setPoint"  field="hj6"  queryMode="single"  width="50"></t:dgCol>--%>

    <t:dgCol title="计件一"  formatterjs="setPoint"  field="hj1"  queryMode="single"  width="60"></t:dgCol>
    <t:dgCol title="计件二"  formatterjs="setPoint"  field="hj2"  queryMode="single"  width="60"></t:dgCol>
    <t:dgCol title="计件三"  formatterjs="setPoint"  field="hj3"  queryMode="single"  width="60"></t:dgCol>
    <t:dgCol title="计件四"  formatterjs="setPoint" field="hj4"  queryMode="single"  width="60"></t:dgCol>
    <t:dgToolBar title="查看明细" icon="fa fa-search"  funname="look" width="1200" height="630"></t:dgToolBar>

</t:datagrid>
<script type="text/javascript">
    $(document).ready(function(){
    });
    function setPoint(val,row,index){
        if(val != null && val != ''  && val != '0.0'){
            return parseInt(val);
        }else{
            return '';
        }
    }
    function setPoint1(val,row,index){
        if(val != null && val != ''  && val != '0.0'){
            return val;
        }else{
            return '';
        }
    }
    function look(){
        var height =window.top.document.body.offsetHeight*0.85;
        createdetailwindow('工资明细' , 'hmWorkPriceController.do?detail&workNo=${param.workNo}&month=${param.month}&day=${param.day}' , 1350, height);
    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'emkSampleTotalController.do?upload', "emkSampleTotalList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("emkSampleTotalController.do?exportXls","emkSampleTotalList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("emkSampleTotalController.do?exportXlsByT","emkSampleTotalList");
    }

</script>
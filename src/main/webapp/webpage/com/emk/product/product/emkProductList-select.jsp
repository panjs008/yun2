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
 <div region="west" style="width: 160px;" title="商品类别" split="true">
  <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false">
   <ul id="orgtree">
   </ul>
  </div>
 </div>
  <div region="center" style="padding:0px;border:0px">

      <t:datagrid name="emkProductList" checkbox="true" sortOrder="asc" sortName="orgCode,proNum"  pagination="true" fitColumns="false" title="" actionUrl="emkProductController.do?datagridSelect" idField="id" fit="true" btnCls="bootstrap" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
          <t:dgCol title="类别"  field="proType" hidden="true"  queryMode="single"  width="100"></t:dgCol>
          <t:dgCol title="商品类别"  field="proTypeName" hidden="true" queryMode="single"  width="80"></t:dgCol>
          <t:dgCol title="商品编号"  field="proNum" frozenColumn="true" query="true" queryMode="single"  width="110"></t:dgCol>
          <t:dgCol title="商品名称"  field="proZnName" query="true"  queryMode="single"  width="140"></t:dgCol>
          <t:dgCol title="条码"  query="true" field="barCode"  queryMode="single"  width="80"></t:dgCol>
          <t:dgCol title="规格"  field="standards"   queryMode="single"  width="90"></t:dgCol>
          <t:dgCol title="型号"  field="brand"   query="true" queryMode="single"  width="120"></t:dgCol>
          <t:dgCol title="单位"  field="unit"  queryMode="single"  width="50"></t:dgCol>
          <t:dgCol title="零售价"  field="sellPrice"  queryMode="single"  width="60"></t:dgCol>
          <t:dgCol title="成本价"  field="costPrice"  queryMode="single"   width="60"></t:dgCol>
          <c:forEach var="list" items="${categoryEntities}" varStatus="status">
              <t:dgCol title="${list.name}"  field="${list.code}" queryMode="single"></t:dgCol>
          </c:forEach>

      </t:datagrid>
</div>
</div>
 <script src = "webpage/com/emk/product/product/emkProductList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });



//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkProductController.do?upload', "emkProductList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkProductController.do?exportXls","emkProductList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkProductController.do?exportXlsByT","emkProductList");
}

 </script>
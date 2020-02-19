<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function () {
        $('#orgtree').tree({
            animate: true,
            url : 'emkProductTypeController.do?setPOfficeInfo&selfId=${emkProductTypePage.id}',
            onClick: function (node) {
                if ($('#orgtree').tree('isLeaf', node.target) || $('#orgtree').tree('expand', node.target)) {
                    $('#emkProductList').datagrid("reload", {"proType": node.id});
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

        <t:datagrid name="emkProductList" checkbox="true" sortOrder="asc" sortName="orgCode,proNumB"  pagination="true" fitColumns="false" title="" actionUrl="emkProductController.do?datagridB" idField="id" fit="true" btnCls="bootstrap" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="类别"  field="proType" hidden="true"  queryMode="single"  width="100"></t:dgCol>
            <t:dgCol title="商品类别"  field="proTypeNameB" hidden="true" queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="商品编号"  field="proNumB" frozenColumn="true" query="true" queryMode="single"  width="110"></t:dgCol>
            <t:dgCol title="商品名称"  field="proZnNameB" query="true"  queryMode="single"  width="140"></t:dgCol>
            <t:dgCol title="条码"  query="true" field="barCodeB"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="规格"  field="standardsB"   queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="型号"  field="brandB"   query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单位"  field="unitB"  queryMode="single"  width="50"></t:dgCol>
            <t:dgCol title="零售价"  field="sellPriceB"  queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="成本价"  field="costPriceB"  queryMode="single"   width="60"></t:dgCol>
            <t:dgCol title="颜色"  field="a01a01a01B"  queryMode="single"   width="60"></t:dgCol>

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
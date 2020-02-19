<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script type="text/javascript">
    $(function () {
        $('#orgtree').tree({
            animate: true,
            //url: 'departController.do?getOrgTree&departid='+$("#departid").val(),
            url : 'departController.do?setPFunctionForStaff&selfId=${depart.orgCode}',
            onClick: function (node) {
                if ($('#orgtree').tree('isLeaf', node.target) || $('#orgtree').tree('expand', node.target)) {
                    $('#userList0').datagrid("reload", {"orgCode": node.id});
                    //var depart = $('#orgtree').tree('getSelected');
                    //$("#showDepart").text(depart.text);
                }
            }/*,
             onLoadSuccess: function (node) {
             expandAll();
             }*/
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
<div class="easyui-layout" style="width:700px;height:500px;">
    <div region="west" style="width: 180px;" title="组织机构" split="true">
        <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false">
            <ul id="orgtree">
            </ul>
        </div>
    </div>
    <div data-options="region:'center'">
<t:datagrid name="userList0" checkbox="false" pagination="true" fitColumns="true" title="" width="500px" actionUrl="userController.do?datagrid&userFlag=0"
             idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
    <t:dgCol title="common.department" sortable="false" width="170" field="userOrgList.tsDepart.departname" query="false"></t:dgCol>
    <t:dgCol title="common.username" sortable="false" width="90" field="userName" query="true"></t:dgCol>
            <t:dgCol title="common.real.name" field="realName" query="false" width="120"></t:dgCol>
            <t:dgCol title="common.status" sortable="true" field="status"  width="70" replace="common.active_1,common.inactive_0,super.admin_-1" ></t:dgCol>
        </t:datagrid>
    </div>
</div>

<script type="text/javascript">

</script>

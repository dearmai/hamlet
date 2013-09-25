Ext.define('HAMLET.view.RemoveSiteWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.removeSiteWindow',
	requires: [
        'HAMLET.store.Groups'
    ],
    itemId: 'removeSiteWindow',
    width: 400,
    closeAction: 'destroy',
    title: '그룹 삭제하기',
    modal: true,

     initComponent: function() {
        var me = this;
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    itemId: 'suggestForm',
					url: 'resources/json/deleteGroup.jsp',
                    bodyPadding: 10,
					layout: 'anchor',
					margin: '3 3 3 3',
					items: [
						{
							xtype: 'combobox',
							fieldLabel: '그룹 이름',
							name: 'parent_id',
							anchor: '100%',
							allowBlank: false,
							editable: false,
							displayField: 'value',
							store: Ext.create('HAMLET.store.Groups', {
								
							}),
							valueField: 'code',
							valueNotFoundText: '삭제할 그룹이 존재하지 않습니다'
						},{
							xtype: 'displayfield',
							value: '<span style="color:red;font-weight:bold" data-qtip="Required">※그룹을 삭제하면 하위 아이템도 모두 삭제 됩니다. (ex. 최상위 그룹을 선택하면 모두 삭제)</span>'
						}
					],
				buttons: [{
					text: 'Delete',
					handler: function() {
						if(this.up('form').getForm().isValid()){
							this.up('form').getForm().submit({
								success : function (result, request){
									
									Ext.Msg.alert("알림","그룹이 삭제되었습니다. 리로드하면 반영됩니다.");
									// 창을 닫는다.
									me.close(me.closeAction);
								},
								failure :  function (response){
									// 에러 표시
									Ext.Msg.alert("경고","그룹이 삭제되지 않았습니다.");
								}	
								
							});
						}else{
							return false;
						}
					}
				},{
					text: 'Close',
					 handler: function() {
						me.close(me.closeAction);
					}
			}]
		}
	]
	});

        me.callParent(arguments);
    }

});
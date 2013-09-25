Ext.define('HAMLET.view.AddSiteWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.addSiteWindow',
    itemId: 'siteInfoWindow',
    width: 400,
    closeAction: 'destroy',
    title: '사이트 추가하기',
    modal: true,

     initComponent: function() {
        var me = this;
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
        Ext.applyIf(me, {
        	items: [{
        		xtype: 'form',
                    itemId: 'suggestForm',
					url: 'resources/json/createSite.jsp',
                    bodyPadding: 10,
					layout: 'anchor',
					margin: '3 3 3 3',
					items: [
						{
							xtype: 'combobox',
							itemId: 'cmbParentId',
							fieldLabel: '상위서버',
							name: 'parent_id',
							anchor: '100%',
							allowBlank: false,
							editable: false,
							displayField: 'value',
							store: Ext.create('HAMLET.store.Groups'),
							valueField: 'code',
							valueNotFoundText: '상위자산이 존재하지 않습니다'
						},{
							xtype: 'textfield',
							fieldLabel: '서버 이름',
							anchor: '100%',
							name: 'server_name',
							allowBlank: false
						},{
							xtype: 'textfield',
							fieldLabel: '서버 URL',
							anchor: '100%',
							name: 'server_url',
							allowBlank: true
						},{
							xtype: 'checkboxfield',
							fieldLabel: '로그인 테스트',
							anchor: '100%',
							name: 'is_login_flow',
							inputValue: 'Y'
						},{
							xtype: 'textfield',
							fieldLabel: '로그인 폼쿼리',
							anchor: '100%',
							name: 'login_form_query',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '아이디 이름',
							anchor: '100%',
							name: 'id_name',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '아이디 값',
							anchor: '100%',
							name: 'id_value',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '패스워드 이름',
							anchor: '100%',
							name: 'password_name',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '패스워드 값',
							anchor: '100%',
							name: 'password_value',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '확인할 문자열',
							anchor: '100%',
							name: 'success_string',
							allowBlank: true
						}
					],
				buttons: [{
					text: 'Save',
					handler: function() {
						if(this.up('form').getForm().isValid()){
							this.up('form').getForm().submit({
								success : function (result, request){
									
									Ext.Msg.alert("알림","아이템이 저장되었습니다. 추가한 아이템은 리로드해야 나타납니다.");

									/*
									// 메뉴 트리를 다시 로드할것인지 묻는다
									Ext.Msg.confirm('알림', '아이템을 생성했습니다. 리로드 할까요?', function(button) {
										if (button === 'yes') {
											// do something when Yes was clicked.
											console.log('delegate realod');
										} else {
											Ext.Msg.alert("알림","추가한 아이템은 리로드해야 나타납니다.");
											// do something when No was clicked.
										}
									});*/
									// 창을 닫는다.
									me.close(me.closeAction);
								},
								failure :  function (response){
									// 에러 표시
									Ext.Msg.alert("경고","아이템이 저장되지 않았습니다.");
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
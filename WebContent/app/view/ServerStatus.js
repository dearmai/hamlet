Ext.define('HAMLET.view.ServerStatus', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.ServerStatus',
	config : {
		pgmKey : ''
	},
	bodyStyle:{
					"background-color":"#157FCC"
				},
	initComponent : function() {
		var me = this;

		me.initConfig();
		Ext.apply(this, {
			items: [
				{
                    xtype: 'form',
                    itemId: 'programForm',
					url: 'resources/json/updateSite.jsp',
                    bodyPadding: 8,
					margin: '3 3 3 3',
					defaults:{
						labelStyle: 'font-size: 11px',
						fieldStyle: 'font-size: 11px',
						labelWidth: 90
					},
					items: [
						{
							xtype: 'textfield',
							fieldLabel: '서버 아이디',
							name: 'server_id',
							allowBlank: false,
							readOnly: true
						},{
							xtype: 'combobox',
							fieldLabel: '상위서버',
							name: 'parent_id',

							allowBlank: false,
							editable: false,
							displayField: 'value',
							store: Ext.create('HAMLET.store.Groups'),
							valueField: 'code'
						},
						{
							xtype: 'textfield',
							fieldLabel: '서버 이름',
							name: 'server_name',
							allowBlank: false
						},{
							xtype: 'textfield',
							fieldLabel: '서버 URL',
							
							name: 'server_url',
							allowBlank: true
						},{
							xtype: 'checkboxfield',
							fieldLabel: '로그인 테스트',
							
							name: 'is_login_flow',
							inputValue: 'Y'
						},{
							xtype: 'textfield',
							fieldLabel: '로그인 폼쿼리',
							
							name: 'login_form_query',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '아이디 이름',
							
							name: 'id_name',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '아이디 값',
							
							name: 'id_value',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '패스워드 이름',
							
							name: 'password_name',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '패스워드 값',
							inputType: 'password',
							name: 'password_value',
							allowBlank: true
						},{
							xtype: 'textfield',
							fieldLabel: '확인할 문자열',
							
							name: 'success_string',
							allowBlank: true
						}
					],
					dockedItems: [{
						xtype: 'toolbar',
						dock: 'top',
						ui: 'footer',
			
					margin: '0 1 1 1',
						items: [
							{xtype: 'tbfill'},
							{
								text: '수정',
								handler: function() {
									if(this.up('form').getForm().isValid()){
										this.up('form').getForm().submit({
											success : function (result, request){
												Ext.Msg.alert("알림","아이템이 수정되었습니다.");
											},
											failure :  function (response){
												// 에러 표시
												Ext.Msg.alert("경고","아이템이 수정되지 않았습니다.");
											}	
										});
									}else{
										return false;
									}
								}
							},{
								text: '삭제',
								handler: function() {
									var me2 = this;
									Ext.Msg.confirm('알림', '삭제된 아이템은 복구되지 않습니다. 삭제 할까요?', function(button) {
										if (button === 'yes') {
											// do something when Yes was clicked.
											console.log('delegate realod');
											me2.up('form').getForm().submit({
												params : {"action": "delete"},
												success : function (result, request){
													
													Ext.Msg.alert("알림","아이템이 수정되었습니다.");

												},
												failure :  function (response){
													// 에러 표시
													Ext.Msg.alert("경고","아이템이 수정되지 않았습니다.");
												}	
											});
										} else {
											//Ext.Msg.alert("알림","추가한 아이템은 리로드해야 나타납니다.");
											// do something when No was clicked.
										}
								});
							}
						}
					]
				}]
			}
		]
	});
		me.callParent(arguments);
		//me.on('render', this.initialJob, this);
	},
	initialJob : function() {
		//console.log('HAMLET.view.ServerStatus', this.getPgmKey())
	}
});
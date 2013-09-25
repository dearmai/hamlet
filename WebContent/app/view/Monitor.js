Ext.define('HAMLET.view.Monitor', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.Monitor',
//	title : 'Company data',
//	frame : true,
	bodyPadding : 5,
	config : {
		pgmKey : ''
	},

	fieldDefaults : {
		labelAlign : 'left',
		msgTarget : 'side'
	},
	 bodyStyle:{
		"background-color":"#157FCC"
	},
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	initComponent : function() {
		var me = this;
		me.initConfig();
		var store = Ext.create('HAMLET.store.ServerData');
		store.proxy.extraParams = {
				serverId : me.getPgmKey()		
		}
		Ext.apply(this, {
			store : store,
			tbar : [{
				xtype : 'button',
				text : '시간대별',
				handler: function(){
					store.proxy.url = '/resources/json/serverData.jsp?type=Hour';
					store.proxy.extraParams = {
							serverId : me.getPgmKey(),		
							displayDataCount : me.down('numberfield[itemId=displayDataCount]').getValue()			
					}
					store.load();
				}
			},{
				xtype : 'button',
				text : '일별',
				handler: function(){
					store.proxy.url = '/resources/json/serverData.jsp?type=Day';
					store.proxy.extraParams = {
							serverId : me.getPgmKey(),		
							displayDataCount : me.down('numberfield[itemId=displayDataCount]').getValue()			
					}
					store.load();
				}
			},{
				xtype : 'button',
				text : '월별',
				handler: function(){
					store.proxy.url = '/resources/json/serverData.jsp?type=Month';
					store.proxy.extraParams = {
							serverId : me.getPgmKey(),		
							displayDataCount : me.down('numberfield[itemId=displayDataCount]').getValue()			
					}
					store.load();
				}
			},{
				xtype : 'button',
				text : '년도별',
				handler: function(){
					store.proxy.url = '/resources/json/serverData.jsp?type=Year';
					store.proxy.extraParams = {
							serverId : me.getPgmKey(),		
							displayDataCount : me.down('numberfield[itemId=displayDataCount]').getValue()			
					}
					store.load();
				}
			}, {
				xtype :'label',
				text : '출력데이터수'
			}, {
				xtype :'numberfield',
				itemId : 'displayDataCount',
				width : 60,
				step: 10,
				value: 30,
		        maxValue: 100,
		        minValue: 0
			}],
			
			items : [ {
				xtype : 'ServerChart',
				store : store
			}, 
			{
				xtype : 'container',
				layout : {
					type : 'hbox',
					align : 'stretch'
				},
				flex : 3,
				items : [ {
					xtype : 'servergrid',
					store : store,
					listeners : {
						select: function(grid, record, item, index){
							var store = me.down('serverrowdatagrid').store;
							store.proxy.extraParams = {
									serverId : me.getPgmKey(),
									type : 'Row',
									created_date: record.get("group_date")
							}
							store.proxy.url = '/resources/json/serverData.jsp';
							store.load();
						}
					}
				}, {
					xtype : 'serverrowdatagrid'
				}]
			} ]
		});

		me.callParent(arguments);
		me.on('render', this.initialJob, this);
	},
	
	initialJob : function() {
		this.store.proxy.extraParams = {
				serverId : this.getPgmKey(),
				type : 'Hour'
		}
		this.store.load();
	}
});
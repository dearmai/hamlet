Ext.define('HAMLET.view.CommonPanel',{
	extend : 'Ext.panel.Panel',
	alias : 'widget.frame-CommonPanel',
	layout : 'border',
	initComponent: function() {
		var me = this;
		
		Ext.apply(this, {
			dockedItems: [{
			    xtype: 'toolbar',
			    itemId:'TB',
			    dock: 'top',
			    items: [
			        { 
			        	xtype: 'button', 
			        	text: '공통기능1',
			        	action : 'common.help',
				        listeners : {
			        		click : function(a){
			        			console.log('도움말 호출', me.uniqid);
			        		}
			        	}
			        },'-',
			        { 
			        	xtype: 'button', 
			        	text: '공통기능2',
			        	action : 'common.help',
				        listeners : {
			        		click : function(a){
			        			console.log('도움말 호출', me.uniqid);
			        		}
			        	}
			        },'-',
			        { 
			        	xtype: 'button', 
			        	text: '공통기능3',
			        	action : 'common.help'
			        }
			    ]
			}]
		});
		me.callParent(arguments);
	}
});
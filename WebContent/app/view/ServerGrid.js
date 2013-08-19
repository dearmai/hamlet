Ext.define('HAMLET.view.ServerGrid', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.ServerGrid',
	config : {
		pgmKey : ''
	},
	initComponent : function() {
		var me = this;
		me.initConfig();
		Ext.apply(this, {
			
		});

		me.callParent(arguments);
		me.on('render', this.initialJob, this);
	},
	initialJob : function() {
		console.log('HAMLET.view.ServerStatus', this.getPgmKey())
	}
});
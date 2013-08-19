Ext.require('HAMLET.view.monitoring.HdfsSummaryView');
Ext.require('HAMLET.view.monitoring.HdfsSummaryChart');
Ext.require('HAMLET.view.monitoring.HdfsSummaryChartCond');
Ext.require('HAMLET.view.monitoring.SummaryOfService');
Ext.require('HAMLET.view.monitoring.BlockChart');
Ext.require('HAMLET.view.monitoring.BlockDataView');
Ext.require('HAMLET.view.monitoring.BlockDataChart');
Ext.require('HAMLET.view.monitoring.HDFSDirectoryList');
Ext.require('HAMLET.view.monitoring.HDFSDirectoryListCond');
Ext.require('HAMLET.view.monitoring.HDFSDirectoryParsed');
Ext.require('HAMLET.view.monitoring.HDFSDirectoryParsedSummary');
Ext.define('HAMLET.view.Monitor', {
	extend : 'Ext.container.Container',
	alias : 'widget.Monitor',
    //autoScroll : true,
    style: {borderColor:'#000000', borderStyle:'solid', borderWidth:'1px'},
    componentCls : 'contents_wrap',
	config : {
		pgmKey : ''
	},
	initComponent : function() {
		var me = this;
		me.initConfig();
		Ext.apply(this, {
			items : [ {
				region : 'center',
				xtype :'ServerChart'
			} ]
		});

		me.callParent(arguments);
		me.on('render', this.initialJob, this);
	},
	initialJob : function() {
		console.log('최초 작업', this.getPgmKey())
	}
});
Ext.require('HAMLET.view.monitoring.PhisicalSummary');
Ext.require('HAMLET.view.monitoring.PhisicalSummaryChart');
Ext.require('HAMLET.view.monitoring.PhisicalSummaryView');
Ext.require('HAMLET.view.monitoring.PerformanceSummaryChart');
Ext.require('HAMLET.view.monitoring.PerformanceSummaryGrid');

Ext.define('HAMLET.view.ServerChart', {
	extend : 'Ext.container.Container',
	alias : 'widget.ServerChart',
	componentCls : 'contents_wrap',
	autoScroll : true,
	height : 800,
	layout : {
		type : "vbox",
		align : "stretch"
	},
	config : {
		pgmKey : ''
	},
	initComponent : function() {
		var me = this;
		me.initConfig();
		Ext.apply(this, {
			items: [{
				xtype : 'phisicalsummary'
			},
			{
				xtype : 'performancesummarychart'
			},
			{
				xtype : 'performancesummarygrid'
			}]
		});

		me.callParent(arguments);
		me.on('render', this.initialJob, this);
	},
	initialJob : function() {
		console.log('HAMLET.view.ServerStatus', this.getPgmKey())
	}
});
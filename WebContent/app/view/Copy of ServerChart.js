Ext.require('HAMLET.view.monitoring.PhisicalSummaryView');
Ext.require('HAMLET.view.monitoring.PhisicalSummaryChart');
Ext.require('HAMLET.view.monitoring.PhisicalSummaryChartCond');
Ext.require('HAMLET.view.monitoring.PerformanceSummary');
Ext.require('HAMLET.view.monitoring.SummaryGrid');

Ext.define('HAMLET.view.ServerChart', {
	extend : 'Ext.panel.Panel',
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
			items : [ {
				xtype : "container", 
				cls : 'repository_wrap',
				layout : {
					type : "vbox",
					align : "stretch"
				},
				margin : '15 0',
				items : [ {
					html : '<h2></h2>'
				}, {
					cls : 'monitoring_wrap',
					items : [ {
						xtype : "phisicalsummaryview"
					}, {
						xtype : 'phisicalsummarychart',
						cls : 'chart_area',
						width : 638
					} ]
				},
                {
                	html : '<br><h2>Latency % Memory</h2>'
                },
                {
                	cls : 'monitoring_wrap chart_area',
                	xtype : 'performancesummary'
                },
                {
                	html : '<br><h2>Latency % Memory</h2>'
                },
                {
                	xtype : 'container',
	                width: 350,
	                height: 250,
	                layout:'column',
                	items : [{
                		xtype : 'summarygrid',
                		columnWidth: 0.5
                	},{
                		xtype : 'summarygrid',
                		columnWidth: 0.5
	            	}]
                }]
                
			} ]
		});

		me.callParent(arguments);
		me.on('render', this.initialJob, this);
	},
	initialJob : function() {
		console.log('HAMLET.view.ServerStatus', this.getPgmKey())
	}
});
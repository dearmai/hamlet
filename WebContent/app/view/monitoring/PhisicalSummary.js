/***
 * 서버의 물리적 정보 확인
 */
Ext.define('HAMLET.view.monitoring.PhisicalSummary', {
	extend : 'Ext.container.Container',
	alias : 'widget.phisicalsummary',
	cls : 'repository_wrap',
	margin : '5 5 5 5',
	initComponent: function() {
		var me = this;
        Ext.apply(this, {
        	items : [ {
				xtype : 'container',
				html : '<br><h2>물리적 정보</h2>'
			},
			{
				xtype : 'container',
				cls: 'monitoring_wrap',
				layout : {
				    type : "hbox",
				    align : "stretch"
				},
				items : [{
					xtype : 'phisicalsummaryview'
				},
				{
					xtype : 'phisicalsummarychart',
					flex : 1,
					cls : 'chart_area block'
				}
				]
			}]
        });
		this.callParent(arguments);
	}
});
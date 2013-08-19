Ext.define('HAMLET.view.monitoring.BlockChart', {
	extend : 'Ext.container.Container',
	alias : 'widget.blockchart',
	initComponent: function() {
		var me = this;
		
		Ext.apply(this, {
			items : [ {
				xtype : 'container',
				html : '<br><h2>Block</h2>'
			},
			{
				xtype : 'container',
				cls: 'monitoring_wrap',
				layout : 'hbox',
				items : [{
					xtype : 'blockdataview'
				},
				{
					xtype : 'container',
					cls : 'chart_area block',
					layout : {
						type : "hbox",
						align : "stretch"
					},
					items : [{
						xtype : 'blockdatachart'
					},
					{
						xtype : 'blockdatachart'
					},
					{
						xtype : 'blockdatachart'
					}]
				}
				]
			}]
		});
		this.callParent(arguments);
	}
})
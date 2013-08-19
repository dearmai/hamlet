Ext.define('HAMLET.view.monitoring.HDFSDirectoryList', {
	extend : 'Ext.container.Container',
	alias : 'widget.hdfsdirectorylist',
	initComponent: function() {
		var me = this;
		
		Ext.apply(this, {
			items : [ {
				xtype : 'container',
				html : '<br><h2>HDFS Directory List</h2>'
			},
			{
				xtype : 'container',
//				cls: 'srch_wrap',
				layout : 'vbox',
				items : [{
					xtype : 'hdfsdirectorylistcond',
					width : '100%'
				},
				{
					xtype : 'container',
					//cls : 'chart_area block',
					layout : {
						type : "vbox",
						align : "stretch"
					},
					items : [{
						cls : 'parsed_wrap mT0',
						xtype : 'hdfsdirectoryparsed'
					},
					{
						cls : 'parsed_wrap',
						xtype : 'hdfsdirectoryparsed'
					},
					{
						cls : 'parsed_wrap',
						xtype : 'hdfsdirectoryparsed'
					}]
				}
				]
			}]
		});
		this.callParent(arguments);
	}
})
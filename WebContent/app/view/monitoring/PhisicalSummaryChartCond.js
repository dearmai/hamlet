
Ext.define('HAMLET.view.monitoring.PhisicalSummaryChartCond', {
	extend: "Ext.form.Panel",
	alias : 'widget.phisicalsummarychartcond',
	initComponent : function() {
		Ext.apply(this,{
			items : [{
				xtype : 'label',
				text : 'Useage Process'
			},
			{
				xtype      : 'fieldcontainer',
	            fieldLabel : 'Period',
	            width    : 320,
	            layout: 'hbox',
	            items : [{
	    			xtype : 'datefield',
	    			width : 100
	    		},
	    		{
	    			xtype :'container',
	    			width  : 15,
	    			html : '~',
	    			style : 'text-align:center;vertical-align:middle;'
	    		},
	    		{
	    			xtype : 'datefield',
	    			width : 100
	    		}]
			}]
		});
		
		this.callParent(arguments);
	}
})
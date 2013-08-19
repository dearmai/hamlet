Ext.define('HAMLET.view.monitoring.PerformanceSummary', {
	extend : 'Ext.container.Container',
	alias : 'widget.performancesummary',
//	layout : {
//		type : "vbox",
//		align : "stretch"
//	},
	initComponent: function() {
		var me = this;
		var LeadTimeStore = Ext.create('Ext.data.Store', {
			proxy: {
		         type: 'ajax',
                url: '/resources/json/chartData.jsp',
		         reader: {
		             type: 'json',
		             root: 'entitys'
		         }
		     },
		     fields: [
		 	         {name: 'latency', type: 'int'},
		 	         {name: 'zratio', type: 'int'},
		 	         {name: 'spmon', type: 'string'},
		 	         {name: 'avg', type: 'int'}
		 	    ],
		     autoLoad: true
		});
		
		Ext.apply(this, {
			items : [{
				xtype: 'chart',
				width: 900,
				height: 180,
				style: 'background:#fff',
	            animate: true,
	            store: LeadTimeStore,
	            shadow: true,
	            theme: 'Category1',
	            axes: [
		            {
		                type: 'Numeric',
		                position: 'left',
		                fields: ['latency'],
		                minimum: 0,
//		                maximum: 100,
			            minorTickSteps: 1,
		                grid: {
		                    odd: {
		                        opacity: 1,
		                        fill: '#ddd',
		                        stroke: '#bbb',
		                        'stroke-width': 0.5
		                    }
		                }
		            },
		            {
		                title: 'Latency',
		                type: 'Category',
		                position: 'bottom',
		                fields: ['spmon']
//		                title: 'Lead Time Trend(Inquiry to Shipment of Departure)'
		            }
		        ],
	            series: [{
	            	type: 'line',
	                xField: 'spmon',
	                yField: 'latency',
	                fill: true,
	                tips: {
	            		trackMouse: true,
	            		width: 140,
	            		height: 28,
	            		renderer: function(storeItem, item) {
	                		this.setTitle(+ storeItem.get('spmon') + " / "+ storeItem.get('latency') );
	                  	}
	                },
		            highlight: {
	                    size: 7,
	                    radius: 7
	                }
	            }]
			},
			{
				xtype: 'chart',
				width: 900,
				height: 180,
				style: 'background:#fff',
	            animate: true,
	            store: LeadTimeStore,
	            shadow: true,
	            theme: 'Category1',
	            axes: [
		            {
		                type: 'Numeric',
		                position: 'left',
		                fields: ['avg'],
		                minimum: 0,
//		                maximum: 100,
			            minorTickSteps: 1,
		                grid: {
		                    odd: {
		                        opacity: 1,
		                        fill: '#ddd',
		                        stroke: '#bbb',
		                        'stroke-width': 0.5
		                    }
		                }
		            },
		            {
		                title: 'Memory',
		                type: 'Category',
		                position: 'bottom',
		                fields: ['spmon']
//		                title: 'Lead Time Trend(Inquiry to Shipment of Departure)'
		            }
		        ],
	            series: [{
	            	type: 'line',
	                xField: 'spmon',
	                yField: 'avg',
	                fill: true,
	                tips: {
	            		trackMouse: true,
	            		width: 140,
	            		height: 28,
	            		renderer: function(storeItem, item) {
	                		this.setTitle(+ storeItem.get('spmon') + " / "+ storeItem.get('latency') );
	                  	}
	                },
		            highlight: {
	                    size: 7,
	                    radius: 7
	                }
	            }]
			}]
		});
		this.callParent(arguments);
	}
})
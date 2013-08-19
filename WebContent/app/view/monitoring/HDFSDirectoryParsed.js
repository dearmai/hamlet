Ext.define('HAMLET.view.monitoring.HDFSDirectoryParsed', {
	extend : 'Ext.container.Container',
	alias : 'widget.hdfsdirectoryparsed',
	layout : {
		type : "hbox",
		align : "stretch"
	},
	initComponent: function() {
		var me = this;
		var LeadTimeStore = Ext.create('Ext.data.Store', {
			model: 'WeatherPoint',
			proxy: {
		         type: 'ajax',
                 url: '/resources/json/chartData.jsp',
		         reader: {
		             type: 'json',
		             root: 'entitys'
		         }
		     },
		     fields: [
		 	         {name: 'zdays', type: 'int'},
		 	         {name: 'zratio', type: 'int'},
		 	         {name: 'spmon', type: 'string'},
		 	         {name: 'avg', type: 'int'}
		 	    ],
		     autoLoad: true
		});
		Ext.apply(this, {
			items : [ {
				xtype : 'hdfsdirectoryparsedsummary',
				width : 196,
				height : 177
			},
			{
				xtype: 'chart',
				width : 862,
				height : 177,
				region : 'center',
				itemId: 'LEADTIME',
				style: 'background:#fff',
	            animate: true,
	            store: LeadTimeStore,
	            shadow: true,
	            theme: 'Category1',
	            axes: [
		            {
		                type: 'Numeric',
		                position: 'left',
		                fields: ['zdays'],
		                minimum: 0,
		                maximum: 100,
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
//		                title: 'Time',
		                type: 'Category',
		                position: 'bottom',
		                fields: ['spmon']
//		                title: 'Lead Time Trend(Inquiry to Shipment of Departure)'
		            }
		        ],
	            series: [{
	            	type: 'line',
	                xField: 'spmon',
	                yField: 'zdays',
	                fill: true,
//		            label: {
//	            		display: 'true',
//	                    field: 'zdays',
//	                    renderer: Ext.util.Format.numberRenderer('0'),
//			            'text-anchor': 'top',
//	                    orientation: 'horizontal',
//	                    fill: '#fff'
//	                },
	                tips: {
	            		trackMouse: true,
	            		width: 140,
	            		height: 28,
	            		renderer: function(storeItem, item) {
	                		this.setTitle(+ storeItem.get('spmon') + " / "+ storeItem.get('zdays') );
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
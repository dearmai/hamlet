Ext.define('HAMLET.view.monitoring.SummaryOfService', {
	extend : 'Ext.container.Container',
	alias : 'widget.summaryservice',
	layout : {
		type : "vbox",
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
			//dockedItems: this.getToolBarConfig(),
			items : [{
				xtype : 'form',
				layout : {
					type : "hbox",
					align : "stretch"
				},
				 fieldDefaults: {
		            msgTarget: 'side',
		            labelAlign:'right'
		        },
				items : [/*{
					 xtype : 'commonCbx',
			            groupCode : 'AlertSearchType',
			            width : 250,
			            fieldLabel: 'Type',
			            allowBlank: false,
			            codefield : 'orgn_dvsn_cd',
			            name: 'orgn_dvsn_nm'
				},      */
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
			},{
				xtype: 'chart',
				itemId: 'LEADTIME',
				width: 100,
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
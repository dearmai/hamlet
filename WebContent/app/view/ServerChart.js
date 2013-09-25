
Ext.define('HAMLET.view.ServerChart', {
	extend : 'Ext.chart.Chart',
	alias : 'widget.ServerChart',
	height: 200,
	margin: '0 0 3 0',
	insetPadding: 30,
	cls: 'x-panel-body-default',
	shadow: true,
	animate: true,
	initComponent : function() {
		var me = this;
		me.initConfig();
		
		Ext.apply(this, {
			axes : [ {
				type : 'Numeric',
				position : 'left',
				fields : [ 'average_latency' ],
		//		minimum : 0,
				grid: true,
				hidden : false,
				label : {
		//			renderer : function(v) {
		//				return Ext.util.Format.fileSize(v);
		//			},
					font : '9px Arial'
				}
			},{
				type : 'Numeric',
				position : 'right',
				fields : [ 'average_vm_use_rate' ],
		//		minimum : 0,
		//		maximum: 100,
				hidden : false
			}, {
				type : 'Category',
				position : 'bottom',
				grid: true,
				fields : [ 'created_date' ],
				label : {
					renderer : function(v) {
						return Ext.String.ellipsis(v, 15, false);
					},
					font : '9px Arial',
					rotate : {
						degrees : 270
					}
				}
			} ],
			series : [ {
				type : 'column',
				axis : 'right',
				style : {
					fill : '#456d9f'
				},
				highlightCfg : {
					fill : '#a2b5ca'
				},
				style: {
                    fill: 'url(#bar-gradient)',
                    'stroke-width': 3
                },
				label : {
					contrast : true,
					display : 'insideEnd',
					field : 'average_vm_use_rate',
					color : '#000',
					'text-anchor' : 'middle'
				},
				listeners : {
					itemmouseup : function(item) {
						var series = me.series.get(0);
						me.up('Monitor').down('servergrid').getSelectionModel().select(
								Ext.Array.indexOf(series.items, item));
					}
				},
				xField : 'name',
				yField : [ 'average_vm_use_rate' ]
			} ,
			{
				type: 'line',
			    xField: 'created_date',
			    yField: 'average_latency',
			//	    label: {
			//			display: 'true',
			//	        field: 'average_latency',
			////	        renderer: Ext.util.Format.numberRenderer('0'),
			//	        'text-anchor': 'top',
			//	        orientation: 'horizontal',
			//	        fill: '#fff'
			//	    },
			    tips: {
					trackMouse: true,
					width: 140,
					height: 28,
					renderer: function(storeItem, item) {
			    		this.setTitle(+ storeItem.get('average_latency') + " / "+ storeItem.get('created_date') );
			      	}
			    },
			    style: {
                    fill: '#38B8BF',
                    stroke: '#38B8BF',
                    'stroke-width': 3
                },
                markerConfig: {
                    type: 'circle',
                    size: 4,
                    radius: 4,
                    'stroke-width': 0,
                    fill: '#38B8BF',
                    stroke: '#38B8BF'
                }
			}]
		});
		me.callParent(arguments);
	}
});
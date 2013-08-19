Ext.define('HAMLET.view.monitoring.PerformanceSummaryGrid', {
	extend : 'Ext.container.Container',
	alias : 'widget.performancesummarygrid',
	cls : 'repository_wrap',
	margin : '5 5 5 5',
	initComponent: function() {
		var me = this;
		
		var store = Ext.create('Ext.data.Store', {
		     fields: [
		 	         {name: 'coverage_date', type: 'string'},
		 	         {name: 'fail_count', type: 'string'},
		 	         {name: 'average_latency', type: 'string'},
		 	         {name: 'average_use_vm_mem', type: 'string'}
		 	    ],
		 	data:{'items':[
					{ 'coverage_date': 'Last 1Day',  "fail_count":"0회",  "average_latency":"120ms","average_use_vm_mem":"250MB"  },
					{ 'coverage_date': 'Last 2Day',  "fail_count":"2회",  "average_latency":"120ms","average_use_vm_mem":"250MB"  },
					{ 'coverage_date': 'Last 7Day',  "fail_count":"6회",  "average_latency":"120ms","average_use_vm_mem":"250MB"  },
					{ 'coverage_date': 'Last 1Month',  "fail_count":"1회",  "average_latency":"120ms","average_use_vm_mem":"250MB"  },
					{ 'coverage_date': 'Last 6Month',  "fail_count":"9회",  "average_latency":"120ms","average_use_vm_mem":"250MB"  },
					{ 'coverage_date': 'ALL',  "fail_count":"2회",  "average_latency":"120ms","average_use_vm_mem":"250MB"  }
		    ]},
		    proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'items'
		        }
		    }
		});
		
		var store2 = Ext.create('Ext.data.Store', {
		     fields: [
		 	         {name: 'create_date', type: 'string'},
		 	         {name: 'status', type: 'string'},
		 	         {name: 'latency', type: 'string'},
		 	         {name: 'use_vm_mem', type: 'string'},
		 	         {name: 'capture_image'}
		 	    ],
		 	data:{'items':[
					{ 'create_date': '2013-08-12_18_28_47',  "status":"OK",  "latency":"120ms","use_vm_mem":"250MB"  },
					{ 'create_date': '2013-08-12_18_27_47',  "status":"OK",  "latency":"20ms","use_vm_mem":"250MB"  },
					{ 'create_date': '2013-08-12_18_26_47',  "status":"OK",  "latency":"320ms","use_vm_mem":"250MB"  },
					{ 'create_date': '2013-08-12_18_25_47',  "status":"OK",  "latency":"420ms","use_vm_mem":"250MB"  },
					{ 'create_date': '2013-08-12_18_24_47',  "status":"OK",  "latency":"70ms","use_vm_mem":"250MB"  },
					{ 'create_date': '2013-08-12_18_23_47',  "status":"OK",  "latency":"870ms","use_vm_mem":"250MB"  },
					{ 'create_date': '2013-08-12_18_22_47',  "status":"OK",  "latency":"90ms","use_vm_mem":"250MB"  }
		    ]},
		    proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'items'
		        }
		    }
		});
		Ext.apply(this, {
			items : [{
				xtype : 'container',
				html : '<br><h2>Performance Grid</h2>'
			},
			{
				xtype : 'container',
				cls: 'monitoring_wrap',
				layout : 'hbox',
				height : 300,
				items : [{
					xtype : 'grid',
					store : store,
					flex: 1,
					columns : [ {
						text : '시간',
						flex : 1,
						align : 'center',
						dataIndex : 'coverage_date'
					}, 
					{
						text : '오류횟수 ',
						flex : 1,
						align : 'center',
						dataIndex : 'fail_count'
					}, 
					{
						text : '평균소요시간',
						flex : 1,
						align : 'center',
						dataIndex : 'average_latency'
					}, 
					{
						text : '평균메모리 ',
						flex : 1,
						align : 'center',
						dataIndex : 'average_use_vm_mem'
					}]
				},
				{
					xtype : 'grid',
					flex : 1,
					store : store2,
					columns : [ {
						text : '시간',
						flex : 1,
						align : 'center',
						dataIndex : 'create_date'
					}, 
					{
						text : '상태 ',
						flex : 1,
						align : 'center',
						dataIndex : 'status'
					}, 
					{
						text : '소요시간',
						flex : 1,
						align : 'center',
						dataIndex : 'latency'
					}, 
					{
						text : '사용메모리 ',
						flex : 1,
						align : 'center',
						dataIndex : 'use_vm_mem'
					},
					{
						text : 'Error Screen ',
						flex : 1,
						align : 'center',
						dataIndex : 'capture_image'
					}]
				}]
			}]
		});
		this.callParent(arguments);
	}
})
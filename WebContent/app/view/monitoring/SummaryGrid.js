Ext.define('HAMLET.view.monitoring.SummaryGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.summarygrid',
	columnLines : true,
	initComponent: function() {
		var me = this;
		var store = Ext.create('Ext.data.Store', {
		     fields: [
		 	         {name: 'latency', type: 'int'},
		 	         {name: 'zratio', type: 'int'},
		 	         {name: 'spmon', type: 'string'},
		 	         {name: 'avg', type: 'int'}
		 	    ],
		 	data:{'items':[
					{ 'name': 'Lisa',  "email":"lisa@simpsons.com",  "phone":"555-111-1224"  },
					{ 'name': 'Bart',  "email":"bart@simpsons.com",  "phone":"555-222-1234" },
					{ 'name': 'Homer', "email":"home@simpsons.com",  "phone":"555-222-1244"  },
					{ 'name': 'Marge', "email":"marge@simpsons.com", "phone":"555-222-1254"  }
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
			store : store,
			columns : [ {
				text : '시간',
				width : 80,
				dataIndex : 'brd_seq'
			}, 
			{
				text : '오류횟수 ',
				flex : 1,
				dataIndex : 'brd_title'
			}, 
			{
				text : '평균소요시간',
				width : 70,
				dataIndex : 'brd_input_user_nm'
			}, 
			{
				text : '평균메모리 ',
				width : 150,
				align : 'center',
				dataIndex : 'brd_input_date'
			}]
		});
		this.callParent(arguments);
	}
})
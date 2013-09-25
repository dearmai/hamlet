function perc(v) {
        return v + '%';
    }

Ext.define('HAMLET.view.ServerGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.servergrid',
	config : {
		pgmKey : ''
	},
//	id: 'company-form',
    flex: 7,
    title:'Summary Data',
    
	initComponent : function() {
		var me = this;
		me.initConfig();
		Ext.apply(this, {
			columns: [{
				 text   : 'Date',
				 flex: 1,
				 sortable : true,
				 align : 'center',
				 style: 'text-align:center',
				 dataIndex: 'group_date'
			 }, {
	            text   : 'Latency<br>(Avg)',
	            style: 'text-align:center',
	            width    : 90,
	            sortable : true,
	            dataIndex: 'average_latency',
	            align: 'right'
	        },{
	            text   : 'Use VM Memory<br>Avg',
	            style: 'text-align:center',
	            width    : 110,
	            sortable : true,
	            align: 'right',
	            dataIndex: 'average_vm_use_rate',
	            renderer: perc
	        } ,{
	            text   : 'Success Rate',
	            width    : 90,
	            sortable : true,
	            style: 'text-align:center',
	            dataIndex: 'success_rate',
	            align: 'right'
	        } ,{
	            text   : 'Fail Count',
	            width    : 90,
	            sortable : true,
	            style: 'text-align:center',
	            dataIndex: 'fail_count',
	            align: 'right'
	        }]
	        
		});

		me.callParent(arguments);
		me.store.on('load',  function(model, records) {
			me.getSelectionModel().select(0);
	    });
	},
	initialJob : function() {
		console.log('HAMLET.view.ServerStatus', this.getPgmKey())
	}
});
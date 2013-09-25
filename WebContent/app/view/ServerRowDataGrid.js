function perc(v) {
        return v + '%';
    }
function renderIcon(val) {
	return '<div class="thumbnail"><a href="' + val + '" rel="lightbox" >View Image</a></div>';
}
Ext.define('HAMLET.view.ServerRowDataGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.serverrowdatagrid',
	config : {
		pgmKey : ''
	},
//	id: 'company-form',
    flex: 7,
    margin: '0 0 0 5',
    title:'Row Data',
   
	initComponent : function() {
		var me = this;
		me.initConfig();
		var store = Ext.create('HAMLET.store.ServerData',{
			autoLoad : false
		});
		store.proxy.url = "/servlet/ServerDataSvl?serverId=1&type=Row";
		store.load();
		Ext.apply(this, {
			store : store,
			columns: [{
				 text   : 'Date',
				 flex: 1,
				 sortable : true,
				 align : 'center',
				 style: 'text-align:center',
				 dataIndex: 'created_date'
			 }, {
	            text   : 'Latency',
	            width    : 90,
	            sortable : true,
	            style: 'text-align:center',
	            dataIndex: 'target_latency',
	            align: 'right'
	        },{
	            text   : 'Use VM Memory',
	            width    : 110,
	            sortable : true,
	            align: 'right',
	            style: 'text-align:center',
	            dataIndex: 'vm_use_rate',
	            renderer: perc
	        } ,{
	            text   : 'Status',
	            width    : 90,
	            sortable : true,
	            style: 'text-align:center',
	            dataIndex: 'status',
	            align: 'right'
	        } ,{
	        	header : 'Screenshot',
	            text   : 'Image',
	            width    : 90,
	            sortable : true,
	            dataIndex: 'image',
	            style: 'text-align:center',
	            align: 'right',
	            renderer:renderIcon
	        }]
	        
		});

		me.callParent(arguments);
		me.on('selectionchange',  function(model, records) {
//			var fields;
//            if (records[0]) {
//                selectedRec = records[0];
//                if (!form) {
//                    form = this.up('panel').down('form').getForm();
//                    fields = form.getFields();
//                    fields.each(function(field){
//                        if (field.name != 'company') {
//                            field.setDisabled(false);
//                        }
//                    });
//                } else {
//                    fields = form.getFields();
//                }
//                
//                // prevent change events from firing
//                form.suspendEvents();
//                form.loadRecord(selectedRec);
//                form.resumeEvents();
//                highlightCompanyPriceBar(selectedRec);
//            }
	    });
	},
	initialJob : function() {
		console.log('HAMLET.view.ServerStatus', this.getPgmKey())
	}
});
Ext.define('HAMLET.view.Main', {
    extend: 'Ext.container.Container',
    requires:[
        'Ext.tab.Panel',
        'Ext.layout.container.Border',
        'HAMLET.view.Header',
        'HAMLET.view.WestMenuPanel',
        'HAMLET.view.WestMenuDataViewPanel',
        'HAMLET.view.ServerStatus',
        'HAMLET.view.ServerGrid',
        'HAMLET.view.ServerRowDataGrid',
        'HAMLET.view.ServerChart'
    ],
    
    xtype: 'app-main',

    layout: {
        type: 'border'
    },

    items: [{
        region: 'north',
	    xtype: 'frame-Header'
	},{
        region: 'west',
        xtype :'container',
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
        items : [{
        	xtype : 'frame-WestMenuPanel',
        	flex : 1
        },{
        	xtype : 'ServerStatus',
			overflowY :'auto',
        	title : '서버 정보',
        	flex : 1
        }],
        width: 280 
    },{
        region: 'center',
        xtype: 'tabpanel'
        
    }]
});
Ext.define('HAMLET.controller.Main', {
	extend : 'Ext.app.Controller',
	views : [ 'WestMenuPanel', 'WestMenuDataViewPanel', 'Header' ],

	refs : [ {
		ref : 'westMenu',
		selector : 'frame-WestMenuPanel'
	},{
		ref : 'serverStatus',
		selector : 'ServerStatus'
	} ],

	init : function() {
		this.control({
			'frame-WestMenuPanel > frame-WestMenuDataViewPanel' : {
				afterrender : this.firstSelect,
				expand : this.onItemClicked
			},
			'frame-WestMenuDataViewPanel > dataview' : {
				select : this.onProgramSelect
			}
		});
	},
	firstSelect : function(panel) {
		panel.firstSelectDataView();
	},

	/**
	 * Step3. 시스템 패널을 클릭 할 때 마다 expand이벤트가 호출 된다. 이 때 클릭되어 expand된 패널 위에 하위
	 * 프로그램을 출력하면 된다.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	onItemClicked : function(panel) {
		panel.onItemClicked();
	},

	onProgramSelect : function(dataview, record) {

		//console.log(record);
		// record.data.pgm_key 로 데이터를 로드한다.

		var infoStore = Ext.create('HAMLET.store.ProgramDetails');
		infoStore.load({
			params: {
				pgmKey: record.data.pgm_key
			},
			callback: function(records, operation, success){
				if(success){
					var info = records[0];
					var detail = Ext.ComponentQuery.query('#programForm')[0];
					detail.getForm().loadRecord(info);
					//console.log('info:',info);
				}
			}
		});

		var pgm_class 	= record.get('pgm_class');
		var title 		= record.get('title');
		var pgm_key 	= record.get('pgm_key')
		var centerpanel = Ext.ComponentQuery
				.query('viewport container[region="center"]')[0];
		var tab = centerpanel.down('[pgmKey=' + pgm_key + ']');
		if (!tab) {
			tab = Ext.create('HAMLET.view.Monitor');

			tab.title = title;
			tab.setPgmKey(pgm_key);
			centerpanel.add(tab);
		}
		
		centerpanel.setActiveTab(tab);
		
		// 상태로드
//		this.checkStatus(record)
	}, 
	checkStatus : function(record){
		var statusForm = this.getServerStatus().down('panel');
		statusForm.getLoader().load();
	}
});

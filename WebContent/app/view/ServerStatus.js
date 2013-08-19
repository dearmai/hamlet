Ext.define('HAMLET.view.ServerStatus', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.ServerStatus',
	config : {
		pgmKey : ''
	},
	initComponent : function() {
		var me = this;
		me.initConfig();
		Ext.apply(this, {
			items: [{
                xtype: 'panel',
				height: '100%',
				loader: {
					url: 'resources/json/systemStatus.jsp',
					type: 'json',
					renderer: function(loader, response, active) {
						var text = response.responseText;
						var jsonObj = Ext.JSON.decode(text);
						loader.getTarget().update(jsonObj);
						return true;
					}
				},
				tpl: [
                    '<div style="padding-left: 10px;padding-top:5px">',
                    '<table width="100%" border="0" cellspacing="0" cellpadding="0">',
                    '    <tr>',
                    '        <td> 모니터 서버 상태 </td>',
					'		<td>',
					'			<tpl if="hamlet_status == \'true\'">',
					'				: <img width="50%" src="resources/images/status_on.png">',
					'			<tpl else>',
					'				: <img width="50%" src="resources/images/status_off.png">',
					'			</tpl>',
					'		</td>',
                    '    </tr>',
					 '    <tr>',
                    '        <td> 대상 서버 상태 </td>',
					'		<td>',
					'			<tpl if="target_status == \'true\'">',
					'				: <img width="50%" src="resources/images/status_on.png">',
					'			<tpl else>',
					'				: <img width="50%" src="resources/images/status_off.png">',
					'			</tpl>',
					'		</td>',
                    '    </tr>',
					'<tr>',
                    '        <td> 서버 네임</td>',
					'		<td>: {target_name}</td>',
                    '    </tr>',
					'<tr>',
                    '        <td> URL</td>',
					'		<td>: {target_url}</td>',
                    '    </tr>',
					'<tr>',
                    '        <td> 기준값(Latency)</td>',
					'		<td>: {target_latency}ms</td>',
                    '    </tr>',
					'<tr>',
                    '        <td> 기준값(Memory)</td>',
					'		<td>: {target_memory}MB</td>',
                    '    </tr>',
					'<tr>',
                    '        <td> 검사주기</td>',
					'		<td>: {monitor_cycle}분</td>',
                    '    </tr>',
                    '</table>',
                    '</div>'
                ]
            }]
		});

		me.callParent(arguments);
		me.on('render', this.initialJob, this);
	},
	initialJob : function() {
		//console.log('HAMLET.view.ServerStatus', this.getPgmKey())
	}
});
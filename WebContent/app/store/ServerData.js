/**
 * Date : 2012.11.30
 * Desc : 좌측 시스템 이하 프로그램 리스트
 * 		  여기서 최하위 단 프로그램 리스트를 가져온다.
 */
Ext.define('HAMLET.store.ServerData', {
    extend: 'Ext.data.Store',
	autoLoad : false,
    model: 'HAMLET.model.ServerStatus',
	proxy: {
        type: 'ajax',
        url: '/resources/json/serverData.jsp',	
        reader: {
        	type: 'json',
        	root: 'entitys',
        	totalProperty: 'totalCount',
        	messageProperty: 'message'
        },
        listeners: {
            exception: function(proxy, response, operation){

			}
	    }
    }
});

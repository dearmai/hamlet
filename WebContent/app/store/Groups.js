/**
 * Date : 2012.11.30
 * Desc : 좌측 시스템 리스트
 * 		  여기서 중분류 정도의 시스템 리스틀 가져온다.
 */
Ext.define('HAMLET.store.Groups', {
    extend: 'Ext.data.Store',	// 당연히 store상속
	storeId: 'Groups',
	autoLoad : true,			// 자동 로드는 꺼놓자.
	proxy: {
        type: 'ajax',
        // json폴더를 루트에 만들고 systemlist.json이라는 파일를 통해 
        // 시스템 리스트를 받을 수 있다.
        url: '/resources/json/groupList.jsp',	
        reader: {
        	type: 'json',
        	root: 'entitys',
        	totalProperty: 'totalCount',
        	messageProperty: 'message'
        },
        listeners: {
            exception: function(proxy, response, operation){
            	// 나중에 구현할 부분 모든 ajax 통신에 공통으로 쓸 수 있는
            	// 에러 캐치 함수를 만들 것이다.
			}
	    }
   },
	fields: [
		{
			name: 'code'
		},{
			name: 'value'
		}
   ]
});

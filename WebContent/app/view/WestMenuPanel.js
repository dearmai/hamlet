/***
 * Author	: 곽옥석(http://benney.tistory.com)
 * Date		: 2013.02.23
 * Desc		: 중분류 시스템 메뉴 패널
 */
Ext.define('HAMLET.view.WestMenuPanel',{
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.frame-WestMenuPanel',
    layout:'accordion',	// 이놈 하위에 추가될 dataview또한 accordion
    collapsible: true,
    split:true,
    title : '^^',
    activeItem: 0,
    initComponent: function() {
        var me = this;
        Ext.apply(this, {
        	/***
        	 * Date : 2012.12.07
        	 * Desc : 최초 첫번째 프로그램 선택.
        	 */
        	setWestMenuDataViewPanel: function(){
        		var store = Ext.create('HAMLET.store.Systems');
        		store.load(function(record, b, c){				// 아직 로드전이므로 로드한다.이때 json파일 호출
        			store.each(function(rec){		// store를 탐색하여
        				me.add({					// 메뉴가 추가될 패널에 아래와 같이 패널을 추가.
			    			xtype:'frame-WestMenuDataViewPanel',
			    			title:rec.get('pgm_sysnm'),	// 시스템명
			    			pgm_syscd:rec.get('pgm_syscd'),	// 시스템 코드
			    			iconCls:rec.get('pgm_sysicon')	// 아이콘이 있다면 표기
			    		});
					});
        		});
        	}
    	});
        this.callParent(arguments);
        this.on('render', this.setWestMenuDataViewPanel, this);
    }
});
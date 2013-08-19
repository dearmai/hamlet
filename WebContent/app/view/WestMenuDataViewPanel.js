/***
 * Author	: 곽옥석(http://benney.tistory.com)
 * Date		: 2013.02.23
 * Desc		: 프로그램 데이터뷰
 */
Ext.define('HAMLET.view.WestMenuDataViewPanel',{
    extend: 'Ext.panel.Panel',
    alias: 'widget.frame-WestMenuDataViewPanel',
    animCollapse : true,
    collapsible : true,
    collapsed   : true,
    useArrows: true,
    rootVisible: false,
    multiSelect: false,
    initComponent: function() {
    	var me = this;
        Ext.apply(this, {
            items: [{
                xtype: 'dataview',
                trackOver: true,
                cls: 'feed-list',
                itemSelector: '.feed-list-item',
                overItemCls: 'feed-list-item-hover',
                tpl: '<tpl for="."><div class="feed-list-item {pgm_icon}">{title}</div></tpl><p>'
            }],
            header: {
                toolFirst: true
            },
            /***
             * Date : 2012.12.06
             * Desc : dataview상에 출력된 리스트 중에 맨처음 프로그램이
             * 		  선택되어지도록 한다.
             * @param store
             */
            firstSelectDataView : function(){
            	if(me.collapsed)	return;
    			var store = this.onItemClicked(); 
    			if(store){
   	 	    		var task = new Ext.util.DelayedTask(function(){
    	    			me.down('dataview').getSelectionModel().select(store.getAt(0));
    				});
    				task.delay(1000);
    			}
            },
            /**
             * Step3. 시스템 패널을 클릭 할 때 마다 expand이벤트가 호출 된다.
             * 이 때 클릭되어 expand된 패널 위에 하위 프로그램을 출력하면 된다.
             * @param a
             * @param b
             * @param c
             */
            onItemClicked : function(){
            	if(me.collapsed) return	// 패널이 접히지 않은 놈을 찾는다.
            	if(me.store) return;
            	me.store = Ext.create('HAMLET.store.Programs');	// 재활용 되는 것을 막는다.
    	    	me.store.load({
    	    		params: {
    	    			// 아래 코드는 시스템 패널이 가지고 있는 시스템 아이디를 프로그램 store에 전달하는
        	    		// 코드다 이렇게 해야 각기 시스템 패널별로 해당 시스템 이하의 프로그램을 가져올수 있다.
        	    		pgm_syscd: this.pgm_syscd
        	    	}
        		});
            	// 최종 시스템 패널 안으 dataview에 프로그램 store를 바인딩 한다.
    	    	me.down('dataview').bindStore(me.store);
    	    	return me.store;
            }
        });
 
        this.callParent(arguments);
    }
});
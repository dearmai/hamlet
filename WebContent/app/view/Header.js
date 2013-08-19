Ext.define('HAMLET.view.Header', {
    extend: 'Ext.Container',
    xtype: 'frame-Header',
    id: 'app-header',
    height: 52,
    layout: {
        type: 'hbox',
        align: 'middle'
    },
    initComponent: function() {
        this.items = [{
            xtype: 'component',
            id: 'app-header-title',
            html: 'HAMLET',
            flex: 1
        }];

        this.callParent();
    }
});

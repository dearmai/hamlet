Ext.define('HAMLET.view.monitoring.BlockDataView', {
	extend: "Ext.view.View",
	alias : 'widget.blockdataview',
    itemSelector: 'div.thumb-wrap',
	initComponent : function() {
		var me = this;
		this.tpl = new Ext.XTemplate(
				'<div class="data_area block">',
				'<dl>',
					'<dt>Usage</dt>',
					'<dd><span class="txt_data">10</span><span class="txt_unit">GB</span></dd>',
				'</dl>',
				'<dl>',
					'<dt>Blocks(Total)</dt>',
					'<dd><span class="txt_data">420</span><span class="txt_unit">blacks</span></dd>',
				'</dl>',
				'<dl>',
					'<dt>Directory / File</dt>',
					'<dd><span class="txt_data">10</span><span class="txt_unit">Directories</span></dd>',
					'<dd><span class="txt_data">423,422</span><span class="txt_unit">Files</span></dd>',
				'</dl>',
			'</div>',
				{
					getCls: function (b) {
		                return b["private"] ? "private" : (b.removed ? "removed" : "")
		            },
		            getMetaTags: function (b) {
		                return Ext.Array.map(Docs.data.signatures, function (a) {
		                    return b[a.key] ? '<span class="signature ' + a.key + '">' + (a["short"]) + "</span>" : ""
		                }).join(" ")
		            },
		            getTotal: Ext.bind(this.getTotal, this),
		            getStart: Ext.bind(this.getStart, this),
		            getEnd: Ext.bind(this.getEnd, this)
				});
		this.callParent(arguments);
		}
	})
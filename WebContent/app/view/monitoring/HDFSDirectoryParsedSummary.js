
Ext.define('HAMLET.view.monitoring.HDFSDirectoryParsedSummary', {
	extend: "Ext.view.View",
	alias : 'widget.hdfsdirectoryparsedsummary',
    itemSelector: 'div.thumb-wrap',
	initComponent : function() {
		var me = this;
		this.tpl = new Ext.XTemplate(
				'<div class="data_area">',
				'<dl>',
					'<dt>Parsed</dt>',
					'<dd>',
						'<span class="txt_ti">Usage</span>',
						'<span class="txt_data">10</span>',
						'<span class="txt_unit">GB</span>',
					'</dd>',
					'<dd>',
						'<span class="txt_ti">Directory</span>',
						'<span class="txt_data">320</span>',
					'</dd>',
					'<dd>',
						'<span class="txt_ti">File</span>',
						'<span class="txt_data">2,422</span>',
						'<span class="txt_unit">Files</span>',
					'</dd>',
				'</dl>',
			'</div>',
			{
//	            '<tpl for=".">',
//	                '<div class="item">',
//	                    '<div class="icon {icon}"></div>',
//	                    '<div class="meta">{[this.getMetaTags(values.meta)]}</div>',
//	                    '<div class="title {[this.getCls(values.meta)]}">{name}</div>',
//	                    '<div class="class">{fullName}</div>',
//	                "</div>",
//	            "</tpl>",
//	            '<div class="footer">',
//	                '<tpl if="this.getTotal()">',
//	                    '<a href="#" class="prev">&lt;</a>',
//	                   '<span class="total">{[this.getStart()+1]}-{[this.getEnd()]} of {[this.getTotal()]}</span>',
//	                    '<a href="#" class="next">&gt;</a>',
//	                "<tpl else>",
//	                    '<span class="total"></span>',
//	                "</tpl>",
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
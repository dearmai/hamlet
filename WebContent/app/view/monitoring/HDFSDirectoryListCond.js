
Ext.define('HAMLET.view.monitoring.HDFSDirectoryListCond', {
	extend: "Ext.view.View",
	alias : 'widget.hdfsdirectorylistcond',
    itemSelector: 'div.thumb-wrap',
	initComponent : function() {
		this.tpl = new Ext.XTemplate(
				'<div class="srch_wrap">',
				'<fieldset>',
					'<legend>e����i?��i?��</legend>',
					'<section class="srch_form">',
						'<table summary="e����i?��i?��i����i?��e����">',
							'<tbody>',
								'<tr>',
									'<th scope="row"><label for="Date">Create Date</label></th>',
									'<td>',
										'<div id="directory_date" class="extjscmb"></div>',
									'</td>',
								'</tr>',
							'</tbody>',
						'</table>',
					'</section>',
					'<div class="btn_wrap row1">',
						'<a href="#" class="btn_st1"><span>Search</span></a>',
					'</div>',
				'</fieldset>',
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
		this.on('afterrender', function(){
			var task = new Ext.util.DelayedTask(function(){
				var directory_date = Ext.widget('container', {
					layout :'hbox',
					width : 400,
					items : [{
						xtype : 'datefield',
						width : 150
					},
					{
						xtype :'container',
						width  : 15,
						html : '~',
						style : 'text-align:center;vertical-align:middle;'
					},
					{
						xtype : 'datefield',
						width : 150
					}],
					renderTo : 'directory_date'
				});
			});
			task.delay(1000);
		})
	}
})
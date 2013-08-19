
   var Runner = function(){
	    var f = function(v, pbar, count, cb){
	        return function(){
	        	pbar.updateProgress(v/count, v + ' GB Used');
	       };
	    };
	    return {
	        run : function(pbar, count, cb) {
	            var ms = 1000/count;
	            for(var i = 1; i < 65; i++){
	               setTimeout(f(i, pbar, count, cb), i*ms);
	            }
	        }
	    };
	}();

Ext.define('HAMLET.view.monitoring.PhisicalSummaryView', {
	extend: "Ext.view.View",
	alias : 'widget.phisicalsummaryview',
    itemSelector: 'div.thumb-wrap',
	initComponent : function() {
		var me = this;
		this.tpl = new Ext.XTemplate(
			'<div class="data_area col">',
                '<dl>',
                '<dt> 물리적 정보 </dt>',
                '<dd><span class="txt_unit">IP Adderss</span><span class="txt_data">10.1.25.111</span></dd>',
                '<dd><span class="txt_unit">OS Name</span><span class="txt_data">Win NT</span></dd>',
                '</dl>',
				'<dl>',
					'<dt>Disk Usage (%)</dt>',
					'<dd class="disk_area" id="diskuseage">',
					'</dd>',
				'</dl>',
				'<dl>',
					'<dt>Disk Info(Total)</dt>',
					'<dd><span class="txt_data">420/30</span><span class="txt_unit">MB</span></dd>',
				'</dl>',
				'<dl>',
					'<dt>Java VM Info</dt>',
					'<dd><span class="txt_data">0</span><span class="txt_unit">Total Memory</span></dd>',
					'<dd><span class="txt_data">0</span><span class="txt_unit">Free Memory</span></dd>',
					'<dd><span class="txt_data">420</span><span class="txt_unit">Use Radio</span></dd>',
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
		this.on('afterrender', function(){
			var task = new Ext.util.DelayedTask(function(){
				
				 var pbar2 = Ext.create('Ext.ProgressBar', {
				        text:'Ready',
				        id:'pbar2',
				        //padding: '0 1 0',
				        cls:'left-align',
				        color : 'red',
				        height : 23
				       //style: {borderColor:'#000000', borderStyle:'solid', borderWidth:'1px'},
//				        renderTo:'diskuseage'
				    });
				   pbar2.reset();
				var container = Ext.create('Ext.container.Container',{
			    	renderTo : 'diskuseage',
			    	width :362,
			    	cls : 'odw_progressbar',
			    	padding: '0 0 0 0',
			    	layout:'vbox',
			    	items : [{
			    		xtype : 'container',
			    		padding: '0 0 0 0',
			    		width : 360,
			    		items : pbar2
			    	},
			    	{
			    		xtype : 'image', 
			    		width : 362,
			    		valign :'top',
			    		padding: '0 0 0 0',
			    		style : 'vertical-align:top; border:none',
			    		src: '/resources/images/common/progresschart_div.png'
			    	}],
			    	listeners : {
			    		afterrender : function() {
			    	        Runner.run(pbar2, 100, function() {
			    	            pbar2.reset();
			    	           // pbar2.updateText('Done.');
			    	        });
			    		}
			    	}
			    });
			});
			 task.delay(1000);
			
			
		});
	}
})
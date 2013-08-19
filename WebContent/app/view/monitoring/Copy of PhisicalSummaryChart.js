var dt = new Date();
Ext.Date.patterns = {
    ISO8601Long:"Y-m-d H:i:s",
    ISO8601Short:"Y-m-d",
    ShortDate: "n/j/Y",
    LongDate: "l, F d, Y",
    FullDateTime: "l, F d, Y g:i:s A",
    MonthDay: "F d",
    ShortTime: "g:i A",
    LongTime: "g:i:s A",
    SortableDateTime: "Y-m-d\\TH:i:s",
    UniversalSortableDateTime: "Y-m-d H:i:sO",
    YearMonth: "F, Y"
};

var year = Ext.Date.format(dt, 'Y');
var month = parseInt(Ext.Date.format(dt, 'm'));
var dtt = parseInt(Ext.Date.format(dt, 'd'));
var hour = parseInt(Ext.Date.format(dt, 'H'));
var minute = parseInt(Ext.Date.format(dt, 'i'));

var generateData = (function() {
    var data = [], i = 0,
        last = false,

        date = new Date(year, month, dtt, hour, minute),
        seconds = +date,
        min = Math.min,
        max = Math.max,
        random = Math.random;


    return function() {
    	console.log(Ext.Date.MINUTE)
        var obj ;
        Ext.Ajax.request({
            async: false,
            url: '/hamlet/server_perf.jsp',
            success : function (response, action) {
                //console.log(';;;;;', response.responseText);
                obj = Ext.JSON.decode(response.responseText);
                // console.log(obj)
                data = data.slice();

                data.push({
                    date:  Ext.Date.add(date, Ext.Date.SECOND, (i++)*5),
                    visits: obj.vm_use_mem_ratio,// min(100, max(last? last.visits + (random() - 0.5) * 20 : random() * 100, 0)),
                    views: obj.vm_free_mem_ratio, //min(100, max(last? last.views + (random() - 0.5) * 10 : random() * 100, 0)),
                    users: obj.vm_free_mem_ratio+10 //min(100, max(last? last.users + (random() - 0.5) * 20 : random() * 100, 0))
                });
            },
            failure : function(opts, response) {

            }
        });

        last = data[data.length -1];
        // console.log('data....', data)
        return data;
    };
})();

Ext.define('HAMLET.view.monitoring.PhisicalSummaryChart', {
	extend : 'Ext.container.Container',
	alias : 'widget.phisicalsummarychart',
	layout : {
		type : "vbox",
		align : "stretch"
	},
	initComponent: function() {
		var me = this;
        var store = Ext.create('Ext.data.JsonStore', {
            fields: ['date', 'visits', 'views', 'users'],
            data: generateData()
        });


         Ext.apply(this, {
            //dockedItems: this.getToolBarConfig(),
			items : [{
                xtype: 'chart',
			legend: {
                position: 'right'
            },
                style: 'background:#fff',
                itemId: 'chartCmp',
                height : 200,
                store: store,
                shadow: false,
                animate: true,
                axes: [{
                    type: 'Numeric',
                    minimum: 0,
                    maximum: 100,
                    position: 'left',
                    fields: ['views', 'visits', 'users'],
                    //title: 'Number of Hits',
                    grid: {
                        odd: {
                            fill: '#dedede',
                            stroke: '#ddd',
                            'stroke-width': 0.5
                        }
                    }
                }, {
                    type: 'Time',
                    position: 'bottom',
                    fields: 'date',
                    //title: 'Day',
                    dateFormat: 'H:i',
                    groupBy: 'year,month,day',
                    aggregateOp: 'sum',

                    constrain: true,
                    fromDate: new Date(year, month, dtt, hour, minute),
                    toDate: new Date(year, month, dtt, hour, minute+5),
                    step: [Ext.Date.SECOND, 60] ,
//                    fromDate: new Date(),
//                    toDate: Ext.Date.add(new Date(), Ext.Date.DAY, 7)   ,
                    grid: true
                }],
                series: [{
                    type: 'line',
                    smooth: false,
                    axis: ['left', 'bottom'],
                    xField: 'date',
                    yField: 'visits',
                    label: {
                        display: 'none',
                        field: 'visits',
                        //renderer: function(v) { return v >> 0; },
                        'text-anchor': 'middle'
                    },
                    markerConfig: {
                        radius: 3,
                        size: 3
                    }
                },{
                    type: 'line',
                    axis: ['left', 'bottom'],
                    smooth: false,
                    xField: 'date',
                    yField: 'views',
                    label: {
                        display: 'none',
                        field: 'visits',
//                        renderer: function(v) { return v >> 0; },
                        'text-anchor': 'middle'
                    },
                    markerConfig: {
                        radius: 3,
                        size: 3
                    }
                },{
                    type: 'line',
                    axis: ['left', 'bottom'],
                    smooth: false,
                    xField: 'date',
                    yField: 'users',
                    label: {
                        display: 'none',
                        field: 'visits',
//                        renderer: function(v) { return v >> 0; },
                        'text-anchor': 'middle'
                    },
                    markerConfig: {
                        radius: 3,
                        size: 3
                    }
                }]
			}]
		});
		this.callParent(arguments);
        this.on('afterrender', function(){
            var me = this;
            var chart, timeAxis;
            var task = new Ext.util.DelayedTask(function(){
                var intr = setInterval(function() {
                    var gs = generateData();

                    var toDate = timeAxis.toDate,
                        lastDate = gs[gs.length - 1].date,
                         markerIndex = chart.markerIndex || 0;

                    if (+toDate < +lastDate) {
                        markerIndex = 1;
                        timeAxis.toDate = lastDate;
                        timeAxis.fromDate = Ext.Date.add(Ext.Date.clone(timeAxis.fromDate), Ext.Date.SECOND, 1);
                        chart.markerIndex = markerIndex;
                        console.log('last', lastDate, markerIndex)
                    }
                    store.loadData(gs);
                }, 60000);
                chart = me.child('#chartCmp');
                timeAxis = chart.axes.get(1);
            });
            task.delay(1000);
        })
	}
});
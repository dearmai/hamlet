/*
    This file is generated and updated by Sencha Cmd. You can edit this file as
    needed for your application, but these edits will have to be merged by
    Sencha Cmd when upgrading.
*/

// DO NOT DELETE - this directive is required for Sencha Cmd packages to work.
//@require @packageOverrides

Ext.application({
    name: 'HAMLET',

    extend: 'HAMLET.Application',

    autoCreateViewport: true
});

//Ext.Loader.setConfig({
//    enabled : true,
//    paths : {
//        'HAMLET' : 'app'
//    }
//});
//Ext.require('HAMLET.view.monitoring.HdfsSummaryView');
//Ext.require('HAMLET.view.monitoring.HdfsSummaryChart');
//Ext.require('HAMLET.view.monitoring.HdfsSummaryChartCond');
//Ext.require('HAMLET.view.monitoring.SummaryOfService');
//Ext.require('HAMLET.view.monitoring.BlockChart');
//Ext.require('HAMLET.view.monitoring.BlockDataView');
//Ext.require('HAMLET.view.monitoring.BlockDataChart');
//Ext.require('HAMLET.view.monitoring.HDFSDirectoryList');
//Ext.require('HAMLET.view.monitoring.HDFSDirectoryListCond');
//Ext.require('HAMLET.view.monitoring.HDFSDirectoryParsed');
//Ext.require('HAMLET.view.monitoring.HDFSDirectoryParsedSummary');
//Ext.onReady(function(){
//    Ext.widget('container', {
//        componentCls : 'contents_wrap',
//        layout : {
//            type : "vbox",
//            align : "stretch"
//        },
//        items : [{
//            xtype :'container',
//            html : '<div class="title_wrap">'+
//                '<p class="bc">Home &gt; Repository  &gt; <span>Monitoring</span></p>'+
//                '<h1>Monitoring</h1></div>',
//            height  : 46
//        },
//            {
//                xtype : "container",	// u??° i? ????.
//                cls : 'repository_wrap',
//                layout : {
//                    type : "vbox",
//                    align : "stretch"
//                },
//                //height : 275,
//                margin : '15 0',
//                items : [ {
//                    html : '<h2>S311 Summary</h2>'
//                },
//                    {
//                        cls : 'monitoring_wrap',
//                        items : [{
//                            xtype : "hdfssummaryview"
//                        },
//                        {
//                            layout : {
//                                type : "vbox",
//                                align : "stretch"
//                            },
//                            items : [
//                                {
//                                    xtype : 'hdfssummarychart',
//                                    cls : 'chart_area',
//                                    width: 638
//                                }]
//                        }]
//                    },
//                    {
//                        html : '<br><h2>Summary of Service</h2>'
//                    },
//                    {
//                        cls : 'monitoring_wrap chart_area',
//                        xtype : 'summaryservice',
//                        width: 638
//                    },
//                    {
//                        xtype : 'blockchart'
//                    },
//                    {
//                        cls : 'lineT',
//                        xtype : 'hdfsdirectorylist'
//                    }]
//            }],
//        renderTo : 'content_area'
//    })
//});

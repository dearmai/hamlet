/**
 * HAMLET 프로젝트 개발을 위한 파일
 * - 실행 방법 : ./phantomjs test01.js http://sencha.or.kr
 */

var paramData = {
	pageText : "네이버", 	// 해당 URL 페이지에 pageText 값의 존재 유무로 테스트 결과를 결정 하게 된다.
	serverId : "1",					// 테스트대상 고유 ID. MySQL>hamlet.server_list 테이블 참조
	targetUrl : "http://naver.com/",	// 모니터링 대상이 되는 URL
	serverUrl : "http://hamlet.sencha.or.kr:8080/BotReceiverSvl?",	// 테스트 결과를 저장 할 서버
	browserWidth : 1024,
	browserHeight: 768,
	clipRectWidth : 1024,
	clipRectHeight: 768,
	zoomFactor: 1,	// Default:1 , 0.5는 줌아웃 되어 저해상도 이미지가 받아진다
	needServerInfo: true,
	serverInfoUrl : "http://hamlet.sencha.or.kr/hamlet/server_perf.jsp"
};

// Local 개발 환경 URL
// paramData.serverUrl = "http://localhost:8080/HAMLET/BotReceiverSvl?";

var pageTextExist;	// paramData.pageText 유무 검사 결과 (boolean);

// Webkit 브라우저 생성
var page = require('webpage').create();
page.viewportSize = { width: paramData.browserWidth, height: paramData.browserHeight };	// 브라우저의 사이즈를 정한다. Media Query가 적용된 사이트라면 적용된 모습이 보인다.

var latency = Date.now();	// 현재 시각


function getServerInfo() {

}

var sendToServer = function(result, timeData ) {

	var serverInfo = null;

	if( paramData.needServerInfo ) {
		serverInfo = getServerInfo();

		page.open( paramData.serverInfoUrl , function(){

			serverInfo = page.evaluate( function() {
				return document.getElementsByTagName("body")[0].innerHTML;
			});

			serverInfo = JSON.parse( serverInfo );

			if( result ) {
				console.log( "# 페이지 작동 확인 완료. - " + timeData.timestamp);
			} else {
				console.log( "# 페이지 로드 실패!! - " + timeData.timestamp);
			};

			paramData.serverUrl += "serverId=" + paramData.serverId;
			paramData.serverUrl += "&createdDate=" + timeData.timestamp;
			paramData.serverUrl += "&status=" + (result ? 'OK' : 'FAIL');
			paramData.serverUrl += "&image=" + timeData.imageFileName;
			paramData.serverUrl += "&latency=" + latency;
			paramData.serverUrl += "&p1=" + serverInfo.vm_total_mem;
			paramData.serverUrl += "&p2=" + serverInfo.freePhysicalMemorySize;
			paramData.serverUrl += "&p3=" + serverInfo.vm_total_mem;
			paramData.serverUrl += "&p4=" + serverInfo.vm_free_mem;
			paramData.serverUrl += "&p5=" + serverInfo.disks[0].totalSpace;
			paramData.serverUrl += "&p6=" + serverInfo.disks[0].freeSpace;

			console.log("# paramData.serverUrl : " + paramData.serverUrl);
			page.open( paramData.serverUrl , function(){
				console.log( "# Time : " + timeData.timestamp);
				console.log( "# Result : " + (result ? 'OK' : 'FAIL'));

				page.close();
				phantom.exit();
			});
		});
	};
}

var timeData = {
		init: function() {
		  var d = new Date();
		  var s =
			  timeData.leadingZeros(d.getFullYear(), 4) + '-' +
			  timeData.leadingZeros(d.getMonth() + 1, 2) + '-' +
			  timeData.leadingZeros(d.getDate(), 2) + '_' +

			  timeData.leadingZeros(d.getHours(), 2) + '_' +
			  timeData.leadingZeros(d.getMinutes(), 2) + '_' +
			  timeData.leadingZeros(d.getSeconds(), 2);

		  timeData.timestamp = s;

		  //s = "../../hamlet_images/" +
		  //s = "/Users/JongKwang/eclipse_workspace/HAMLET/WebContent/" +
		  s = "/home/www/hamlet.sencha.or.kr/hamlet_images/" +//TODO 수정 필요
			  paramData.serverId + "_" +
			  timeData.leadingZeros(d.getFullYear(), 4) +
			  timeData.leadingZeros(d.getMonth() + 1, 2) +
			  timeData.leadingZeros(d.getDate(), 2) +

			  timeData.leadingZeros(d.getHours(), 2) +
			  timeData.leadingZeros(d.getMinutes(), 2) +
			  timeData.leadingZeros(d.getSeconds(), 2) + ".png"

		  timeData.imageFileName = s;
		  
		},
		leadingZeros: function(n, digits) {
			  var zero = '';
			  n = n.toString();
			
			  if (n.length < digits) {
			    for (i = 0; i < digits - n.length; i++)
			      zero += '0';
			  }
			  return zero + n;
		},
		timestamp: "# ERROR : timeData.init() 먼저 실행 시켜 주세요.",
		imageFileName: "# ERROR : timeData.init() 먼저 실행 시켜 주세요."
}

// paramData.targetUrl 로 페이지를 Open 한다.
page.open(paramData.targetUrl, function (status) {

	// 현재 시간 구한다.
	timeData.init();	// // 현재 시간을 구한다. YYYY-MM-DD_HH:MM:SS 형식으로 구한다.

	if (status === 'success') { // 페이지가 정상 로딩 되면

		// Loading 소요 시간 계산
		latency = Date.now() - latency;

		// paramData.pageText 유무 검사
		if( page.content.match(paramData.pageText) ) {
			pageTextExist = true;

			// 정상인 경우 화면 캡쳐 영역을 작게 조정한다. (HDD 용량 절약을 위함)
			// 이 기능을 False 하고 싶으면, aramData.regularCaptureWidth 값을 0으로 한다.
			if( paramData.clipRectWidth )	// 값이 0이 아니면 캡쳐 영역을 한정한다.
				page.clipRect = { top: 0, left: 0, width: paramData.clipRectWidth, height: paramData.clipRectHeight };
		} else {
			pageTextExist = false;
		};

		// 화면 캡쳐
		page.render( timeData.imageFileName );
		sendToServer( pageTextExist, timeData );

	} else {
		// Loading 소요 시간 계산
		latency = Date.now() - latency;
		sendToServer( false, timeData );
	}

});
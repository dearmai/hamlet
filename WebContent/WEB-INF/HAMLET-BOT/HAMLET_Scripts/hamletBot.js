
var casper = require("casper").create({
	viewportSize: {
		width: 800,
		height: 600
	},
	onTimeout: function() {
		this
			.echo("- Timeout", "RED_BAR")
			.exit()
		;
	},
	httpStatusHandlers: {
		200: function(self, resource) {
			this.log( "- Resource at " + resource.url + " Success", "info");
		},
		404: function(self, resource) {
			this.log( "- Resource at " + resource.url + " not found (404)", "error");
			this.log( "### " + this.fetchText('body'), "error");

			results.status = false;
			results.msg = "404 Error";
			casper.then(function() {
				casper.evaluate(function() {
					var sheet = document.createElement('style');
					sheet.innerHTML = 'body {background-color: #FFFFFF !important; color: #000000 !important}';
					document.body.appendChild(sheet);
				});
			});

			casper.then(function() {
				casper.wait(2000, function() {
					this.capture(timeData.imageFileName,
						undefined,
						{
							format: 'jpg',
							quality: 75		// 이미지의 해상도 결정
						});

					// 소요시간을 기록한다.
					results.latency = Date.now() - latency;
				});

			});
		}
	},
	verbose: true,
	logLevel: "debug"
});

function parseJson( text ) {
	try {
		text = JSON.parse( text );
	} catch ( exception ) {
		text = ""; // login 체크를 하지 않는 경우에 exception 발생하므로, 아무 처리를 하지 않는다.
	}
	return text;
}

// 실행에 필요한 정보들
var params = {
	serverId : 			casper.cli.get(0),	// "1".
											// 테스트대상 고유 ID. MySQL>hamlet.server_list 테이블 참조
	targetUrl : 		casper.cli.get(1),	// "http://sencha.or.kr/wp-login.php".
											// 모니터링 대상이 되는 URL
	hamletReceiverUrl : casper.cli.get(2),	// "http://hamlet.sencha.or.kr:8080/BotReceiverSvl?".
											// 테스트 결과를 저장 할 서버 (Hamlet-Server)
	containText : 		casper.cli.get(3),	// "김종광".
											// 해당 URL 페이지에 pageText 값의 존재 유무로 테스트 결과를 결정 하게 된다.
	serverInfoUrl : 	casper.cli.get(4),	// "http://hamlet.sencha.or.kr/hamlet/server_perf.jsp",
											// 서버정보(CPU/메모리/HDD 등의 정보)를 얻는 페이지 URL
	captureImagesPath : casper.cli.get(5),	// "/home/www/hamlet.sencha.or.kr/hamlet/capture_images/"
											// 캡쳐된 이미지가 저장 될 폴더. 끝에 '/'로 끝남에 주의한다.
	loginTest : 		casper.cli.get(6),	// "true" or "false"
											// login 테스트 실행 여부
	formQuery : 		casper.cli.get(7),	// 워드프레스 "form[name='loginform']" or 네이버 "form[id='frmNIDLogin']"
											// login 시 필요한 Form 객체 선택자
	loginInfo: parseJson( casper.cli.get(8) ),	// '{"log":"casperjs","pwd":"anjejfk8898"}'
												// Login 정보이다. JSON형식에 따라 안쪽에 쌍따옴표가 있다. 따라서 바깥이 싱글커텐션이다.
	timeout: 30000,
	browserWidth : 800,
	browserHeight: 600,

};

// 취합되는 정보들을 저장 한다.
var results = {
	status : false,
	textCheck : false,
	serverInfo : "",
	latency : 0,
	msg : ""
};



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

		s = params.captureImagesPath +
			params.serverId + "_" +
			timeData.leadingZeros(d.getFullYear(), 4) +
			timeData.leadingZeros(d.getMonth() + 1, 2) +
			timeData.leadingZeros(d.getDate(), 2) +

			timeData.leadingZeros(d.getHours(), 2) +
			timeData.leadingZeros(d.getMinutes(), 2) +
			timeData.leadingZeros(d.getSeconds(), 2) + ".jpg"

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
};

// 현재 시간 구한다.
timeData.init();	// // 현재 시간을 구한다. YYYY-MM-DD_HH:MM:SS 형식으로 구한다.
var latency = Date.now();	// 현재 시각

// casperjs 옵션 설정 - timeout
casper.options.timeout = params.timeout;

// casperjs 시작
casper.start(params.targetUrl, function() {

	if( params.loginTest ) {
		casper.log( "- params.loginTest1 : " + params.loginTest, "error" );
		this.fill(params.formQuery, params.loginInfo, true);
	} else {
		casper.log( "- params.loginTest2 : " + params.loginTest, "error" );
		// loginTest가 없는 경우 이므로 아무 작업을 하지 않는다.
	}

	casper.then(function() {

		casper.log( "#####1 : " + results.msg, "error" );
		if( results.msg == "404 Error" ) {
			// 404 에러 발생한 경우 이므로 아무 작업 하지 않는다.
		} else {

			casper.wait(2000, function() {
				this.capture(timeData.imageFileName,
					undefined
					, {
					format: 'jpg',
					quality: 75		// 이미지의 해상도 결정
				});

				if( this.fetchText('body').match(params.containText)) {
					results.status = true;
					results.msg = "Success";
				} else {	// 일치하는 텍스트가 없는 경우. 에러상황
					results.status = false;
					results.msg = "Cannot find Text";
				}

				// 소요시간을 기록한다.
				results.latency = Date.now() - latency;
			});
		}// close else

	});

});

casper.thenOpen(params.serverInfoUrl, function() {

	casper.log( "- getCurrentUrl : " + this.getCurrentUrl(), "debug" );
	casper.log( "- this.fetchText('body') : " + this.fetchText('body'), "debug" );

	results.serverInfo = JSON.parse( this.fetchText('body') );

	// 시스템 정보(cpu/메모리/hdd 등의 정보)를 Hamlet 서버로 보내어 기록한다.
	casper.thenOpen(params.hamletReceiverUrl,
		{
			method: "post",
			data: {
				serverId: params.serverId,
				createdDate: timeData.timestamp,
				status: results.status,
				image: timeData.imageFileName,
				latency: results.latency,
				p1: results.serverInfo.vm_total_mem,
				p2: results.serverInfo.vm_free_mem,
				p3: results.serverInfo.totalPhysicalMemorySize,
				p4: results.serverInfo.freePhysicalMemorySize,
				p5: results.serverInfo.totalSpace,
				p6: results.serverInfo.freeSpace,
				msg: results.msg,
				targetUrl: params.targetUrl
			}
		},
		function() {
			casper.log( "- POST request params.hamletReceiverUrl : " + params.hamletReceiverUrl, "debug" );
			casper.log( "- POST request has been sent.", "info" );
			casper.exit();
		});
});

casper.run(function() {
});

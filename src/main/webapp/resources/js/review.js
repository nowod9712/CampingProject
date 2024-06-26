//즉시실행함수 : reviewService 에 {name: "AAAA"} 할당

const reviewService = (function(){
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	function add(review, callback, error){
	
		console.log("add review..........");
		console.log(review);
		
		$.ajax({
			type: 'post',
			url: '/review/write',
			beforeSend: function(xhr) {
		            xhr.setRequestHeader(header, token);
		        },
			data: JSON.stringify(review),
			contentType: "application/json; charset=utf-8",
			/*
				xhr: ajax 요청을 생성하는 자바스크립트 api
				브라우저와 서버간의 네트워크 요청을 전송할 수 있음
				새로고침 없이도 url에서 데이터를 가져올 수 있다.
			*/
			success: function(result, status, xhr){//성공
				if(callback){ //callback값으로 적절한 함수가 존재한다면
					callback(result);//호출해서 결과 반영
				}
			},
			error: function(xhr, status, er){//실패
				if(error){//예외처리 함수가 있다면
					error(er);//예외처리
				}
			}
		});
	}
	function getList(param, callback, error){
		const item_no = param.item_no;
		//js에서의 논리연산자 (||) -> 제일 먼저 true가 나온 값을 반환
		//param.page가 없으면(undefined) 1을 반환
		const page = param.page || 1;
		
		//getJSON : JSON 목록 호출(json 형태가 필요하므로 URL확장자 .json)
		$.getJSON("/review/reviewList/" + item_no + "/" + page + ".json", //파라미터 1: url
			function(data){ // 파라미터2: 성공
				if(callback){//callback 함수가 있으면
					//callback(data); 댓글 목록만 가져옴
					callback(data.reviewCnt, data.reviewList);//리뷰 숫자와 목록을 가져옴
				}
			}).fail(function(xhr, status, err){ //파라미터3 : 실패
				if(error){ // error함수가 있으면
					error();
				}
			});
	}
	
	function remove(rev_no, callback, error){
		$.ajax({
			type: 'delete',
			url: '/review/'+rev_no,
			beforeSend: function(xhr) {
		            xhr.setRequestHeader(header, token);
		        },
			success:function(deleteResult, status, xhr){
				if(callback){
					callback(deleteResult);
				}
			},
			error:function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}
	
	function update(review, callback, error){
		console.log("RNO: "+review.rev_no);
		$.ajax({
			type: 'put',
			url:'/review/'+review.rev_no,
			beforeSend: function(xhr) {
		            xhr.setRequestHeader(header, token);
		        },
			data: JSON.stringify(review),
			contentType: "application/json; charset=utf-8",
			success: function(result, status, xhr){
				if(callback) callback(result);
			},
			error: function(xhr, status, er){
				if(error) error(er);
			}
		});
	}
	
	function get(rev_no, callback, error){
		//get메소드
		$.get("/review/"+rev_no+".json", function(result){
			if(callback) callback(result);
		}).fail(function(xhr, status, err){
			if(error) error();
		});
	}
	
	function displayTime(timeValue){
		const today = new Date();
		const gap = today.getTime() - timeValue;
		
		const dateObj = new Date(timeValue);
		let str = "";
		
		// 밀리초, 초, 분, 시(하루)
		if(gap < (1000 * 60 * 60 * 24)){ // 작성된 지 하루도 안 지났다면- 시:분:초 표시
			const hh = dateObj.getHours();
			const mi = dateObj.getMinutes();
			const ss = dateObj.getSeconds();
			
			//각 시, 분, 초가 한자리면 앞에 0을 붙여 출력한다.
			return [(hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':',
				(ss > 9 ? '' : '0') + ss ].join('');
		}else{ //작성된 지 1일 이상이 되었다면- 연/월/일 표시
			const yy = dateObj.getFullYear();
			const mm = dateObj.getMonth() + 1; // getMonth()는 0부터 시작한다
			const dd = dateObj.getDate();
			
			//각 연, 월, 일이 한자리면 앞에 0을 붙여 출력한다.
			return [yy, '/', (mm > 9 ? '' : '0') + mm, '/',
				(dd > 9 ? '' : '0') + dd ].join('');
		}
	}
	
	//key: add, value: add메소드
	return {
		add:add,
		getList: getList,
		remove: remove,
		update: update,
		get: get,
		displayTime: displayTime
	};
})();
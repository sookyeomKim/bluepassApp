/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive(
	"bookedScheduleCountByDate",
	[
		'$filter',
		'ClassSchedule',
		function($filter, ClassSchedule) {
		    return {
			restrict : "A",
			scope : {
			    actionId : "@",
			    customDate : "=",
			    count : "=",
			    version : "@"
			},
			link : function(sco) {
			    sco.load = function() {
				var params = {};
				var modiCustomDate;

				switch (sco.version) {
				    case '0'://클래스의 예약 되어있는 (출석체크 안 함) 모든 스케줄
					params = {
					    page : -1,
					    actionId : sco.actionId,
					    reservated : true,
					    enable : true,
					    used:false
					};
					break;
				    case '1'://클래스의 기간이 지난 예약 되어있는 (출석체크 안 한) 모든 스케줄
					var yesterday = new Date(sco.customDate.year, sco.customDate.month-1,sco.customDate.date);
					modiCustomDate = $filter("date")(yesterday.setDate(yesterday.getDate() - 1), "yyMMdd", "KST");
					params = {
					    actionId : sco.actionId,
					    startDate : 150801,
					    endDate : modiCustomDate,
					    reservated : true,
					    finished : true,
					    enable : true,
					    used:false
					};
					break;
				    case '2'://클래스의 해당날짜의 예약 되어 있는 (출석체크 안 함) 모든 스케줄
					modiCustomDate = $filter("date")(
						new Date(sco.customDate.year, sco.customDate.month-1, sco.customDate.date),
						"yyMMdd", "KST");
					params = {
					    actionId : sco.actionId,
					    startDate : modiCustomDate,
					    endDate : modiCustomDate,
					    reservated : true,
					    enable : true,
					    used:false
					};
					break;

				    default:
					params = {
					    page : -1,
					    reservated : true,
					    enable : true,
					    actionId : sco.actionId,
					    used:false
					};
					break;
				}

				ClassSchedule.query(params).$promise.then(function(success) {
				    if(sco.version==='0'){
				    }

				    sco.count = success.length;
				});
			    };

			    sco.$watch("actionId", function(newVal) {
				sco.load(newVal);
			    })
			}
		    }
		} ]);

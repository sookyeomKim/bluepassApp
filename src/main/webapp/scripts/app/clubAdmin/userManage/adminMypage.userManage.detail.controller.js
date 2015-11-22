/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'adminMypageUserManageDetailController',
    [
        '$log',
        '$scope',
        '$timeout',
        '$stateParams',
        'TodayDate',
        'legendInit',
        'ActionByClub',
        'Club',
        'ClassSchedule',
        'bookedClassSchedule',
        '$filter',
        'lodash',
        'customerByClub',
        'customersByAction',
        function ($log, $scope, $timeout, $stateParams, TodayDate, legendInit, ActionByClub, Club,
                  ClassSchedule, bookedClassSchedule, $filter, lodash, customerByClub, customersByAction) {
            $scope.mpc = true;
            $scope.getScope = function () {
                return $scope;
            };
            /* 클럽ID */
            var idBelongToClub = $scope.idBelongToClub = $stateParams.id;

            /* 오늘의 날짜 */
            $scope.today = "오늘은 \"" + TodayDate.t_year() + "/" + TodayDate.t_month() + "/" + TodayDate.t_date()
                + "/" + TodayDate.t_daylabel(TodayDate.t_day()) + "\" 입니다.";

            /* 요일 탭 */
            $scope.dayArry = legendInit.legendinit(7);

            $scope.navigationState = 0;

            $scope.selected_day = function (count) {
                $scope.navigationState = count;
            };

            /* 강사목록 */
            $scope.insCall = function (scheduleInfo) {
                if (scheduleInfo.length !== 0) {
                    $scope.instructorArry = [];
                    for (var i = 0; i < scheduleInfo.length; i++) {
                        $scope.instructorArry.push({
                            id: scheduleInfo[i].instructor.id,
                            name: scheduleInfo[i].instructor.name
                        });
                    }
                    $scope.uniqInstructorArry = lodash.uniq($scope.instructorArry, "id");
                }
            };

            /* 예약인원목록 */
            $scope.bookedUsers = function (id) {
                var bookedUserCall = bookedClassSchedule.query({
                    id: id
                });
                $scope.bookedUserList = [];
                bookedUserCall.$promise.then(function (success) {
                    $scope.bookedUserList = success;
                    $scope.usedCount = lodash.size(lodash.filter(success, 'used', true));
                    $scope.notUsedCount = lodash.size(lodash.filter(success, 'used', false));
                    /*console.log($scope.bookedUserList)*/
                })
            };

            /* 스케줄 목록 불러오기 */
            $scope.actionCall = function (id, ns) {
                $scope.mpc = true;

                var dateToggle;
                var yesterday;
                var customDate;
                var params = {};
                var owl = jQuery("#reservedTable");

                owl.owlCarousel({
                    itemsCustom: [[0, 3], [450, 4], [600, 5], [960, 6], [1200, 7], [1366, 7]],
                    pagination: false

                });

                customersByAction.query({
                    id: id,
                    yearMonth: $filter("date")(
                        new Date($scope.dayArry[0].year, $scope.dayArry[0].month - 1,
                            $scope.dayArry[0].date), "yyMM", "KST")
                }).$promise.then(function (success) {
                        $scope.customersByActionCount = success.length;
                        /* console.log($scope.customersByActionCount) */
                    });

                if (ns === -2) {//출석 완료한 스케줄
                    /*dateToggle = 0;
                     yesterday = new Date($scope.dayArry[dateToggle].year, $scope.dayArry[dateToggle].month - 1,
                     $scope.dayArry[dateToggle].date);
                     customDate = $filter("date")(yesterday.setDate(yesterday.getDate() - 1), "yyMMdd", "KST");*/
                    params = {
                        page: -1,
                        actionId: id,
                        reservated: true,
                        used: true
                    }
                } else if (ns === -1) {//기간 지난 예약된 스케줄(출석,미출석체크 안한)
                    dateToggle = 0;
                    yesterday = new Date($scope.dayArry[dateToggle].year, $scope.dayArry[dateToggle].month - 1,
                        $scope.dayArry[dateToggle].date);
                    customDate = $filter("date")(yesterday.setDate(yesterday.getDate() - 1), "yyMMdd", "KST");
                    params = {
                        actionId: id,
                        startDate: 150801,
                        endDate: customDate,
                        reservated: true,
                        finished: true,
                        enable: true,
                        used: false
                    }
                } else {//기간별 예약된 스케줄(출석,미출석체크 안한)
                    dateToggle = ns;
                    customDate = $filter("date")(
                        new Date($scope.dayArry[dateToggle].year, $scope.dayArry[dateToggle].month - 1,
                            $scope.dayArry[dateToggle].date), "yyMMdd", "KST");
                    params = {
                        actionId: id,
                        startDate: customDate,
                        endDate: customDate,
                        reservated: true,
                        enable: true,
                        used: false
                    }
                }

                /*예약된 스케줄 목록 불러오기*/
                ClassSchedule.query(params).$promise.then(function (success) {
                    /*console.log(success)*/
                    $scope.insCall(success);
                    $scope.reservedSchedules = success;

                }, function () {
                    $scope.mpc = false;
                    alert("스케줄 정보를 불러오지 못 하였습니다.")
                }).then(function () {
                    $timeout(function () {
                        jQuery(".userManageWrap .next").click(function () {
                            owl.trigger('owl.next');
                        });
                        jQuery(".userManageWrap .prev").click(function () {
                            owl.trigger('owl.prev');
                        });
                        jQuery('.footable').footable();
                        jQuery('.footable').trigger('footable_redraw');
                    });
                }).then(function () {
                    $timeout(function () {
                        $scope.mpc = false;
                    }, 1000);
                });
            };

            /* 클래스 목록 탭 */
            $scope.loadAll = function (id) {
                Club.get({
                    id: id
                }).$promise.then(function (success) {
                        var actionsCall = ActionByClub.query({
                            id: idBelongToClub
                        });
                        $scope.club = success;

                        actionsCall.$promise.then(function (success) {
                            $scope.actionList = success;
                            /* console.log($scope.actionList) */
                            if ($scope.actionList.length !== 0) {
                                $scope.classSelected = success[0].id;
                            } else {
                                $scope.mpc = false;
                            }
                            $scope.$watchGroup(["classSelected", "navigationState"], function (newVal) {
                                /* 예약된 스케줄 소환 */
                                if (newVal[0]) {
                                    $scope.actionCall(newVal[0], newVal[1]);
                                }
                            })
                        })
                    });
                customerByClub.query({
                    id: id,
                    yearMonth: $filter("date")(
                        new Date($scope.dayArry[0].year, $scope.dayArry[0].month - 1,
                            $scope.dayArry[0].date), "yyMM", "KST")
                }).$promise.then(function (success) {
                        $scope.customerCount = success.length;
                        /* console.log(success); */
                    })
            };
            $scope.loadAll(idBelongToClub);
        }]);

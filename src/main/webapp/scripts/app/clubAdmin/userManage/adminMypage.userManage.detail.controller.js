/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageUserManageDetailController', adminMypageUserManageDetailController);

adminMypageUserManageDetailController.$inject = [
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
    'CustomerByClub',
    'CustomersByAction'
];

function adminMypageUserManageDetailController($log, $scope, $timeout, $stateParams, TodayDate, legendInit, ActionByClub, Club,
                                               ClassSchedule, bookedClassSchedule, $filter, lodash, CustomerByClub, CustomersByAction) {
    var vm = this;

    vm.mpc = true;
    /* 클럽ID */
    vm.idBelongToClub = $stateParams.id;
    /* 오늘의 날짜 */
    vm.today = "오늘은 \"" + TodayDate.t_year() + "/" + TodayDate.t_month() + "/" + TodayDate.t_date()
        + "/" + TodayDate.t_daylabel(TodayDate.t_day()) + "\" 입니다.";
    /* 요일 탭 */
    vm.dayArry = legendInit.legendinit(7);
    vm.navigationState = 0;
    vm.selected_day = selected_day;
    /* 예약인원목록 */
    vm.bookedUsers = bookedUsers;

    /*총 이용고객수 불러오기*/
    getgetCustomersByClub(vm.idBelongToClub);
    /*클래스 목록 불러오기*/
    getActionByClubQuery(vm.idBelongToClub);

    function getgetCustomersByClub(id) {
        return CustomerByClub.query({
            id: id,
            yearMonth: $filter("date")(
                new Date(vm.dayArry[0].year, vm.dayArry[0].month - 1,
                    vm.dayArry[0].date), "yyMM", "KST")
        }).$promise.then(function (response) {
                vm.customerCount = response.length;
            })
    }

    function getActionByClubQuery(id) {
        return ActionByClub.query({id: id}).$promise.then(function (response) {
            vm.actionList = response;
            if (vm.actionList.length !== 0) {
                vm.classSelected = response[0].id;
            } else {
                vm.mpc = false;
            }
            return $scope.$watchGroup(["vm.classSelected", "vm.navigationState"], function (newVal) {
                /* 예약된 스케줄 소환 */
                if (newVal[0]) {
                    /*클래스별 이용고객수 불러오기*/
                    getCustomersByAction(newVal[0]);

                    /*클래스별 예약스케줄 불러오기*/
                    getClassScheduleQuery(newVal[0], newVal[1]);
                }
            })
        })
    }

    function getCustomersByAction(id) {
        return CustomersByAction.query({
            id: id,
            yearMonth: $filter("date")(
                new Date(vm.dayArry[0].year, vm.dayArry[0].month - 1,
                    vm.dayArry[0].date), "yyMM", "KST")
        }).$promise.then(function (reponse) {
                vm.customersByActionCount = reponse.length;
            });
    }

    function getClassScheduleQuery(id, ns) {
        var dateArryNum;
        var yesterday;
        var customDate;
        var params = {};
        var owl = jQuery("#reservedTable");
        owl.owlCarousel({
            itemsCustom: [[0, 3], [450, 4], [600, 5], [960, 6], [1200, 7], [1366, 7]],
            pagination: false

        });
        if (ns === -2) {//출석 완료한 스케줄
            params = {
                page: -1,
                actionId: id,
                reservated: true,
                used: true
            }
        } else if (ns === -1) {//기간 지난 예약된 스케줄(출석,미출석체크 안한)
            dateArryNum = 0;
            yesterday = new Date(vm.dayArry[dateArryNum].year, vm.dayArry[dateArryNum].month - 1,
                vm.dayArry[dateArryNum].date);
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
            dateArryNum = ns;
            customDate = $filter("date")(
                new Date(vm.dayArry[dateArryNum].year, vm.dayArry[dateArryNum].month - 1,
                    vm.dayArry[dateArryNum].date), "yyMMdd", "KST");
            params = {
                actionId: id,
                startDate: customDate,
                endDate: customDate,
                reservated: true,
                enable: true,
                used: false
            }
        }

        return ClassSchedule.query(params).$promise.then(function (response) {
            /*강사 목록 불러오기*/
            getInstructor(response);
            vm.reservedSchedules = response;
            jQuery(".userManageWrap .next").click(function () {
                owl.trigger('owl.next');
            });
            jQuery(".userManageWrap .prev").click(function () {
                owl.trigger('owl.prev');
            });
            jQuery('.footable').footable().trigger('footable_redraw');
            vm.mpc = false;
        }).catch(function () {
            vm.mpc = false;
            return alert("스케줄 정보를 불러오지 못 하였습니다.")
        })
    }

    function getInstructor(scheduleInfo) {
        if (scheduleInfo.length !== 0) {
            vm.instructorArry = [];
            for (var i = 0; i < scheduleInfo.length; i++) {
                vm.instructorArry.push({
                    id: scheduleInfo[i].instructor.id,
                    name: scheduleInfo[i].instructor.name
                });
            }
            return vm.uniqInstructorArry = lodash.uniq(vm.instructorArry, "id");
        }
    }

    function selected_day(count) {
        vm.navigationState = count;
    }

    function bookedUsers(id) {
        vm.bookedUserList = [];
        return bookedClassSchedule.query({id: id}).$promise.then(function (reponse) {
            vm.bookedUserList = reponse;
            vm.usedCount = lodash.size(lodash.filter(reponse, 'used', true));
            vm.notUsedCount = lodash.size(lodash.filter(reponse, 'used', false));
        })
    }
}

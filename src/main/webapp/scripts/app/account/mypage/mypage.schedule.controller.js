/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('mypageScheduleController', mypageScheduleController);

mypageScheduleController.$inject = ['$scope', '$timeout', 'lodash', 'ReservationOfUser', 'ReservationHistoryByUser', 'CodeNameCommonCode'];

function mypageScheduleController($scope, $timeout, lodash, ReservationOfUser, ReservationHistoryByUser, CodeNameCommonCode) {
    var vm = this;

    vm.account = {};
    vm.ticketId = null;
    vm.ticket = null;
    vm.userReservation = 0;
    vm.myScheduleList = [];
    vm.getMyScheduleList = getMyScheduleList;

    getAccount();
    function getAccount() {
        return $scope.$on('Account', function (event, response) {
            vm.stateConfirmVendor = lodash.findIndex(response.roles, function (chr) {
                return chr === 'ROLE_VENDOR'
            });
            vm.ticketId = response.ticketId;
            vm.account = response;
            return getTickets();
        })
    }

    function getTickets() {
        return CodeNameCommonCode.query({codeName: 'CATEGORY_TICKET'}).$promise.then(function (success) {
            return vm.ticket = lodash.find(success, 'id', vm.ticketId);
        });
    }

    function getMyScheduleList(n) {
        if (n === 0) {// 현재예약목록
            getReservationOfUser();
        } else if (n === 1) {// 취소한목록
            getReservationHistoryByUser({canceled: true})
        } else if (n === 2) {// 출석체크된목록
            getReservationHistoryByUser({used: true})
        } else if (n === 3) {// 결석한목록
            getReservationHistoryByUser({used: false})
        }
    }

    function getReservationOfUser() {
        return ReservationOfUser.query().$promise.then(function (success) {
            vm.myScheduleList = success
        }).then(function () {
            $timeout(function () {
                jQuery("#mySchduleTab .nav-tabs a").click(function (e) {
                    e.preventDefault();
                });
                jQuery('.footable').footable({
                    addRowToggle: false
                }).trigger('footable_redraw');
            }, 500);
        })
    }

    function getReservationHistoryByUser(prams) {
        return ReservationHistoryByUser.query(prams).$promise.then(function (success) {
            vm.myScheduleList = success;
        }).then(function () {
            $timeout(function () {
                jQuery("#mySchduleTab .nav-tabs a").click(function (e) {
                    e.preventDefault();
                });
                jQuery('.footable').footable({
                    addRowToggle: false
                }).trigger('footable_redraw');
            });
        })
    }

    $scope.$watch("vm.userReservation", function (newVal) {
        vm.getMyScheduleList(newVal);
    })
}

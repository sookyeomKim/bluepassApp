/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('mypageScheduleController', mypageScheduleController);

mypageScheduleController.$inject = ['$scope', '$timeout', 'lodash', 'ReservationOfUser', 'myReservationHistory', 'codeNameCommonCode'];

function mypageScheduleController($scope, $timeout, lodash, ReservationOfUser, myReservationHistory, codeNameCommonCode) {
    var vm = this;

    vm.account = {};
    vm.ticketId = null;
    vm.ticket = null;
    vm.userReservation = 0;
    vm.myScheduleList = [];
    vm.loadAll = loadAll;

    getAccount();
    function getAccount() {
        return $scope.$on('Account', function (event, response) {
            vm.ticketId = response.ticketId;
            vm.account = response;
            return getTickets();
        })
    }

    function getTickets() {
        return codeNameCommonCode.query({codeName: 'CATEGORY_TICKET'}).$promise.then(function (success) {
            return vm.ticket = lodash.find(success, 'id', vm.ticketId);
        });
    }

    function loadAll(n) {
        if (n === 0) {// 현재예약목록
            ReservationOfUser.query().$promise.then(function (success) {
                vm.myScheduleList = success
            }).then(function () {
                $timeout(function () {
                    jQuery("#mySchduleTab .nav-tabs a").click(function (e) {
                        e.preventDefault();
                    });
                    jQuery('.footable').footable({
                        addRowToggle: false
                    });
                    jQuery('.footable').trigger('footable_redraw');
                }, 500);
            })
        } else if (n === 1) {// 취소한목록
            myReservationHistory.query({
                canceled: true
            }).$promise.then(function (success) {
                    vm.myScheduleList = success;
                }).then(function () {
                    $timeout(function () {
                        jQuery("#mySchduleTab .nav-tabs a").click(function (e) {
                            e.preventDefault();
                        });
                        jQuery('.footable').footable({
                            addRowToggle: false
                        });
                        jQuery('.footable').trigger('footable_redraw');
                    });
                })
        } else if (n === 2) {// 출석체크된목록
            myReservationHistory.query({
                used: true
            }).$promise.then(function (success) {
                    vm.myScheduleList = success;
                    /*console.log(vm.myScheduleList)*/
                }).then(function () {
                    $timeout(function () {
                        jQuery("#mySchduleTab .nav-tabs a").click(function (e) {
                            e.preventDefault();
                        });
                        jQuery('.footable').footable({
                            addRowToggle: false
                        });
                        jQuery('.footable').trigger('footable_redraw');
                    });
                })
        } else if (n === 3) {// 결석한목록
            myReservationHistory.query({
                used: false
            }).$promise.then(function (success) {
                    vm.myScheduleList = success;
                    /*console.log(vm.myScheduleList)*/
                }).then(function () {
                    $timeout(function () {
                        jQuery("#mySchduleTab .nav-tabs a").click(function (e) {
                            e.preventDefault();
                        });
                        jQuery('.footable').footable({
                            addRowToggle: false
                        });
                        jQuery('.footable').trigger('footable_redraw');
                    });
                })
        }
    }

    $scope.$watch("vm.userReservation", function (newVal) {
        vm.loadAll(newVal);
    })
}

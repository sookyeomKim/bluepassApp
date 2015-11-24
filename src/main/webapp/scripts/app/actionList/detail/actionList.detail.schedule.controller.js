/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('actionListDetailScheduleController', actionListDetailScheduleController);

actionListDetailScheduleController.$inject = [
    '$scope',
    '$stateParams',
    '$timeout',
    'legendInit',
    'ClassSchedule',
    '$filter',
    'authorize'
];

function actionListDetailScheduleController($scope, $stateParams, $timeout, legendInit, ClassSchedule, $filter, authorize) {
    var vm = this;

    /*계정정보*/
    vm.account = authorize;
    /*요일 초기화 */
    vm.dayArry = legendInit.legendinit(7);
    vm.navigationState = 0;
    vm.selected_day = selected_day;

    /*스케줄 불러오기*/
    $scope.$watch('vm.navigationState', function (newVal) {
        getClassSchedule(newVal);
    });

    function getClassSchedule(ns) {
        var customDate = $filter("date")
        (
            new Date(vm.dayArry[ns].year, vm.dayArry[ns].month - 1,
                vm.dayArry[ns].date), "yyMMdd", "KST");
        return ClassSchedule.query({
            startDate: customDate,
            endDate: customDate,
            actionId: $stateParams.id,
            enable: true,
            finished: false
        }).$promise.then(function (success) {
                vm.actionSchedulesList = success;
                vm.error = false;
            }, function () {
                vm.error = true;
            }).then(
            function () {
                return $timeout(function () {
                    var owl = jQuery("#detailTableLegend");
                    owl.owlCarousel({
                        itemsCustom: [[0, 3], [450, 4], [600, 5], [960, 6], [1200, 7],
                            [1366, 7]],
                        pagination: false
                    });
                    jQuery(".actionDetailWrap .next").click(function () {
                        owl.trigger('owl.next');
                    });
                    jQuery(".actionDetailWrap .prev").click(function () {
                        owl.trigger('owl.prev');
                    });
                    jQuery('.footable').footable({
                        addRowToggle: false
                    }).trigger('footable_redraw');
                })
            })
    }

    function selected_day(count) {
        vm.navigationState = count;
    }
}

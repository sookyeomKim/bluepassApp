/**
 * 만든이 :수겨미
 */
'use strict';

angular.module('bluepassApp').controller('actionListTableTypeController', actionListTableTypeController);

actionListTableTypeController.$inject = [
    '$scope',
    '$timeout',
    '$filter',
    'ClassSchedule',
    'legendInit',
    'ParseLinks'
];

function actionListTableTypeController($scope, $timeout, $filter, ClassSchedule, legendInit, ParseLinks) {
    var vm = this;

    /* 레전드 세팅 */
    vm.navigationState = 0;
    vm.dayArry = legendInit.legendinit(7);
    vm.selected_day = selected_day;
    vm.mpc = true;
    vm.error = false;
    vm.queryPrams = {};
    vm.loadPage = loadPage;

    getQueryPrams();

    function getQueryPrams() {
        return $scope.$on('QueryPrams', function (event, response) {
            var owl = jQuery("#tableTypeLegend");
            owl.owlCarousel({
                itemsCustom: [[0, 3], [450, 4], [600, 5], [960, 6], [1200, 7], [1366, 7]],
                pagination: false
            });
            vm.queryPrams = response;
            return $scope.$watch('vm.navigationState', function (newVal) {
                vm.page = 1;
                vm.actionScheduleList = [];
                getActionSchedule(newVal, vm.queryPrams);
            })
        })
    }

    function getActionSchedule(navigationState, queryPrams) {
        var customDate = $filter("date")(
            new Date(vm.dayArry[navigationState].year, vm.dayArry[navigationState].month - 1,
                vm.dayArry[navigationState].date), "yyMMdd", "KST"
        );
        return ClassSchedule.query({
            page: vm.page,
            per_page: 30,
            startDate: customDate,
            endDate: customDate,
            startTime: queryPrams.startTime,
            endTime: queryPrams.endTime,
            category: queryPrams.exerciseType,
            address: queryPrams.siteType,
            enable: true,
            finished: false
        }, function (result, headers) {
            vm.links = ParseLinks.parse(headers('link'));
        }).$promise.then(function (response) {
                vm.actionScheduleList = response;
                vm.error = false;
                vm.mpc = false;
            }, function () {
                vm.error = true;
                vm.mpc = false;
            }).then(function () {
                jQuery(".actionList .next").click(function () {
                    owl.trigger('owl.next');
                });
                jQuery(".actionList .prev").click(function () {
                    owl.trigger('owl.prev');
                });
                $timeout(function () {
                    jQuery('.footable').footable({
                        addRowToggle: false
                    }).trigger('footable_redraw');
                });
            });
    }

    function selected_day(count) {
        vm.navigationState = count;
    }

    function loadPage(page, navigationState, queryPrams) {
        vm.mpc = true;
        vm.page = page;
        return getActionSchedule(navigationState, queryPrams)
    }
}

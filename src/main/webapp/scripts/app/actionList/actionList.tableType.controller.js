/**
 * 만든이 :수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'actionListTableTypeController',
    [
        '$scope',
        '$timeout',
        '$log',
        '$element',
        '$filter',
        'ClassSchedule',
        'legendInit',
        'ParseLinks',
        function ($scope, $timeout, $log, $element, $filter, ClassSchedule, legendInit, ParseLinks) {

            /* 스케줄 리퀘스트 */
            $scope.loadAll = function (ns, et, st, ts, te) {
                var owl = jQuery("#tableTypeLegend");
                owl.owlCarousel({
                    itemsCustom: [[0, 3], [450, 4], [600, 5], [960, 6], [1200, 7], [1366, 7]],
                    pagination: false
                });

                var customDate = $filter("date")
                (
                    new Date($scope.dayArry[ns].year, $scope.dayArry[ns].month - 1,
                        $scope.dayArry[ns].date), "yyMMdd", "KST");
                /*
                 * console.log(customDate + "/" + tt.startTime + "/" +
                 * tt.endTime + "/" + et + "/" + st)
                 */
                var actionSchedule = ClassSchedule.query({
                    page: $scope.page,
                    per_page: 30,
                    startDate: customDate,
                    endDate: customDate,
                    startTime: ts,
                    endTime: te,
                    category: et,
                    address: st,
                    enable: true,
                    finished: false
                }, function (result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                });
                actionSchedule.$promise.then(function (success) {
                    /*console.log(success)*/
                    $scope.actionScheduleList = success;
                    $scope.error = false;
                    $scope.mpc = false;
                    /* console.log($scope.actionScheduleList) */
                }, function () {
                    $scope.error = true;
                    $scope.mpc = false;
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
                        });
                        jQuery('.footable').trigger('footable_redraw');
                    });
                });
            };

            $scope.loadPage = function (page, ns, et, st, ts, te) {
                $scope.mpc = true;
                $scope.page = page;
                $scope.loadAll(ns, et, st, ts, te);
            };

            /*
             * $scope.$on('timeEvent', function(event, args) {
             * $scope.loadAll($scope.navigationState, args.exerciseType,
             * args.siteType, args.timeType); });
             */

            /* 레전드 세팅 */
            $scope.navigationState = 0;

            $scope.dayArry = legendInit.legendinit(7);
            $scope.selected_day = function (count) {
                $scope.navigationState = count;
            };

            /* 검색와처 */
            $scope.$watchGroup(['navigationState', 'exerciseType', 'siteType', 'timeType.startTime',
                'timeType.endTime'], function (newVal) {
                $scope.mpc = true;
                $scope.page = 1;
                $scope.actionScheduleList = [];
                $scope.loadAll(newVal[0], newVal[1], newVal[2], newVal[3], newVal[4]);
            });
        }]);

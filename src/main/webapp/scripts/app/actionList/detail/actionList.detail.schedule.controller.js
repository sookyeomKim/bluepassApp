/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'actionListDetailScheduleController',
    [
        '$scope',
        '$stateParams',
        '$timeout',
        '$log',
        'legendInit',
        'ClassSchedule',
        '$filter',
        function ($scope, $stateParams, $timeout, $log, legendInit, ClassSchedule, $filter) {
            $scope.getScope = function () {
                return $scope;
            };
            /* 레전드 세팅 */
            $scope.dayArry = legendInit.legendinit(7);

            $scope.navigationState = 0;

            $scope.selected_day = function (count) {
                $scope.navigationState = count;
            };

            /* 스케줄 CRUD */
            $scope.loadAll = function (ns) {
                var customDate = $filter("date")
                (
                    new Date($scope.dayArry[ns].year, $scope.dayArry[ns].month - 1,
                        $scope.dayArry[ns].date), "yyMMdd", "KST");
                var actionSchedules = ClassSchedule.query({
                    startDate: customDate,
                    endDate: customDate,
                    actionId: $stateParams.id,
                    enable: true,
                    finished: false
                });
                actionSchedules.$promise.then(function (success) {
                    $scope.actionSchedulesList = success;
                    /*console.log($scope.actionSchedulesList)*/
                    $scope.error = false;
                }, function () {
                    $scope.error = true;
                }).then(
                    function () {
                        $timeout(function () {
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
                            });
                            jQuery('.footable').trigger('footable_redraw');
                        })
                    })
            };
            $scope.$watch('navigationState', function (newVal) {
                $scope.loadAll(newVal);
            });
        }]);

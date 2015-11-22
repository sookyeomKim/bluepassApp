/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'actionListDetailOtherClassController',
    ['$scope', '$log', '$stateParams', '$filter', 'ActionByClub', 'DataShare',
        function ($scope, $log, $stateParams, $filter, ActionByClub, DataShare) {
            $scope.currentId = $stateParams.id;
            $scope.$on('data_shared', function () {
                /* 클럽ID */
                var idBelongToClub = DataShare.getData();
                /* 클래스 CRUD */
                $scope.loadAll = function () {
                    var otherActions = ActionByClub.query({
                        id: idBelongToClub
                    });
                    otherActions.$promise.then(function (success) {
                        $scope.otherActionList = $filter('removeWith')(success, {
                            id: $scope.currentId
                        });
                        /*$log.debug($scope.otherActionList);*/
                    });
                };
                $scope.loadAll();
            });
        }]);

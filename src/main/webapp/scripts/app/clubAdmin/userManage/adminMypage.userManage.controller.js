/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageUserManageController',
    ['$scope', '$log', 'ParseLinks', 'partnerClubs', function ($scope, $log, ParseLinks, partnerClubs) {
        /* 업체/그룹 CRUD */
        $scope.page = 1;

        $scope.loadAll = function () {
            $scope.clubs = partnerClubs.query({
                page: -1
            });
            $scope.clubs.$promise.then(function (success) {
                $scope.clubList = success;
                $scope.mpc = false;
            }).then(function () {
                jQuery('#club-accordion .panel-heading a').click(function (e) {
                    e.preventDefault();
                });
            });
        };

        $scope.loadAll();
    }]);
